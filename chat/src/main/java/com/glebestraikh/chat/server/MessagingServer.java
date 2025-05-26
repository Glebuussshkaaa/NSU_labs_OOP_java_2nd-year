package com.glebestraikh.chat.server;

import com.glebestraikh.chat.server.dto.DTOFormat;
import com.glebestraikh.chat.server.database.UserRepository;
import com.glebestraikh.chat.server.database.UserRepositoryInMemory;
import com.glebestraikh.chat.server.service.AcceptService;
import com.glebestraikh.chat.server.service.RegisterService;
import com.glebestraikh.chat.server.service.RequestHandleService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class MessagingServer {
    public void start(InetAddress address, int port, int timeout, DTOFormat dtoFormat) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 0, address);
        UserRepository userRepository = new UserRepositoryInMemory();
        RequestHandleService requestHandleService = new RequestHandleService(userRepository);
        RegisterService registerService = new RegisterService(userRepository, requestHandleService);
        AcceptService acceptService = new AcceptService(serverSocket, timeout, dtoFormat, registerService);
        acceptService.accept();
        System.out.printf("✅ Сервер запущен на %s:%d, timeout: %d мс, формат DTO: %s%n",
                address.getHostAddress(), port, timeout, dtoFormat);
    }
}
