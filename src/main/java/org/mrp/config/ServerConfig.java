package org.mrp.config;

import com.sun.net.httpserver.HttpServer;
import org.mrp.controller.UserController;
import org.mrp.controller.Router;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerConfig {
    private static final int PORT = 8080;
    private static final int BACKLOG = 0;
    private static final String ROOT_PATH = "/";
    private static final String START_MESSAGE = "Server running on port ";

    public static void startServer() throws IOException {
        Router router = createRouter();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
        server.createContext(ROOT_PATH, router);
        server.setExecutor(null);
        server.start();

        System.out.println(START_MESSAGE + PORT);
    }

    private static Router createRouter() {
        Router router = new Router();

        UserController userController = new UserController();
        userController.registerRoutes(router);

        return router;
    }
}