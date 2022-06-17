package com.cornello.prototype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

	private final Logger logger = LogManager.getLogger(RequestResponseLoggingInterceptor.class);
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);;
        traceResponse(response);
        return response;
	}
	
    private String getBodyAsJson(String bodyString) {
        if (null == bodyString || bodyString.length() == 0) {
            return null;
        } else {
            if (isValidJSON(bodyString)) {
                return bodyString;
            } else {
                bodyString = bodyString.replace("\"", "\\\"");
                return "\"" + bodyString + "\"";
            }
        }
    }

    private String getRequestBody(byte[] body) {
        if (null != body && body.length > 0) {
            return getBodyAsJson(new String(body, StandardCharsets.UTF_8));
        } else {
            return null;
        }
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        logger.info("request URI : {}", request.getURI());
        //.replaceAll("[?].*", "")
        logger.info("request method : {}", request.getMethod());
        logger.info("request header : {}",request.getHeaders());
        if (!new String(body, StandardCharsets.UTF_8).contains("passWord")){
        	logger.debug("request body : {}", getRequestBody(body));
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        String body = getBodyString(response);
        logger.info("response status code: {}", response.getStatusCode());
        logger.info("response status text: {}", response.getStatusText());
        logger.info("response body : {}", body);
    }

    private String getBodyString(ClientHttpResponse response) {
        try {
            if (null !=response && isReadableResponse(response) ) {
                StringBuilder inputStringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
                String line = bufferedReader.readLine();
                while (line != null) {
                    inputStringBuilder.append(line);
                    inputStringBuilder.append('\n');
                    line = bufferedReader.readLine();
                }
                return inputStringBuilder.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isReadableResponse(ClientHttpResponse response) {
    	if( null != response && null != response.getHeaders().get("Content-Type") ){
    		for (String contentType: response.getHeaders().get("Content-Type")) {
                if (isReadableContentType(contentType)) {
                    return true;
                }
            }
    	}
        return false;
    }

    private boolean isReadableContentType(String contentType) {
        return contentType.startsWith("application/json")
                || contentType.startsWith("text");
    }

    public boolean isValidJSON(final String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
        } catch(IOException e) {
            return false;
        }
        return true;
    }
}

