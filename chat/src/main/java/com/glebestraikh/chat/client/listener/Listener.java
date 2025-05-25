package com.glebestraikh.chat.client.listener;

import com.glebestraikh.chat.client.listener.event.Event;

import java.util.EventListener;

public interface Listener extends EventListener {
    void processEvent(Event event);
}

