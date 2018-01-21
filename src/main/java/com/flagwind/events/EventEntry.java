package com.flagwind.events;

import java.util.function.Consumer;

public class EventEntry<T extends EventArgs> {
    private String type;

    private Consumer<T> listener;

    private Object scope;

    public boolean once;

    public EventEntry(String type, Consumer<T> listener, Object scope, boolean once) {
        this.type = type;
        this.listener = listener;
        this.scope = scope;
        this.once = once;
    }

    public String getType() {
        return type;
    }

    public Consumer<T> getListener() {
        return listener;
    }

    public Object getScope() {
        return scope;
    }

    public boolean isOnce() {
        return once;
    }
}
