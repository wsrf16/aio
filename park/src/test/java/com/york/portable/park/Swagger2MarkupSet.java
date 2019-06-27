package com.york.portable.park;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.asciidoctor.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

@Component
//@AutoConfigureMockMvc
public class Swagger2MarkupSet {
//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Value("${server.servlet.contextPath:}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String port;

//    @LocalServerPort
//    private int port;

    public void run() throws Exception {
        createSpringfoxSwaggerJson();
        generateMultiple();
        asciiDoctor();
    }

    private void createSpringfoxSwaggerJson() throws Exception {
        String swaggerJsonFile = System.getProperty("test.swagger2markup.swaggerOutput");

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        new File(swaggerJsonFile).getParentFile().mkdir();
        String swaggerJsonContent = mvcResult.getResponse().getContentAsString();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(swaggerJsonFile), StandardCharsets.UTF_8)){
            writer.write(swaggerJsonContent);
        }
    }

    private void generateMultiple() throws MalformedURLException {
        String urlApiDocs = MessageFormat.format("http://localhost:{0}{1}/v2/api-docs", port, contextPath);
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
//                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        generateMarkup(urlApiDocs, config,
                "./target/asciidoc/generated",
                "./target/asciidoc/generated/all");
    }

    private static void generateMarkup(String local, Swagger2MarkupConfig config, String toFolder, String toFile) throws MalformedURLException {
        URL url = UriComponentsBuilder.fromUriString(local).build().toUri().toURL();
        Swagger2MarkupConverter swagger2MarkupConverter = Swagger2MarkupConverter.from(url)
                .withConfig(config)
                .build();
        swagger2MarkupConverter.toFolder(Paths.get(toFolder));
        swagger2MarkupConverter.toFile(Paths.get(toFile));
    }

    private void asciiDoctor() {
        String generated = System.getProperty("test.asciidoctor.directory.input.generated");
        String html = System.getProperty("test.asciidoctor.directory.output.html");
        String pdf = System.getProperty("test.asciidoctor.directory.output.pdf");
        String asciiInputFile = Paths.get(System.getProperty("test.asciidoctor.directory.input"), "index.adoc").toFile().getAbsolutePath();


        Attributes attributes = new Attributes();
        attributes.setCopyCss(true);
        attributes.setTableOfContents(true);
        attributes.setLinkCss(false);
        attributes.setSectNumLevels(3);
        attributes.setAnchors(true);
        // 自动打数字序号
        attributes.setSectionNumbers(true);
        attributes.setHardbreaks(true);
        attributes.setTableOfContents(Placement.LEFT);
        attributes.setAttribute("toclevels", "3");
        attributes.setAttribute("generated", generated);
        OptionsBuilder optionsBuilder = OptionsBuilder.options()
                .docType("book")
                .eruby("")
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .mkDirs(true)
                .backend("html5")
                .toDir(new  File(html))
                .attributes(attributes);
        Options options = optionsBuilder.get();
//        String asciiInputFile = "./target/asciidoc/generated/index.adoc";

//        String asciiInputFile = "./src/docs/asciidoc/index.adoc";
        Asciidoctor.Factory.create().convertFile(new File(asciiInputFile), options);
    }
}
