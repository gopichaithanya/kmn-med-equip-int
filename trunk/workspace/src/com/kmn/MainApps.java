/*
 * MainApps.java
 */

package com.kmn;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class MainApps extends SingleFrameApplication {
    private MainView mainView;
    /**
     * At startup create and show the main frame of the application.
     */

    @Override
    protected void startup() {
        this.mainView =  new MainView(this);
        show(mainView);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MainApps
     */
    public static MainApps getApplication() {
        return Application.getInstance(MainApps.class);
    }

    public void close() {
        System.exit(100);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MainApps.class, args);
    }
}