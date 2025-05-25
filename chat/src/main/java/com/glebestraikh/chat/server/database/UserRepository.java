package com.glebestraikh.chat.server.database;

import com.glebestraikh.chat.server.connection.Connection;

public interface UserRepository {

    void addUser(User user);

    User findUserByUsername(String username);

    User findUserByConnection(Connection connection);

    String[] getUsernames();

    Connection[] getConnections();

    void removeUser(String username);

}