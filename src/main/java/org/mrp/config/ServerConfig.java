package org.mrp.config;

import com.sun.net.httpserver.HttpServer;
import org.mrp.controller.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;


public class ServerConfig {
    private static final int PORT = 8080;
    private static final String ROOT_PATH = "/";

    public static HttpServer startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext(ROOT_PATH, new Controller());
        server.setExecutor(null);
        server.start();


        System.out.println("Server running on port 8080");
        return server;
    }

}
