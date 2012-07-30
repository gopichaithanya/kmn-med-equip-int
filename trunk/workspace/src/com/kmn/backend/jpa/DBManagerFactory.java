/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.controller.props.PropertiesController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Hermanto
 */
public class DBManagerFactory implements PropertiesController {
    private EntityManagerFactory fac = null;
    private Properties prop = null;
    
    private static final String SYS_DIR = System.getProperty("user.dir");
    private static final String SYS_SEPARATOR = System.getProperty("file.separator");
    private static final String CONF_DIR = "conf";
    
    private static DBManagerFactory instance = new DBManagerFactory();

    public static DBManagerFactory getInstance() {
        return instance;
    }

    private DBManagerFactory() {
        if(prop == null) {
            prop = new Properties();
            this.load();
        }
    }

    public EntityManagerFactory getEntityManager() {
        Map<String, String> dbProps = new HashMap<String, String>();
        dbProps.put("hibernate.connection.url", prop.getProperty(DB_URL));
        dbProps.put("hibernate.connection.driver_class", prop.getProperty(DB_DRIVER) );
        dbProps.put("hibernate.connection.password", prop.getProperty(DB_PASSWORD).toString());
        dbProps.put("hibernate.connection.username", prop.getProperty(DB_USER_NAME).toString());
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("workspacePU", dbProps);
        
        return fact;
    }

    public void load() {
        try {
            FileInputStream inProp = new FileInputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + DB_FILE);
            prop.load(inProp);
        }
        catch(IOException e) {
        }
    }

    public void store() {
        //
    }

}
