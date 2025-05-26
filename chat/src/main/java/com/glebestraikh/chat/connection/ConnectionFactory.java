package com.glebestraikh.chat.connection;

import com.glebestraikh.chat.util.DTOFormat;

public class ConnectionFactory {
    private ConnectionFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection newConnection(DTOFormat dtoFormat) {
        return switch (dtoFormat) {
            case JAVA_OBJECT -> new JavaObjectConnection();
            case XML_FILE -> new XmlFileConnection();
        };
    }
}
