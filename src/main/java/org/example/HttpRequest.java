package org.example;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public class HttpRequest {
    Method method;
    String path;
    String protocol;
    HashMap<String, String> headers = new LinkedHashMap<>();
    String body;
}
