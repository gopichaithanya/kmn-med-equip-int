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
public class DatabaseProperties implements PropertiesController {
    private static final String SYS_DIR = System.getProperty("user.dir");
    private static final String SYS_SEPARATOR = System.getProperty("file.separator");
    private static final String CONF_DIR = "conf";
    
    private Properties prop = new Properties();

    private Confirm confirm;
    private FileInputStream inProp;
    private FileOutputStream outProp;
    private String username;
    private String password;
    private String driver;
    private String url;
    private String provider;

    public DatabaseProperties(Confirm confirm){
        this.confirm = confirm;
    }

    //@Override
    public void load() {
        try {
            inProp = new FileInputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + DB_FILE);
            
            prop.load(inProp);            
            this.setUsername(prop.getProperty(DB_USER_NAME));
            this.setPassword(prop.getProperty(DB_PASSWORD));
            this.setDriver(prop.getProperty(DB_DRIVER));
            this.setUrl(prop.getProperty(DB_URL));
            this.setProvider(prop.getProperty(DB_PROVIDER));

        } catch (IOException ex) {
            confirm.onError(ex);
        }
    }

    //@Override
    public void store() {
        prop.setProperty(DB_USER_NAME, this.getUsername());
        prop.setProperty(DB_PASSWORD, this.getPassword());
        prop.setProperty(DB_DRIVER, this.getDriver());
        prop.setProperty(DB_URL, this.getUrl());
        prop.setProperty(DB_PROVIDER, this.getProvider());
        
        try {
            if(inProp != null) inProp.close();
            
            outProp = new FileOutputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + DB_FILE);
            outProp.flush();
            prop.store(outProp, null);
            outProp.close();
            
        } catch (IOException ex) {
            confirm.onError(ex);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
