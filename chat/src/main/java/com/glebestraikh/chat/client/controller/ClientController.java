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
    private final ListenerManager listenerManager = new ListenerManager();
    private Connection connection;
    private ClientDTOHandleService clientDTOHandleService;

    public void addListener(Listener listener) {
        listenerManager.addListener(listener);
    }

    public void removeListener(Listener listener) {
        listenerManager.removeListener(listener);
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
            listenerManager.notifyListeners(new ErrorEvent("Server is not available"));
            return false;
        }

        return true;
    }

    public boolean login(String username) {
        if (connection == null) {
            return false;
        }

        ClientRegistrationService clientRegistrationService = new ClientRegistrationService(listenerManager);
        boolean registerResult = clientRegistrationService.register(connection, username);
        clientRegistrationService.shutdown();

        if (registerResult) {
            clientDTOHandleService = new ClientDTOHandleService(username, connection, listenerManager);
        }

        return registerResult;
    }

    public void handleDTO() {
        clientDTOHandleService.handle();
    }

    public String[] getUsers() {
        if (clientDTOHandleService == null) {
            listenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return new String[]{};
        }

        DTO response = clientDTOHandleService.sendRequest(DTO.newUserListRequest());

        if (response.getSubtype() == DTO.Subtype.ERROR) {
            listenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
            return new String[]{};
        }

        return response.getUsers();
    }

    public void sendNewMessage(String message) {
        if (clientDTOHandleService == null) {
            listenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = clientDTOHandleService.sendRequest(DTO.newNewMessageRequest(message));

        if (response.getSubtype() == Subtype.ERROR) {
            listenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
        }
    }

    public void logout() {
        if (clientDTOHandleService == null) {
            listenerManager.notifyListeners(new ErrorEvent("Client not registered"));
            return;
        }

        DTO response = clientDTOHandleService.sendRequest(DTO.newLogoutRequest());

        if (response.getSubtype() == Subtype.ERROR) {
            listenerManager.notifyListeners(new ErrorEvent(response.getMessage()));
        }

        clientDTOHandleService.shutdown();
    }
}
