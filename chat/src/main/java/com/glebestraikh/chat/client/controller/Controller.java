package com.glebestraikh.chat.client.controller;

import com.glebestraikh.chat.client.listener.Listener;
import com.glebestraikh.chat.client.listener.ListeningSupport;
import com.glebestraikh.chat.client.listener.event.ErrorEvent;
import com.glebestraikh.chat.client.service.DTOHandleService;
import com.glebestraikh.chat.client.service.UserRegistrationService;
import com.glebestraikh.chat.server.ServerConfig;
import com.glebestraikh.chat.server.connection.Connection;
import com.glebestraikh.chat.server.connection.ConnectionFactory;
import com.glebestraikh.chat.server.dto.DTO;
import com.glebestraikh.chat.server.dto.DTO.Subtype;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Controller {
    private final ListeningSupport listeningSupport = new ListeningSupport();
    private Connection connection;
    private DTOHandleService dtoHandleService;

    public void addListener(Listener listener) {
        listeningSupport.addListener(listener);
    }

    public void removeListener(Listener listener) {
        listeningSupport.removeListener(listener);
    }

    public boolean connect(InetAddress serverAddress, int serverPort) {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ignores) {}
        }

        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);
            connection = ConnectionFactory.newConnection(ServerConfig.getDTOFormat());
            connection.connect(clientSocket);
        } catch (IOException e) {
            listeningSupport.notifyListeners(new ErrorEvent("Server is not available"));
            return false;
        }

        return true;
    }

    public boolean login(String username) {
        if (connection == null) {
            return false;
        }

        UserRegistrationService registerService = new UserRegistrationService(listeningSupport);
        boolean registerResult = registerService.register(connection, username);
        registerService.shutdown();

        if (registerResult) {
            dtoHandleService = new DTOHandleService(username, connection, listeningSupport);
        }

        return registerResult;
    }

    public void handleDTO() {
        dtoHandleService.handle();
    }

    public String[] getUsers() {
        if (dtoHandleService == null) {
            listeningSupport.notifyListeners(new ErrorEvent("Client not registered"));
            return new String[]{};
        }

        DTO response = dtoHandleService.sendRequest(DTO.newUserListRequest());

        if (response.getSubtype() == DTO.Subtype.ERROR) {
            listeningSupport.notifyListeners(new ErrorEvent(response.getMessage()));
            return new String[]{};
        }

        return response.getUsers();
    }

    public void sendNewMessage(String message) {
        if (dtoHandleService == null) {
            listeningSupport.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = dtoHandleService.sendRequest(DTO.newNewMessageRequest(message));

        if (response.getSubtype() == Subtype.ERROR) {
            listeningSupport.notifyListeners(new ErrorEvent(response.getMessage()));
        }
    }

    public void logout() {
        if (dtoHandleService == null) {
            listeningSupport.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = dtoHandleService.sendRequest(DTO.newLogoutRequest());

        if (response.getSubtype() == Subtype.ERROR) {
            listeningSupport.notifyListeners(new ErrorEvent(response.getMessage()));
        }

        dtoHandleService.shutdown();
    }
}
