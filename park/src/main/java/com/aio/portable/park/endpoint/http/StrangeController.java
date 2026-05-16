package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component("strange2/api")
@LogRecord(enabled = false)
public class StrangeController implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");
        PrintWriter out = response.getWriter();
        out.print("<h1>你好，HttpRequestHandler</h1>");
        out.close();
    }
}
