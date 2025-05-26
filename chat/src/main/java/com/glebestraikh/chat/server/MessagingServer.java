package com.glebestraikh.chat.server;

import com.glebestraikh.chat.dto.DTOFormat;
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
        ServerDTOHandleService ServerDTOHandleService = new ServerDTOHandleService(userRepository);
        ServerRegistrationService ServerRegistrationService = new ServerRegistrationService(userRepository, ServerDTOHandleService);
        ServerAcceptService ServerAcceptService = new ServerAcceptService(serverSocket, timeout, dtoFormat, ServerRegistrationService);
        ServerAcceptService.accept();
        System.out.printf("✅ Сервер запущен на %s:%d, timeout: %d мс, формат DTO: %s%n",
                address.getHostAddress(), port, timeout, dtoFormat);
    }
}
