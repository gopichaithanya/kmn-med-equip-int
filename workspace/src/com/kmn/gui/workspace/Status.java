/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Status.java
 *
 * Created on 28 Mei 12, 3:32:02
 */

package com.kmn.gui.workspace;

import com.kmn.MainApps;

/**
 *
 * @author Hermanto
 */
public class Status extends javax.swing.JDialog {

    /** Creates new form Status */
    public Status(WorkspaceModel workspace, boolean modal) {
        super(MainApps.getApplication().getMainFrame(), modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageStatus = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.kmn.MainApps.class).getContext().getResourceMap(Status.class);
        messageStatus.setText(resourceMap.getString("messageStatus.text")); // NOI18N
        messageStatus.setName("messageStatus"); // NOI18N

        labelStatus.setFont(resourceMap.getFont("labelStatus.font")); // NOI18N
        labelStatus.setText(resourceMap.getString("labelStatus.text")); // NOI18N
        labelStatus.setName("labelStatus"); // NOI18N

        loading.setIcon(resourceMap.getIcon("loading.icon")); // NOI18N
        loading.setText(resourceMap.getString("loading.text")); // NOI18N
        loading.setName("loading"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(messageStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(loading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelStatus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelStatus)
                    .addComponent(loading))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(messageStatus)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel loading;
    private javax.swing.JLabel messageStatus;
    // End of variables declaration//GEN-END:variables

}
