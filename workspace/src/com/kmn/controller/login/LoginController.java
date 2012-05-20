/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.controller.login;

import com.kmn.Login;
import com.kmn.backend.entity.SecUser;
import com.kmn.backend.jpa.SecUserJpaController;
import com.kmn.controller.Confirm;

/**
 *
 * @author Hermanto
 */
public class LoginController {

    private SecUserJpaController usrJpa;

    public LoginController() {
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

        SecUser sec = this.usrJpa.findByUserName(username);
        if (sec == null) {
            confirm.onWarning("Invalid Login"+ "\n" +"Please check user name");
            return;
        } else if (!sec.getUsrPassword().equals(password)) {
            confirm.onWarning("Invalid Login" + "\n" + "Please check your password");
            return;
        }

        confirm.onSuccess();
    }

    public SecUser getUserDetail(Login login) {
        String username = login.getjUsername().getText();
        SecUser sec = this.usrJpa.findByUserName(username);

        return sec;
    }
}
