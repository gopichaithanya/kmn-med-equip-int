/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller.props;

import com.kmn.controller.Confirm;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author Hermanto
 */
public class ServerProperties implements PropertiesController {
    private static final String SYS_DIR = System.getProperty("user.dir");
    private static final String SYS_SEPARATOR = System.getProperty("file.separator");
    private static final String CONF_DIR = "conf";

    private Properties prop = new Properties();

    private Confirm confirm;
    private FileInputStream inProp;
    private FileOutputStream outProp;
    private String username;
    private String password;
    private String ip;
    private String port;

    public ServerProperties(Confirm confirm) {
        this.confirm = confirm;
    }

    @Override
    public void load() {
        try {
            inProp = new FileInputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + SERVER_FILE);

            prop.load(inProp);
            this.setUsername(prop.getProperty(SERVER_USER_NAME));
            this.setPassword(prop.getProperty(SERVER_PASSWORD));
            this.setIp(prop.getProperty(SERVER_IP));
            this.setPort(prop.getProperty(SERVER_PORT));

        } catch (IOException ex) {
            confirm.onError(ex);
        }
    }

    @Override
    public void store() {
        prop.setProperty(SERVER_USER_NAME, this.getUsername());
        prop.setProperty(SERVER_PASSWORD, this.getPassword());
        prop.setProperty(SERVER_IP, this.getIp());
        prop.setProperty(SERVER_PORT, this.getPort());
        
        try {
            if(inProp != null) inProp.close();

            outProp = new FileOutputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + SERVER_FILE);
            outProp.flush();
            prop.store(outProp, null);
            outProp.close();

        } catch (IOException ex) {
            confirm.onError(ex);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
