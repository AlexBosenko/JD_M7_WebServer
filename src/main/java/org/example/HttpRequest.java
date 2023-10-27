package org.example;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

@Data
public class HttpRequest {
    private Method method;
    private String path;
    private String protocol;
    private HashMap<String, String> headers = new LinkedHashMap<>();
    private String body;

    public enum Method {
        GET,
        POST
    }

    public static HttpRequest of(String text) {
        HttpRequest httpRequest = new HttpRequest();
        String[] lines = text.replace("\r", "").split("\n");
        if (lines.length > 0) {
            String startLine = lines[0];
            String[] startLineParts = startLine.split(" ");
            httpRequest.setMethod(Method.valueOf(startLineParts[0]));
            httpRequest.setPath(startLineParts[1]);
            httpRequest.setProtocol(startLineParts[2]);

            for (int i = 1; i < lines.length; i++) {
                if (!lines[i].isEmpty()) {
                    String[] headers = lines[i].split(": ");
                    httpRequest.getHeaders().put(headers[0], headers[1]);
                } else {
                    StringJoiner body = new StringJoiner("\n");
                    for (int j = i + 1; j < lines.length; j++) {
                        body.add(lines[j]);
                    }
                    httpRequest.setBody(body.toString());
                    break;
                }
            }
        }
        return httpRequest;
    }
}
