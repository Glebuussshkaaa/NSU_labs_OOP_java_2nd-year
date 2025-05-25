package com.glebestraikh.chat.client.service;

import com.glebestraikh.chat.client.listener.ListeningSupport;
import com.glebestraikh.chat.client.listener.event.ErrorEvent;
import com.glebestraikh.chat.server.connection.Connection;
import com.glebestraikh.chat.server.dto.DTO;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRegistrationService {
    private final ExecutorService registrar = Executors.newSingleThreadExecutor();
    private final ListeningSupport listeningSupport;

    public UserRegistrationService(ListeningSupport listeningSupport) {
        this.listeningSupport = listeningSupport;
    }

    public boolean register(Connection connection, String username) {
        Future<DTO> futureResponse = registrar.submit(new RegisterTask(connection, username));

        DTO response;
        try {
            response = futureResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            listeningSupport.notifyListeners(new ErrorEvent("Connection error"));
            return false;
        }

        if (DTO.isErrorResponse(response)) {
            listeningSupport.notifyListeners(new ErrorEvent(response.getMessage()));
            return false;
        }

        return true;
    }

    public void shutdown() {
        registrar.shutdownNow();
    }

    private class RegisterTask implements Callable<DTO> {
        private final Connection connection;
        private final String username;

        public RegisterTask(Connection connection, String username) {
            this.connection = connection;
            this.username = username;
        }

        @Override
        public DTO call() throws Exception {
            DTO request = DTO.newLoginRequest(username);
            connection.send(request);

            while (!registrar.isShutdown()) {
                DTO response = connection.receive();

                if (DTO.isResponse(response)) {
                    return response;
                }
            }

            return null;
        }
    }
}