package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(10000)) {
            while (true) {
                System.out.println("Wait for connection...");
                Socket connection = socket.accept();
                System.out.println("Client connection created...");

                String requestText = readAll(connection);
                System.out.println(requestText);
                HttpRequest httpRequest = HttpRequest.of(requestText);
                System.out.println("httpRequest = " + httpRequest);
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
}