package com.glebestraikh.chat.client.listener.event;

public record NewMessageEvent(String username, boolean isCurrentUser, String message) implements Event {
}