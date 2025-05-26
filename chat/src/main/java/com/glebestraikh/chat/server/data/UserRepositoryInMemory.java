package com.glebestraikh.chat.server.data;

import com.glebestraikh.chat.connection.Connection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryInMemory implements UserRepository {
    private final Set<User> users = new HashSet<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (Objects.equals(user.username(), username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByConnection(Connection connection) {
        for (User user : users) {
            if (Objects.equals(user.connection(), connection)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public String[] getUsernames() {
        return users.stream()
                .map(User::username)
                .collect(Collectors.toSet())
                .toArray(String[]::new);
    }

    @Override
    public Connection[] getConnections() {
        return users.stream()
                .map(User::connection)
                .collect(Collectors.toSet())
                .toArray(Connection[]::new);
    }

    @Override
    public void removeUser(String username) {
        User user = findUserByUsername(username);

        if (user != null) {
            users.remove(user);
        }
    }
}