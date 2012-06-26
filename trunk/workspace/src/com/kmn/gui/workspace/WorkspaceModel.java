/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WorkspaceModel.java
 *
 * Created on 28 Mei 12, 1:26:59
 */

package com.kmn.gui.workspace;

import com.kmn.gui.workspace.ModelInterface;
import com.kmn.MainApps;
import com.kmn.controller.InterfaceEvent;
import com.kmn.controller.props.EquipmentDetailProperties;
import com.kmn.util.CommInterface;
import com.kmn.util.DicomInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.comm.SerialPort;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hermanto
 */
public class WorkspaceModel extends javax.swing.JPanel implements InterfaceEvent, ModelInterface {

    private Status statusBox;
    private JTabbedPane owner;
    private EquipmentDetailProperties equip;
    private ModelInterface modelinterface;
    private DicomInterface dicomInterface;
    private CommInterface commInterface;

    /** Creates new form WorkspaceModel */
    public WorkspaceModel() {
        initComponents();
    }
    public WorkspaceModel(JTabbedPane owner, EquipmentDetailProperties equip) {
        this.owner = owner;
        this.equip = equip;
        initComponents();
        //receiveEquipmentData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "First name", "Last Name", "Remark", "Branch", "Data Output"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/save.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/kmn/gui/workspace/resources/WorkspaceModel"); // NOI18N
        jButton1.setText(bundle.getString("jButton1.save")); // NOI18N
        jButton1.setName("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText(bundle.getString("jButton2.cancel")); // NOI18N
        jButton2.setName("jButton2");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/login_user.png"))); // NOI18N
        jButton3.setText(bundle.getString("jButton3.lookup")); // NOI18N
        jButton3.setName("jButton3");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/loading.gif"))); // NOI18N
        jLabel1.setName("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        receiveEquipmentData();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        receiveEquipmentData();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //this.modelinterface.close();
        if(this.dicomInterface != null) this.dicomInterface.close();
        if(this.commInterface != null) this.commInterface.close();
        int index = this.owner.getSelectedIndex();
        this.owner.remove(index);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    protected javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    //@Override
    public void onSend() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //@Override
    public void onReceive(String message) {
        if (statusBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            statusBox = new Status(this, true);
            statusBox.setLocationRelativeTo(mainFrame);
        }
        //MainApps.getApplication().show(statusBox);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        sdf.format(now);
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{null,null,null,now,null,message});
        while(!jLabel1.getText().isEmpty()) {
            try {
                synchronized (this) {
                    wait(1000);
                }
                jLabel1.setText("");
            } catch (InterruptedException e) {
            }
        }
        //statusBox.setVisible(false);
    }

    //@Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //@Override
    public void onMessage(String message) {
        //throw new UnsupportedOperationException("Not supported yet.");
        while(jLabel1 == null) {
            try {
                synchronized (this) {
                    wait(1000);
                }
            } catch (InterruptedException e) {
            }
        }
        jLabel1.setText(message);
    }

    private void receiveEquipmentData() {
        if (equip.getInterfaceType().equalsIgnoreCase("DICOM")) {
            this.dicomInterface = new DicomInterface(this, "127.0.0.1", Integer.valueOf(equip.getPort()), "DCMRCV", "C:\\kmntmp");
            //this.modelinterface = new DicomInterface(this, "127.0.0.1", Integer.valueOf(equip.getPort()), "DCMRCV", "C:\\kmntmp");
            this.dicomInterface.connect();
         } else {
            this.commInterface = new CommInterface(this, equip.getCom(), Integer.valueOf(equip.getRate())
                    , Integer.valueOf(equip.getDataBit()), getStopBitInt(equip.getStopBit())
                    , getParityInt(equip.getParity()), getFlowInt(equip.getFlow()));
            this.commInterface.connect();
//            this.modelinterface = new CommInterface(this, equip.getCom(), Integer.valueOf(equip.getRate())
//                    , Integer.valueOf(equip.getDataBit()), getStopBitInt(equip.getStopBit())
//                    , getParityInt(equip.getParity()), getFlowInt(equip.getFlow()));
        }
        //this.modelinterface.connect();        
    }
    
    public Integer getStopBitInt(String string) {
        int result = 1;
        if(string.equalsIgnoreCase("2")) {
            result = SerialPort.STOPBITS_2;
        } else if(string.equalsIgnoreCase("1.5")) {
            result = SerialPort.STOPBITS_1_5;
        } else if(string.equalsIgnoreCase("1")) {
            result = SerialPort.STOPBITS_1;
        }
        return result;
    }
    
    public static final String FLOW_NONE = "None";
    public static final String FLOW_XONXOFF = "Xon / Xoff";
    public static final String FLOW_XONXOFFOUT = "Xon / Xoff out";
    public static final String FLOW_HARDWARE = "Hardware";
    public static final String FLOW_RTSCTS_IN = "RTCS In";
    public static final String FLOW_RTSCTS_OUT = "RTCS Out";
    
    public Integer getFlowInt(String string) {
        int result = SerialPort.FLOWCONTROL_NONE;
        if(string.equalsIgnoreCase(FLOW_NONE)) {
            result = SerialPort.FLOWCONTROL_NONE;
        } else if(string.equalsIgnoreCase(FLOW_XONXOFF)) {
            result = SerialPort.FLOWCONTROL_XONXOFF_IN;
        } else if(string.equalsIgnoreCase(FLOW_HARDWARE)) {
            result = SerialPort.FLOWCONTROL_RTSCTS_IN;
        } else if(string.equalsIgnoreCase(FLOW_RTSCTS_IN)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_RTSCTS_IN;
        } else if(string.equalsIgnoreCase(FLOW_RTSCTS_OUT)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_RTSCTS_OUT;
        } else if(string.equalsIgnoreCase(FLOW_XONXOFFOUT)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_XONXOFF_OUT;
        }
        return result;
    }
    
    public static final String PARITY_NONE = "None";
    public static final String PARITY_ODD = "Odd";
    public static final String PARITY_EVEN = "Even";
    public static final String PARITY_MARK = "Mark";
    public static final String PARITY_SPACE = "Space";
    
    public Integer getParityInt(String string) {
        int result = SerialPort.PARITY_NONE;
        if(string.equalsIgnoreCase(PARITY_NONE)) {
            result = SerialPort.PARITY_NONE;
        } else if(string.equalsIgnoreCase(PARITY_ODD)) {
            result = SerialPort.PARITY_ODD;
        } else if(string.equalsIgnoreCase(PARITY_EVEN)) {
            result = SerialPort.PARITY_EVEN;
        } else if(string.equalsIgnoreCase(PARITY_MARK)) {
            result = SerialPort.PARITY_MARK;
        } else if(string.equalsIgnoreCase(PARITY_SPACE)) {
            result = SerialPort.PARITY_SPACE;
        } 
        return result;
    }

    @Override
    public void connect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
