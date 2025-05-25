package com.glebestraikh.chat.server.database;

import com.glebestraikh.chat.server.connection.Connection;

public record User(String username, Connection connection) {}
