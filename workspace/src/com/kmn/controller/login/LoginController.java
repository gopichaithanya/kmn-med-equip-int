/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.controller.login;

import com.kmn.Login;
import com.kmn.MainView;
import com.kmn.backend.entity.SecUser;
import com.kmn.backend.jpa.SecUserJpaController;
import com.kmn.controller.Confirm;
import com.kmn.controller.UserSession;
import com.kmn.controller.menu.MainViewController;
import java.util.Date;

/**
 *
 * @author Hermanto
 */
public class LoginController {

    private SecUserJpaController usrJpa;
    private MainViewController controller;
    private SecUser secuser;
    private MainView mainview;

    public LoginController(MainView mainview) {
        this.mainview = mainview;
        this.controller = new MainViewController(mainview);
        this.usrJpa = new SecUserJpaController();
    }

    public void processLogin(Login login, Confirm confirm) {
        String username = login.getjUsername().getText();
        String password = new String(login.getjPassword().getPassword());

        if (username.trim().isEmpty()) {
            confirm.onWarning("Please Input Username");
            return;
        } else if (password.isEmpty()) {
            confirm.onWarning("Please Input Password");
            return;
        }

        this.secuser = this.usrJpa.findByUserName(username);
        
        if (this.secuser == null) {
            confirm.onWarning("Invalid Login"+ "\n" +"Please check user name");
            return;
        } else if (!this.secuser.getUsrPassword().equals(password)) {
            confirm.onWarning("Invalid Login" + "\n" + "Please check your password");
            return;
        }

        confirm.onSuccess();
    }

    public SecUser getUserDetail(Login login) {
        return this.secuser;
    }

    public void loginUser() {
        UserSession.getInstance().setSession(this.secuser, new Date());
        controller.init(true);
    }

    public void logoutUser() {
        UserSession.getInstance().setSession(null, null);
        controller.init(false);
    }
}
