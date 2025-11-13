package org.example.databasetechnologyproject;

import javafx.application.Platform;

import java.util.function.Consumer;

public class ReservationServiceClass {
    private static ReservationServiceClass  instance;
    private Consumer<Void> refreshCallback;
    ReservationServiceClass(){}
    public static ReservationServiceClass  getInstance() {
        if (instance == null) {
            instance = new ReservationServiceClass ();
        }
        return instance;
    }
    public void setRefreshCallback(Consumer<Void> refreshCallback) {
        this.refreshCallback = refreshCallback;
    }
    public void triggerRefresh() {
        if (refreshCallback != null) {
            Platform.runLater(() -> refreshCallback.accept(null));
        }
    }
}
