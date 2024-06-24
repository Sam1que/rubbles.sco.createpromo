package ru.a366.sco_createpromo.http;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ru.a366.sco_createpromo.http.MultiReadHttpServletRequest multiReadRequest = new ru.a366.sco_createpromo.http.MultiReadHttpServletRequest((HttpServletRequest) request);

        String body = readBody(multiReadRequest);
        log.info("Request Body: {}", body);

        chain.doFilter(multiReadRequest, response);
    }

    private String readBody(ru.a366.sco_createpromo.http.MultiReadHttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder body = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }
}
