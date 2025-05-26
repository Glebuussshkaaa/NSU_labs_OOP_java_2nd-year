package com.glebestraikh.chat.connection;

import com.glebestraikh.chat.dto.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JavaObjectConnection implements Connection {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    @Override
    public Socket getSocket() {
        return clientSocket;
    }

    @Override
    public void connect(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void send(DTO dto) throws IOException {
        if (out == null) {
            throw new IOException("Connection not established");
        }

        synchronized (out) {
            out.writeObject(dto);
        }
    }

    @Override
    public DTO receive() throws IOException {
        if (in == null) {
            throw new IOException("Connection not established");
        }

        try {
            synchronized (in) {
                return (DTO) in.readObject();
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new IOException(String.format("Invalid DTO format: %s", e));
        }
    }

    @Override
    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }
}