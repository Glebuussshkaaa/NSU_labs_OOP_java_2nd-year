package com.glebestraikh.chat.server;

import com.glebestraikh.chat.server.dto.DTOFormat;
import com.glebestraikh.chat.util.ServerConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.io.InputStream;
import java.util.logging.LogManager;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        try (InputStream config = ServerApp.class.getResourceAsStream("/logging.properties")) {
            if (config == null) {
                throw new java.io.FileNotFoundException("Could not find logging.properties in resources.");
            }
            LogManager.getLogManager().readConfiguration(config);
        } catch (Exception e) {
            System.err.println(STR."Failed to load logging.properties: \{e.getMessage()}");
            System.exit(1);
        }

        InetAddress address = ServerConfig.getAddress();
        int port = ServerConfig.getPort();
        int timeout = ServerConfig.getTimeout();
        DTOFormat dtoFormat = ServerConfig.getDTOFormat();

        MessagingServer MessagingServer = new MessagingServer();
        MessagingServer.start(address, port, timeout, dtoFormat);
    }
}