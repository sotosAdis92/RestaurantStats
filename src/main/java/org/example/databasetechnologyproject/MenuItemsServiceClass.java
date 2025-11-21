package org.example.databasetechnologyproject;

import javafx.application.Platform;

import java.util.function.Consumer;

public class MenuItemsServiceClass {
    private static MenuItemsServiceClass  instance;
    private Consumer<Void> refreshCallback;
    private MenuItemsServiceClass () {}
    public static MenuItemsServiceClass  getInstance() {
        if (instance == null) {
            instance = new MenuItemsServiceClass();
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
