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
        System.out.println("text = " + text);
        HttpRequest request = new HttpRequest();

        String[] lines = text.split("\n");
        if (lines.length > 0) {
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].replace("\r", "");
            }

            String startLine = lines[0];
            String[] startLineParts = startLine.split(" ");
            request.setMethod(Method.valueOf(startLineParts[0]));
            request.setPath(startLineParts[1]);
            request.setProtocol(startLineParts[2]);

            for (int i = 1; i < lines.length; i++) {
                if (!lines[i].isEmpty()) {
                    String[] headers = lines[i].split(": ");
                    request.getHeaders().put(headers[0], headers[1]);
                } else {
                    StringJoiner body = new StringJoiner("\n");
                    for (int j = i + 1; j < lines.length; j++) {
                        body.add(lines[j]);
                    }
                    request.setBody(body.toString());
                    break;
                }
            }
        }
        return request;
    }
}
