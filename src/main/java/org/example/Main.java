package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(10000)) {
            while (true) {
                System.out.println("Wait for connection...");
                Socket connection = socket.accept();
                System.out.println("Client connection created...");

                String request = readAll(connection);
                System.out.println(request);

            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readAll(Socket connection) throws InterruptedException, IOException {
        Thread.sleep(1000);
        byte[] buffer = new byte[20 * 1024];
        InputStream is = connection.getInputStream();
        int len = 0;
        while (is.available() > 0) {
            int read = is.read(buffer, len, is.available());
            len += read;

            Thread.sleep(1000);
        }
        return new String(buffer, 0, len);
    }

    private static HttpRequest getHttpRequest(String request) {
        HttpRequest httpRequest = new HttpRequest();
        String[] lines = request.replace("\r", "").split("\n");
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