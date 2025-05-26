package com.glebestraikh.chat.server;

import com.glebestraikh.chat.util.DTOFormat;
import com.glebestraikh.chat.server.data.UserRepository;
import com.glebestraikh.chat.server.data.UserRepositoryInMemory;
import com.glebestraikh.chat.server.service.ServerAcceptService;
import com.glebestraikh.chat.server.service.ServerDTOHandleService;
import com.glebestraikh.chat.server.service.ServerRegistrationService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class MessagingServer {
    public void start(InetAddress address, int port, int timeout, DTOFormat dtoFormat) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 0, address);

        UserRepository userRepository = new UserRepositoryInMemory();

        ServerDTOHandleService serverDTOHandleService = new ServerDTOHandleService(userRepository);

        ServerRegistrationService serverRegistrationService = new ServerRegistrationService(userRepository, serverDTOHandleService);

        ServerAcceptService serverAcceptService = new ServerAcceptService(serverSocket, timeout, dtoFormat, serverRegistrationService);

        serverAcceptService.accept();

        System.out.printf("✅ Сервер запущен на %s:%d, timeout: %d мс, формат DTO: %s%n",
                address.getHostAddress(), port, timeout, dtoFormat);
    }
}
