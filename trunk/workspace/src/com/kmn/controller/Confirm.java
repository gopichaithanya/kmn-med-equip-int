/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller;

/**
 *
 * @author Hermanto
 */
public interface Confirm {
    void onSuccess();

    void onWarning(String message);

    void onError(Throwable t);
}
