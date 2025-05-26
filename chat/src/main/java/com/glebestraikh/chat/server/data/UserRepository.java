package com.glebestraikh.chat.server.data;

import com.glebestraikh.chat.connection.Connection;

public interface UserRepository {
    void addUser(User user);

    User findUserByUsername(String username);

    User findUserByConnection(Connection connection);

    String[] getUsernames();

    Connection[] getConnections();

    void removeUser(String username);

}