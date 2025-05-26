package com.glebestraikh.chat.server.data;

import com.glebestraikh.chat.connection.Connection;

public record User(String username, Connection connection) {}
