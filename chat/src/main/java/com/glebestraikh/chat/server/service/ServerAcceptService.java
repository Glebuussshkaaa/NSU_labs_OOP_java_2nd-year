package com.glebestraikh.chat.server.service;

import com.glebestraikh.chat.connection.Connection;
import com.glebestraikh.chat.connection.ConnectionFactory;
import com.glebestraikh.chat.dto.DTOFormat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerAcceptService {
    private static final Logger logger = Logger.getLogger(ServerAcceptService.class.getName());
    private final ExecutorService acceptor = Executors.newSingleThreadExecutor();
    private final ServerSocket serverSocket;
    private final int timeout;
    private final DTOFormat dtoFormat;
    private final ServerRegistrationService registerService;

    public ServerAcceptService(ServerSocket serverSocket, int timeout, DTOFormat dtoFormat, ServerRegistrationService registerService) {
        this.serverSocket = serverSocket;
        this.timeout = timeout;
        this.dtoFormat = dtoFormat;
        this.registerService = registerService;
    }

    public void accept() {
        acceptor.execute(new AcceptTask());
    }

    private class AcceptTask implements Runnable {
        @Override
        public void run() {
            while (!acceptor.isShutdown()) {
                Socket clientSocket = accept();
                if (clientSocket == null) {
                    continue;
                }
                logger.info(String.format("Client %s accepted", clientSocket.getRemoteSocketAddress()));

                if (setTimeout(clientSocket)) {
                    continue;
                }
                logger.info(String.format("Set timeout for client %s", clientSocket.getRemoteSocketAddress()));

                Connection connection = createConnection(clientSocket);
                if (connection == null) {
                    continue;
                }
                logger.info(String.format("Create read/write client %s connection", clientSocket.getRemoteSocketAddress()));

                registerService.register(connection);
            }
        }

        private Socket accept() {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to accept client connection", e);
                return null;
            }

            return clientSocket;
        }

        private boolean setTimeout(Socket clientSocket) {
            try {
                clientSocket.setSoTimeout(timeout);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to set socket timeout", e);
                closeResource(clientSocket);
                return true;
            }

            return false;
        }

        private Connection createConnection(Socket clientSocket) {
            Connection connection = ConnectionFactory.newConnection(dtoFormat);
            try {
                connection.connect(clientSocket);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create read/write connection", e);
                closeResource(connection);
                return null;
            }

            return connection;
        }

        private void closeResource(AutoCloseable resource) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to close resource", e);
            }
        }
    }
}

