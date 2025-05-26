package com.glebestraikh.chat.client.controller;

import com.glebestraikh.chat.client.listener.Listener;
import com.glebestraikh.chat.client.listener.ListenerManager;
import com.glebestraikh.chat.client.listener.event.ErrorEvent;
import com.glebestraikh.chat.client.service.ClientDTOHandleService;
import com.glebestraikh.chat.client.service.ClientRegistrationService;
import com.glebestraikh.chat.util.ServerConfig;
import com.glebestraikh.chat.connection.Connection;
import com.glebestraikh.chat.connection.ConnectionFactory;
import com.glebestraikh.chat.dto.DTO;
import com.glebestraikh.chat.dto.DTO.Subtype;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientController {
    private final ListenerManager ListenerManager = new ListenerManager();
    private Connection connection;
    private ClientDTOHandleService dtoHandleService;

    public void addListener(Listener listener) {
        ListenerManager.addListener(listener);
    }

    public void removeListener(Listener listener) {
        ListenerManager.removeListener(listener);
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
            ListenerManager.notifyListeners(new ErrorEvent("Server is not available"));
            return false;
        }

        return true;
    }

    public boolean login(String username) {
        if (connection == null) {
            return false;
        }

        ClientRegistrationService registerService = new ClientRegistrationService(ListenerManager);
        boolean registerResult = registerService.register(connection, username);
        registerService.shutdown();

        if (registerResult) {
            dtoHandleService = new ClientDTOHandleService(username, connection, ListenerManager);
        }

        return registerResult;
    }

    public void handleDTO() {
        dtoHandleService.handle();
    }

    public String[] getUsers() {
        if (dtoHandleService == null) {
            ListenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return new String[]{};
        }

        DTO response = dtoHandleService.sendRequest(DTO.newUserListRequest());

        if (response.getSubtype() == DTO.Subtype.ERROR) {
            ListenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
            return new String[]{};
        }

        return response.getUsers();
    }

    public void sendNewMessage(String message) {
        if (dtoHandleService == null) {
            ListenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = dtoHandleService.sendRequest(DTO.newNewMessageRequest(message));

        if (response.getSubtype() == Subtype.ERROR) {
            ListenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
        }
    }

    public void logout() {
        if (dtoHandleService == null) {
            ListenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = dtoHandleService.sendRequest(DTO.newLogoutRequest());

        if (response.getSubtype() == Subtype.ERROR) {
            ListenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
        }

        dtoHandleService.shutdown();
    }
}
