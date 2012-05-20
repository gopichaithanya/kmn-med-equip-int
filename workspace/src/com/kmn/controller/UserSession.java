/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller;

import com.kmn.backend.entity.SecUser;
import java.util.Date;

/**
 *
 * @author Hermanto
 */
public class UserSession {
    private static UserSession instance = new UserSession();
    private SecUser secuser;
    private Date datelogin;
    
    private UserSession() {
    }

    public static UserSession getInstance() {
        return instance;
    }

    public void setSession(SecUser secuser, Date datelogin) {
        this.secuser = secuser;
        this.datelogin = datelogin;
    }

    public SecUser getSecuser() {
        return secuser;
    }

    public void setSecuser(SecUser secuser) {
        this.secuser = secuser;
    }

    public Date getDatelogin() {
        return datelogin;
    }

    public void setDatelogin(Date datelogin) {
        this.datelogin = datelogin;
    }
}
