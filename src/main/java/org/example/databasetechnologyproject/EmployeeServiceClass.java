package org.example.databasetechnologyproject;

import javafx.application.Platform;

import java.util.function.Consumer;

public class EmployeeServiceClass {
    private static EmployeeServiceClass  instance;
    private Consumer<Void> refreshCallback;
    private EmployeeServiceClass () {}
    public static EmployeeServiceClass  getInstance() {
        if (instance == null) {
            instance = new EmployeeServiceClass();
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
