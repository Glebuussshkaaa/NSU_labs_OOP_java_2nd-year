package com.glebestraikh.chat.client.listener;

import com.glebestraikh.chat.client.listener.event.Event;

import java.util.HashSet;
import java.util.Set;

public class ListenerManager {
    private final Set<Listener> listeners = new HashSet<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(Event event) {
        for (Listener listener : listeners) {
            listener.processEvent(event);
        }
    }
}
