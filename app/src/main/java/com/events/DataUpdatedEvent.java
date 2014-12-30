package com.events;


public class DataUpdatedEvent {

    boolean success;

    public DataUpdatedEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccessFull() {
        return success;
    }

}
