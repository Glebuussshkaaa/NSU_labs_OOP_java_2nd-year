package com.glebestraikh.chat.connection;

import com.glebestraikh.chat.dto.DTO;
import com.glebestraikh.chat.util.XmlUtils;

import java.io.*;
import java.net.Socket;
import javax.xml.bind.JAXBException;
public class XmlFileConnection implements Connection {
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
    public Socket getSocket() {
        return clientSocket;
    }

    @Override
    public void connect(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void send(DTO dto) throws IOException {
        if (out == null) {
            throw new IOException("Connection not established");
        }

        String xml;
        try {
            xml = XmlUtils.dtoToXml(dto);
        } catch (JAXBException e) {
            throw new IOException(String.format("Invalid DTO format: %s", e));
        }

        synchronized (out) {
            out.write(xml);
            out.newLine();
            out.flush();
        }
    }

    @Override
    public DTO receive() throws IOException {
        if (in == null) {
            throw new IOException("Connection not established");
        }

        String xml = readXml();

        try {
            return XmlUtils.xmlToDto(xml);
        } catch (JAXBException e) {
            throw new IOException(String.format("Invalid DTO format: %s", e));
        }
    }

    private String readXml() throws IOException {
        StringBuilder xmlFile = new StringBuilder();

        synchronized (in) {
            while (true) {
                String line = in.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }

                xmlFile.append(line);
                xmlFile.append('\n');
            }
        }

        return xmlFile.toString();
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