package com.glebestraikh.chat.server.service;

import com.glebestraikh.chat.connection.Connection;
import com.glebestraikh.chat.dto.DTO;
import com.glebestraikh.chat.server.data.User;
import com.glebestraikh.chat.server.data.UserRepository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRegistrationService {
    private static final Logger logger = Logger.getLogger(ServerRegistrationService.class.getName());
    private static final int REGISTRAR_COUNT = 3;
    private final ExecutorService registrars = Executors.newFixedThreadPool(REGISTRAR_COUNT);
    private final UserRepository userRepository;
    private final ServerDTOHandleService serverDTOHandleService;

    public ServerRegistrationService(UserRepository userRepository, ServerDTOHandleService requestHandleService) {
        this.userRepository = userRepository;
        this.serverDTOHandleService = requestHandleService;
    }
    public void register(Connection connection) {
        registrars.execute(new RegisterTask(connection));
    }

    private class RegisterTask implements Runnable {
        private final Connection connection;

        public RegisterTask(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            while (!registrars.isShutdown()) {
                DTO request = receiveRequest();
                if (request == null) {
                    closeConnection(connection);
                    return;
                }

                DTO response = createResponse(request);
                if (!sendResponse(response)) {
                    closeConnection(connection);
                    return;
                }

                if (DTO.isSuccessResponse(response)) {
                    userRepository.addUser(new User(request.getUsername(), connection));
                    sendEvent(DTO.newLoginEvent(request.getUsername()));
                    logger.info(String.format("Client %s is registered", connection.getSocket().getRemoteSocketAddress()));
                    serverDTOHandleService.handle(connection);
                    return;
                }
            }
        }

        private DTO receiveRequest() {
            DTO request;
            try {
                request = connection.receive();
            } catch (IOException e) {
                logger.info("Client not registered");
                logger.log(Level.SEVERE, "Failed to receive request", e);
                return null;
            }

            return request;
        }

        private DTO createResponse(DTO request) {
            if (!DTO.isLoginRequest(request)) {
                return DTO.newErrorResponse(request.getId(), "DTO is not request");
            } else if (!usernameIsCorrect(request)) {
                return DTO.newErrorResponse(request.getId(), "Username format is invalid");
            } else if (usernameIsUsed(request)) {
                return DTO.newErrorResponse(request.getId(), "Username already used");
            }

            return DTO.newSuccessResponse(request.getId());
        }

        private boolean sendResponse(DTO response) {
            try {
                connection.send(response);
                return true;
            } catch (IOException e) {
                logger.info("Client not registered");
                logger.log(Level.SEVERE, "Failed to send response", e);
                return false;
            }
        }

        private boolean usernameIsCorrect(DTO request) {
            return request.getUsername() != null && !request.getUsername().isEmpty();
        }

        private boolean usernameIsUsed(DTO request) {
            return userRepository.findUserByUsername(request.getUsername()) != null;
        }

        private void sendEvent(DTO event) {
            for (Connection userConnection : userRepository.getConnections()) {
                try {
                    userConnection.send(event);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to send event", e);
                }
            }
        }

        private void closeConnection(Connection connection) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to close resource", e);
            }
        }
    }
}
