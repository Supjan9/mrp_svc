package org.mrp.config;

import com.sun.net.httpserver.HttpServer;
import de.mkammerer.argon2.Argon2Factory;
import org.mrp.controller.MediaController;
import org.mrp.controller.UserController;
import org.mrp.controller.Router;
import org.mrp.repository.MediaRepository;
import org.mrp.repository.UserRepository;
import org.mrp.security.AuthChecker;
import org.mrp.security.PasswordHasher;
import org.mrp.security.TokenGenerator;
import org.mrp.service.MediaService;
import org.mrp.service.UserService;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.SecureRandom;

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

        SecureRandom secureRandom = new SecureRandom();
        DatabaseManager databaseManager = new DatabaseManager();
        ObjectMapper objectMapper = new ObjectMapper();

        TokenGenerator tokenGenerator = new TokenGenerator(secureRandom);
        PasswordHasher passwordHasher = new PasswordHasher(Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id));
        UserRepository userRepository = new UserRepository(databaseManager);
        MediaRepository mediaRepository = new MediaRepository(databaseManager);
        AuthChecker authChecker = new AuthChecker(userRepository);

        UserService userService = new UserService(userRepository, passwordHasher, tokenGenerator);
        MediaService mediaService = new MediaService(mediaRepository);
        UserController userController = new UserController(userService, authChecker, objectMapper);
        MediaController mediaController = new MediaController(mediaService, authChecker, objectMapper);
        mediaController.registerRoutes(router);
        userController.registerRoutes(router);

        return router;
    }
}