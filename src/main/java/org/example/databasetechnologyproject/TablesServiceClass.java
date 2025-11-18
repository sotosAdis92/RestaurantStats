package org.example.databasetechnologyproject;

import javafx.application.Platform;

import java.util.function.Consumer;

public class TablesServiceClass {
    private static TablesServiceClass  instance;
    private Consumer<Void> refreshCallback;
    private TablesServiceClass () {}
    public static TablesServiceClass  getInstance() {
        if (instance == null) {
            instance = new TablesServiceClass ();
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
