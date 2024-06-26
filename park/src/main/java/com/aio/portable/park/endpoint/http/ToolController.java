package com.aio.portable.park.endpoint.http;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.spring.web.Base64MultipartFile;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import com.aio.portable.swiss.suite.system.HostInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

@RestController
@RequestMapping("tool")
@Api(tags = "工具", consumes = "application/json")
public class ToolController {
    private static final String UPLOADS_CONTENT_TYPE = HttpHeaders.CONTENT_TYPE + "=" + MediaType.MULTIPART_FORM_DATA_VALUE;
    private static final String UPLOAD_DIRECTORY = "upload";

    private LogHub log = AppLogHubFactory.staticBuild().setAsync(false);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    RestTemplate skipSSLRestTemplate;

//    @ApiOperation(value = "upload接口")
    @PostMapping("/upload")
    public ResponseWrapper<String> upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        Path targetDirectory = new File(UPLOAD_DIRECTORY).toPath();
        Files.createDirectories(targetDirectory);

        String originalFilename = multipartFile.getOriginalFilename();
        Path targetFile = Paths.get(UPLOAD_DIRECTORY, originalFilename);
        multipartFile.transferTo(targetFile);

        String realIP = HostInfo.getClientIpAddress(request);
        String uploadLocation = MessageFormat.format("{0}:{1}", realIP, targetFile.toAbsolutePath());
        log.info(UPLOAD_DIRECTORY, uploadLocation);
        return ResponseWrappers.succeed(uploadLocation);
    }

