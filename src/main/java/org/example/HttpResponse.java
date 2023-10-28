package org.example;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

@Data
public class HttpResponse {
    private String protocol = "HTTP/1.1";
    private int statusCode;
    private String statusText;
    private HashMap<String, String> headers = new LinkedHashMap<>();
    private String body;

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner("\n");
        
        result.add(protocol + " " + statusCode + " " + statusText);

        for (Map.Entry<String, String> keyValue : headers.entrySet()) {
            result.add(keyValue.getKey() + ": " + keyValue.getValue());
        }

        result.add("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length);
        result.add("Content-Type: " + "text/html; charset=utf-8");

        result.add("");
        result.add(body);

        return result.toString();
    }
}
