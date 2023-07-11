package com.example.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getContentAsString(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int responseCode = response.getStatus();

    	
        ObjectNode logObject = objectMapper.createObjectNode();
        logObject.put("method", method);
        logObject.put("requestURI", requestURI);
        logObject.set("requestPayload", parseJsonString(requestBody));
        logObject.put("responseCode", responseCode);
        logObject.set("response", parseJsonString(responseBody));
        logObject.put("timeTaken", timeTaken);

        String formattedLogMessage = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logObject);

        LOGGER.info(formattedLogMessage);

        responseWrapper.copyBodyToResponse();
    }

    private String getContentAsString(byte[] contentAsByteArray, String characterEncoding) {
        if (contentAsByteArray == null || contentAsByteArray.length == 0) {
            return "";
        }
        return new String(contentAsByteArray, StandardCharsets.UTF_8);
    }

    private JsonNode parseJsonString(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            return objectMapper.getNodeFactory().textNode(jsonString);
        }
    }
}