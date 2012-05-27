/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.gui.workspace;

import com.kmn.controller.Confirm;

/**
 *
 * @author Hermanto
 */
public class AddNewWorkspace {
    private Confirm confrim;

    public AddNewWorkspace(Confirm confrim) {
        this.confrim = confrim;
    }

    public void add() {
        confrim.onSuccess();
    }
}
