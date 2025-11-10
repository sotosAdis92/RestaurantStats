package org.example.databasetechnologyproject;

import javafx.application.Platform;

import java.util.function.Consumer;

public class customerServiceClass  {
    private static customerServiceClass  instance;
    private Consumer<Void> refreshCallback;
    private customerServiceClass () {}
    public static customerServiceClass  getInstance() {
        if (instance == null) {
            instance = new customerServiceClass ();
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
