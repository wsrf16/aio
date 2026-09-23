package com.aio.portable.swiss.suite.net.tcp.http;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class HttpResponseSugar {
    public static void setXLSXResponse(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    public static ResponseEntity<Resource> downloadFile(String baseDirectory, String fileName) {
        try {
            // 1. 安全校验：防止路径穿越漏洞 (Path Traversal)
            // 例如防止用户传入 "../../etc/passwd" 下载系统敏感文件
            Path basePath = Paths.get(baseDirectory).toAbsolutePath().normalize();
            Path filePath = basePath.resolve(fileName).normalize();

            if (!filePath.startsWith(basePath)) {
                return ResponseEntity.badRequest().body(null); // 非法路径
            }

            File file = filePath.toFile();
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.notFound().build(); // 文件不存在
            }

            // 2. 加载文件为 Resource
            Resource resource = new FileSystemResource(file);

            // 3. 处理中文文件名乱码问题 (RFC 5987 标准)
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20"); // 将空格转为 %20

            // 4. 探测文件的 Content-Type (如 application/pdf, image/png 等)
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // 默认二进制流
            }

            // 5. 构建并返回 ResponseEntity
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName)
                    .contentLength(file.length())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
