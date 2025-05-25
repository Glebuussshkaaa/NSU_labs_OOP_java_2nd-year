package com.glebestraikh.chat.server.connection;

import com.glebestraikh.chat.server.dto.DTO;

import java.io.Closeable;
import java.net.Socket;
import java.io.IOException;

public interface Connection extends Closeable {
    Socket getSocket();

    void connect(Socket clientSocket) throws IOException;

    void send(DTO dto) throws IOException;

    DTO receive() throws IOException;
}