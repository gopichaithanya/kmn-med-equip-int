/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller;

/**
 *
 * @author Hermanto
 */
public interface InterfaceEvent {
    void onSend();

    void onReceive(String message);

    void onError(Throwable t);

    void onMessage(String message);
}
