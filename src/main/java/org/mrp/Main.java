package org.mrp;

import com.sun.net.httpserver.HttpServer;
import org.mrp.config.ServerConfig;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main() throws IOException {

        ServerConfig.startServer();

    }
}
