package com.glebestraikh.chat.client.listener;

import com.glebestraikh.chat.client.listener.event.Event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ListeningSupport {
    private final Set<Listener> listeners = new HashSet<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void addListeners(Collection<? extends Listener> listeners) {
        this.listeners.addAll(listeners);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(Event event) {
        for (Listener listener : listeners) {
            listener.processEvent(event);
        }
    }

    public Set<Listener> getListeners() {
        return listeners;
    }

}
