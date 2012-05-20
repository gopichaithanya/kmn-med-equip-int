/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller.menu;

import com.kmn.MainView;
import com.kmn.backend.entity.SecUser;
import com.kmn.controller.UserSession;

/**
 *
 * @author Hermanto
 */
public class MainViewController {
    private MainView mainview;
    
    public MainViewController(MainView mainview) {
        this.mainview = mainview;
    }

    public void init(boolean enabled) {
        String userlogin = (enabled)? UserSession.getInstance().getSecuser().getUsrLoginname():null;
        String tooltips = (enabled) ? "Logout user" : "Login user";

        /* Status */
        mainview.getUserLoginLabel().setText(userlogin);

        /* Toolbar */
        mainview.getBtnOpen().setEnabled(enabled);
        mainview.getBtnFind().setEnabled(enabled);
        mainview.getBtnRefresh().setEnabled(enabled);
        if(enabled) mainview.getBtnLogin().setIcon((mainview.getResourceMap().getIcon("btnLogout.icon")));
        else mainview.getBtnLogin().setIcon((mainview.getResourceMap().getIcon("btnLogin.icon")));
        mainview.getBtnLogin().setToolTipText(tooltips);

        /* Menu */
        mainview.getOpenMenuItem().setEnabled(enabled);
        mainview.getLoginMenuItem().setEnabled(!enabled);
        mainview.getLogoutMenuItem().setEnabled(enabled);
        mainview.getProfileMenuItem().setEnabled(enabled);
    }
}
