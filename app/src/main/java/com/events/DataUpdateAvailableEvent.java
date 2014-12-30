package com.events;

/**
 * Created by fbaue on 12/30/14.
 */
public class DataUpdateAvailableEvent {

    public final boolean updateAvailable;

    public DataUpdateAvailableEvent(final boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
