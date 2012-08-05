/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import com.kmn.MainApps;
import com.kmn.ws.ClientService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author valeo
 */
public class CommViewer extends javax.swing.JDialog {
    
    WorkspaceModel vo;
    Document document;
    /**
     * Creates new form CommViewer
     */
    public CommViewer(WorkspaceModel vo, File file) throws ParserConfigurationException, SAXException, IOException {
        super(MainApps.getApplication().getMainFrame(), true);
        
        this.vo = vo;
        initComponents();
        renderXml(file);
    }
    
    private void renderXml(File file) throws ParserConfigurationException, SAXException, IOException {
        ClientService cs = new ClientService();
        this.document = cs.readXml(file.getAbsolutePath());
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        List<Object[]> tableContents = new ArrayList<Object[]>();
        //NodeList rootNode = this.document.getElementsByTagName(ClientService.TAG_COMM);
        NodeList n1 = this.document.getElementsByTagName(ClientService.TAG_ATTR);
        String data1, data2, data3;
        for (int i = 0; i < n1.getLength(); i++) {
            data1 = cs.getStringNodeValue(n1.item(i).getAttributes().getNamedItem(ClientService.ATTR_NAME));
            data2 = cs.getStringNodeValue(n1.item(i).getAttributes().getNamedItem(ClientService.ATTR_SELECTED));
            data3 = cs.getStringNodeValue(n1.item(i));
            model.addRow(new Object[]{data1,data2,data3});
            tableContents.add(new Object[]{data1,data2,data3});
        }
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Com Viewer");
        setModal(true);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Selected", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/ok.png"))); // NOI18N
        jButton1.setText("Save");
        jButton1.setToolTipText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.setToolTipText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(11, 11, 11))
        );

        getAccessibleContext().setAccessibleName("Com Viewer");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here: save button
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
