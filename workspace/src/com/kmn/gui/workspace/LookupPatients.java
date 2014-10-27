/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import com.kmn.MainApps;
import com.kmn.controller.props.ServerProperties;
import com.kmn.ws.ClientService;
import com.kmn.ws.bean.Patient;
import com.kmn.ws.bean.PatientInfo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.soap.SOAPException;
import javax.xml.transform.*;

/**
 *
 * @author valeo
 */
public class LookupPatients extends javax.swing.JDialog {
    private WorkspaceModel wm;
    private Workspace ws;
    private PatientInfo patientInfo;
    private static final String MSG_MANDATORY = "Harap masukkan Nama Pasien, BRM atau Single ID.";
    private static final String DEFAULT_CLINIC_ID = "8";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_ROW_PER_PAGE = 10;
    private String clinicId; 
    private int pageNumber; 
    private int rowPerPage;
    ClientService cs;
    ServerProperties sp;
    /**
     * Creates new form LookupPatients
     */
    protected LookupPatients() {
        super(MainApps.getApplication().getMainFrame(), true);
        initComponents();
    }
    
    public LookupPatients(WorkspaceModel wm) throws SOAPException, MalformedURLException, IOException, TransformerException {
        this.wm = wm;
        this.setTitle(wm.owner.getTitleAt(wm.owner.getSelectedIndex()));
        initComponents();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        this.cs = new ClientService();
        this.sp  = new ServerProperties(null);
        sp.load();
        patientInfo = cs.retrievePatients(jTextField1.getText(), sp.getKdn(), 
                DEFAULT_PAGE_NUMBER, DEFAULT_ROW_PER_PAGE);
        if (patientInfo != null) {
            for (Patient p : patientInfo.getPatients()) {
                model.addRow(new Object[]{p.getPatientId(),p.getSingleId(), p.getPatientName(),
                    p.getPatientBrm(), p.getDocId(), p.getDocName()});
            }
        }
    }
    
    public LookupPatients(Workspace ws) throws SOAPException, MalformedURLException, IOException, TransformerException {
        this.ws = ws;
        this.setTitle(ws.owner.getTitleAt(ws.owner.getSelectedIndex()));
        initComponents();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        this.cs = new ClientService();
//        patientInfo = cs.retrievePatients(jTextField1.getText(), DEFAULT_CLINIC_ID, 
//                DEFAULT_PAGE_NUMBER, DEFAULT_ROW_PER_PAGE);
//        if (patientInfo != null) {
//            for (Patient p : patientInfo.getPatients()) {
//                model.addRow(new Object[]{p.getPatientId(),p.getSingleId(), p.getPatientName(),
//                    p.getPatientBrm(), p.getDocId(), p.getDocName()});
//            }
//        }
    }
    
    public LookupPatients(WorkspaceModel wm, String clinicId, int pageNumber, int rowPerPage) throws SOAPException, MalformedURLException, IOException, TransformerException {
        this.wm = wm;
        this.setTitle(wm.owner.getTitleAt(wm.owner.getSelectedIndex()));
        this.clinicId = clinicId;
        this.pageNumber = pageNumber;
        this.rowPerPage = rowPerPage;
        initComponents();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        //ClientService cs = new ClientService();
        patientInfo = this.cs.retrievePatients(jTextField1.getText(), this.sp.getKdn(), 1, 10);
        for (Patient p : patientInfo.getPatients()) {
            model.addRow(new Object[]{p.getPatientId(),p.getPatientName(),
                p.getPatientBrm(), p.getDocId(), p.getDocName()});
        }
    }
    
    public LookupPatients(Workspace ws, String clinicId, int pageNumber, int rowPerPage) throws SOAPException, MalformedURLException, IOException, TransformerException {
        this.ws = ws;
        this.setTitle(ws.owner.getTitleAt(ws.owner.getSelectedIndex()));
        this.clinicId = clinicId;
        this.pageNumber = pageNumber;
        this.rowPerPage = rowPerPage;
        initComponents();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        //ClientService cs = new ClientService();
        patientInfo = this.cs.retrievePatients(jTextField1.getText(), this.sp.getKdn(), 1, 10);
        for (Patient p : patientInfo.getPatients()) {
            model.addRow(new Object[]{p.getPatientId(),p.getPatientName(),
                p.getPatientBrm(), p.getDocId(), p.getDocName()});
        }
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patients");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Single ID", "Patient Name", "Patient BRM", "Doc ID", "Doc Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(800, 200));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel1.setText("Patient Name");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/menu/find.png"))); // NOI18N
        jButton1.setText("Search");
        jButton1.setPreferredSize(new java.awt.Dimension(93, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/ok.png"))); // NOI18N
        jButton3.setText("Select");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("BRM");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Single ID");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 344, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // Search Patient
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            while(model.getRowCount()>0){
                model.removeRow(0);
            }
            //ClientService cs = new ClientService();
            if(jTextField1.getText().isEmpty() && jTextField3.getText().isEmpty() && jTextField2.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, MSG_MANDATORY);
            } else {
                String keyword = "%"+jTextField1.getText()+"%"+"#"+jTextField3.getText()+"#"+jTextField2.getText();
                patientInfo = this.cs.retrievePatients(keyword, this.sp.getKdn(), this.pageNumber, this.rowPerPage);
                for (Patient p : patientInfo.getPatients()) {
                    model.addRow(new Object[]{p.getPatientId(),p.getSingleId(), p.getPatientName(),
                        p.getPatientBrm(), p.getDocId(), p.getDocName()});
                }
            }
        } catch (SOAPException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cancel Button
        this.dispose();
        //this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Select Patient
        int row = jTable1.getSelectedRow();
        HashMap hm = new HashMap();
        hm.put(ClientService.TAG_PATIENTID, (String) jTable1.getValueAt(row, 0));
        hm.put(ClientService.TAG_SINGLEID, (String) jTable1.getValueAt(row, 1));
        hm.put(ClientService.TAG_PATIENTNAME, (String) jTable1.getValueAt(row, 2));
        hm.put(ClientService.TAG_PATIENTBRM, (String) jTable1.getValueAt(row, 3));
        hm.put(ClientService.TAG_DOCID, (String) jTable1.getValueAt(row, 4));
        hm.put(ClientService.TAG_DOCNAME, (String) jTable1.getValueAt(row, 5));
        if (this.wm != null) {
            int rowWm = this.wm.jTable1.getSelectedRow();
            //this.wm.jTable1.setValueAt(hm.get(ClientService.TAG_PATIENTID), rowWm, 0);
            this.wm.jTable1.setValueAt(hm.get(ClientService.TAG_SINGLEID), rowWm, 0);
            this.wm.jTable1.setValueAt(hm.get(ClientService.TAG_PATIENTNAME), rowWm, 1);
            this.wm.jTable1.setValueAt(hm.get(ClientService.TAG_PATIENTBRM), rowWm, 2);
            this.wm.jTable1.setValueAt(hm.get(ClientService.TAG_DOCID), rowWm, 4);
        } else if(this.ws != null) {
            int rowWm = this.ws.jTable2.getSelectedRow();
            //this.ws.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTID), rowWm, 0);
            this.ws.jTable2.setValueAt(hm.get(ClientService.TAG_SINGLEID), rowWm, 0);
            this.ws.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTNAME), rowWm, 1);
            this.ws.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTBRM), rowWm, 2);
            this.ws.jTable2.setValueAt(hm.get(ClientService.TAG_DOCID), rowWm, 4);
        }
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    protected javax.swing.JTextField jTextField1;
    protected javax.swing.JTextField jTextField2;
    protected javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
