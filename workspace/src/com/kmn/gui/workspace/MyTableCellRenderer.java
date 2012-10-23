/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author valeo
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer {  
   
    public MyTableCellRenderer() {  
        setOpaque(true);  
    }  
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
        //changes the cell color
        //if(String.valueOf(value).equalsIgnoreCase("true")) {  
        //changes the row color
        if(((String)table.getValueAt(row, 1)).equalsIgnoreCase("true")) { 
            setForeground(Color.black);  
            setBackground(Color.green);  
        } else{    
            setForeground(Color.black);
            setBackground(Color.white);  
        }  
        setText(value !=null ? value.toString() : "");  
        return this;  
    }  
} 