//    @ApiOperation(value = "上传文件接口",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiResponses({
//            @ApiResponse(code = 601,message = "上传文件发生异常")
//    })
//    @PostMapping(value = "/uploads", headers = "Content-Type=multipart/form-data")


    @PostMapping(value = "/uploads", headers = UPLOADS_CONTENT_TYPE)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "file", dataType = "__File", value = "文件流对象,接收数组格式", required = true)
            }
    )
    public ResponseWrapper<List<String>> uploads(@RequestPart(value = "files") MultipartFile[] multipartFiles) throws IOException {
        Path targetDirectory = new File(UPLOAD_DIRECTORY).toPath();
        Files.createDirectories(targetDirectory);

        List<String> uploadLocationList = new ArrayList<String>();
        Arrays.stream(multipartFiles).forEach(multipartFile -> {
            String originalFilename = multipartFile.getOriginalFilename();

            try {
                Path targetFile = Paths.get(UPLOAD_DIRECTORY, originalFilename);
                multipartFile.transferTo(targetFile);

                String realIP = HostInfo.getClientIpAddress(request);
                String uploadLocation = realIP + ":" + targetFile.toAbsolutePath();
                uploadLocationList.add(uploadLocation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        log.info(UPLOAD_DIRECTORY, uploadLocationList);
        return ResponseWrappers.succeed(uploadLocationList);
    }

    @PostMapping(value = "/uploadBase64")
    public ResponseWrapper<String> uploadBase64(String base64) throws IOException {
        Base64MultipartFile multipartFile = Base64MultipartFile.toMultipartFile(base64);

        String originalFilename = multipartFile.getOriginalFilename();
        Path targetFile = Paths.get(UPLOAD_DIRECTORY, originalFilename);
        multipartFile.transferTo(targetFile);

        String realIP = HostInfo.getClientIpAddress(request);
        String uploadLocation = MessageFormat.format("{0}:{1}", realIP, targetFile.toAbsolutePath());
        log.info(UPLOAD_DIRECTORY, uploadLocation);
        return ResponseWrappers.succeed(uploadLocation);
    }

    @GetMapping("aaa")
    public ResponseWrapper<Object> aaa() {
//        List<Integer> list = Stream.iterate(0, i -> i + 1).limit(10000).collect(Collectors.toList());
////        list.parallelStream().forEach(c -> log.w(c.toString()));
//        list.parallelStream().forEach(c -> ff());
//        System.out.println("1111111111111111111111");
        return ResponseWrappers.succeed();
    }

    public final void ff() {
        {
            String s = "http://localhost:8084/permission/ingress/grant";
            String body = "{\n" +
                    "  \"id\":null,\n" +
                    "  \"orgId\":\"d96ad332f33242099a60397ee99b9b8b\",\n" +
                    "  \"appId\":3211001,\n" +
                    "  \"action\":null,\n" +
                    "  \"resourceCategory\":\"engine\",\n" +
                    "  \"resourceId\":\"1674343025399779329\",\n" +
                    "  \"resourceIdList\":null,\n" +
                    "  \"principleType\":null,\n" +
                    "  \"principleIdList\":null,\n" +
                    "  \"createId\":null,\n" +
                    "  \"createTime\":null,\n" +
                    "  \"updateId\":null,\n" +
                    "  \"updateTime\":null,\n" +
                    "  \"permissionIngressConditionList\":[\n" +
                    "    {\n" +
                    "      \"id\":null,\n" +
                    "      \"permissionId\":null,\n" +
                    "      \"name\":\"v\",\n" +
                    "      \"operator\":\"=\",\n" +
                    "      \"value\":\"394642012\",\n" +
                    "      \"sort\":0,\n" +
                    "      \"createId\":null,\n" +
                    "      \"createTime\":null,\n" +
                    "      \"updateId\":null,\n" +
                    "      \"updateTime\":null\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\":null,\n" +
                    "      \"permissionId\":null,\n" +
                    "      \"name\":\"name\",\n" +
                    "      \"operator\":\"=\",\n" +
                    "      \"value\":\"22\",\n" +
                    "      \"sort\":0,\n" +
                    "      \"createId\":null,\n" +
                    "      \"createTime\":null,\n" +
                    "      \"updateId\":null,\n" +
                    "      \"updateTime\":null\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\":null,\n" +
                    "      \"permissionId\":null,\n" +
                    "      \"name\":\"name\",\n" +
                    "      \"operator\":\"=\",\n" +
                    "      \"value\":\"1\",\n" +
                    "      \"sort\":0,\n" +
                    "      \"createId\":null,\n" +
                    "      \"createTime\":null,\n" +
                    "      \"updateId\":null,\n" +
                    "      \"updateTime\":null\n" +
                    "    }],\n" +
                    "  \"enabled\":null\n" +
                    "}";
            ResponseEntity<String> responseEntity = RestTemplater.create(skipSSLRestTemplate)
                    .contentTypeApplicationJson()
                    .header("Cookie", "lcdpAccessToken=882a4e58ec914ea682ef6bf3a3f74d3b")
                    .uri(s)
                    .body(body)
                    .method(HttpMethod.GET)
                    .retrieve(String.class);
        }

    }





//    /**
//     * 从 request 中获取上传的文件
//     * @warn 后续需要 删除处理文件上传时
//     * 生成的临时文件 ,使用item.delete();
//     * @param request http请求
//     * @param encoding 解码方式,传null默认 utf-8
//     * @return 返回的是一个 FileItem 集合
//     */
//    public List<FileItem> getUploadInputSteam(HttpServletRequest request, String encoding){
//        if (encoding==null || "".equals(encoding)) {
//            encoding = "UTF-8";
//        }
//        List<FileItem> items = new ArrayList<FileItem>();
//        //初始化需要解析文件的几个类
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setHeaderEncoding(encoding);
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return items;
//        }
//        try {
//            //使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合
//            //每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
//            for (FileItem item : list) {
//                //如果fileitem中封装的是普通输入项的数据
//                if (item.isFormField()) {
//// String name = item.getFieldName();
////  //解决普通输入项的数据的中文乱码问题
//// String value = item.getString(encoding);
//// System.out.println(name +"="+value);
//                }else{
//                    String fileName = item.getName();
//                    if (fileName==null||fileName.trim().equals("")) {
//                        continue;
//                    }
//                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，
//                    //如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
//// fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//                    items.add(item);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return items;
//        }
//        return items;
//    }
//    @RequestMapping("/testUpload")
//    @ResponseBody
//    public String testUpload(HttpServletRequest request) throws Exception{
//        //获取文件集合
//        List<FileItem> items = getUploadInputSteam(request, "utf-8");
//        //取第一个做实验
//        FileItem item = items.get(0);
//
//        //上传的文件名
//        String fileName = item.getName();
//        fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//
//        //数据流获取,获得数据流你可以自由解析 , 保存 等操作
//        InputStream in = item.getInputStream();
//
//        //关闭流
//        in.close();
//
//        //最后一定要删除,item的临时文件
//        item.delete();
//        return "success!";
//    }

}
