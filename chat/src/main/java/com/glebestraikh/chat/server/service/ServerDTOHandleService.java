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

public class ServerDTOHandleService {
    private static final Logger logger = Logger.getLogger(ServerDTOHandleService.class.getName());
    private static final int HANDLER_COUNT = 3;
    private final ExecutorService handlers = Executors.newFixedThreadPool(HANDLER_COUNT);
    private final UserRepository userRepository;

    public ServerDTOHandleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handle(Connection connection) {
        handlers.execute(new RequestHandleTask(connection));
    }

    private class RequestHandleTask implements Runnable {
        private final User user;

        public RequestHandleTask(Connection connection) {
            this.user = userRepository.findUserByConnection(connection);
        }

        @Override
        public void run() {
            if (user == null) {
                return;
            }

            DTO request = receiveRequest();
            if (request == null) {
                removeUser();
                return;
            }

            DTO response = createResponse(request);
            if (!sendResponse(response)) {
                removeUser();
                return;
            }

            if (executeRequestAction(request)) {
                handlers.execute(this);
            }
        }

        private DTO receiveRequest() {
            DTO request;
            try {
                request = user.connection().receive();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to receive request", e);
                return null;
            }

            return request;
        }

        private DTO createResponse(DTO request) {
            DTO response;
            if (DTO.isNewMessageRequest(request)) {
                response = DTO.newSuccessResponse(request.getId());
            } else if (DTO.isUserListRequest(request)) {
                response = DTO.newSuccessResponse(request.getId(), userRepository.getUsernames());
            } else if (DTO.isLogoutRequest(request)) {
                response = DTO.newSuccessResponse(request.getId());
            } else {
                response = DTO.newErrorResponse(request.getId(), "DTO is not request");
            }
            return response;
        }

        private boolean sendResponse(DTO response) {
            try {
                user.connection().send(response);
                return true;
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to send response", e);
                return false;
            }
        }

        private boolean executeRequestAction(DTO request) {
            if (DTO.isNewMessageRequest(request)) {
                sendEvent(DTO.newNewMessageEvent(user.username(), request.getMessage()));
            } else if (DTO.isLogoutRequest(request)) {
                removeUser();
                return false;
            }

            return true;
        }

        private void sendEvent(DTO event) {
            for (Connection connection : userRepository.getConnections()) {
                try {
                    connection.send(event);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to send event", e);
                }
            }
        }

        private void removeUser() {
            if (user == null) {
                return;
            }

            userRepository.removeUser(user.username());
            sendEvent(DTO.newLogoutEvent(user.username()));
            logger.info(String.format("Client %s disabled", user.connection().getSocket().getRemoteSocketAddress()));
            closeConnection(user.connection());
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
