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

import com.kmn.MainApps;
import com.kmn.controller.InterfaceEvent;
import com.kmn.controller.UserSession;
import com.kmn.controller.props.EquipmentDetailProperties;
import com.kmn.util.CommInterface;
import com.kmn.util.DicomInterface;
import com.kmn.ws.ClientService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.SerialPort;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.joda.time.DateTime;
import org.xml.sax.SAXException;

/**
 *
 * @author Hermanto
 */
public class WorkspaceModel extends javax.swing.JPanel implements InterfaceEvent {
    /*Message Strings*/
    public static final String MSG_LOADING_PATIENTS = "Loading Patients...";
    public static final String MSG_EMPTY = "";
    public static final String MSG_NOT_SUPPORTED = "Not supported yet.";
    public static final String MSG_ERROR = "[ERROR]: ";
    public static final String MSG_CAUSE = "\n[CAUSE]: ";
    public static final String MSG_SELECT_ROW = "Please select a data row.";
    public static final String MSG_INPUT_PATIENT = "Please input patient data.";
    public static final String MSG_NO_FILE = "Output result file does not exist.";
    public static final String MSG_SAVE_SUCCESS = "Save successful.";
    public static final String MSG_SAVE_FAILED = "Save failed.";
    /* Connection String Constants */
    public static final String TEMP_DIR = "C:/kmntmp";
    public static final String OUTPUT_PDF = ".out/output.pdf";
    public static final String OUTPUT_JPG = ".out/output.jpg";
    public static final String OUTPUT_XML = ".out/output.xml";
    public static final String DICOM = "DICOM";
    public static final String LOCAL_IP = "127.0.0.1";
    public static final String AP_NAME = "DCMRCV";
    public static final String SDF = "E, dd MMM yyyy HH:mm:ss Z";
    public static final String SDF_DIR = "ddMMMyyyyHHmmss";
    /* Equipment Properties File Mapping - Flow Control */
    public static final String FLOW_NONE = "None";
    public static final String FLOW_XONXOFF = "Xon / Xoff";
    public static final String FLOW_XONXOFFOUT = "Xon / Xoff out";
    public static final String FLOW_HARDWARE = "Hardware";
    public static final String FLOW_RTSCTS_IN = "RTCS In";
    public static final String FLOW_RTSCTS_OUT = "RTCS Out";
    /* Equipment Properties File Mapping - Parity */
    public static final String PARITY_NONE = "None";
    public static final String PARITY_ODD = "Odd";
    public static final String PARITY_EVEN = "Even";
    public static final String PARITY_MARK = "Mark";
    public static final String PARITY_SPACE = "Space";
    /* Equipment Properties File Mapping - Stop Bits */
    public static final String STOPBITS_1 = "1";
    public static final String STOPBITS_1_5 = "1.5";
    public static final String STOPBITS_2 = "2";
    
    private Status statusBox;
    protected JTabbedPane owner;
    private EquipmentDetailProperties equip;
    private ModelInterface modelinterface;
    private DicomInterface dicomInterface;
    private CommInterface commInterface;
    private ClientService cs;
    
    

    /** Creates new form WorkspaceModel */
    public WorkspaceModel() {
        initComponents();
    }
    public WorkspaceModel(JTabbedPane owner, EquipmentDetailProperties equip) {
        this.owner = owner;
        this.equip = equip;
        initComponents();
        this.setName(equip.getCode());
        receiveEquipmentData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RightMenu1 = new javax.swing.JPopupMenu();
        ItemOutput = new javax.swing.JMenuItem();
        ItemPatient = new javax.swing.JMenuItem();
        ItemSave = new javax.swing.JMenuItem();
        ItemDelete = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        RightMenu1.setName("RightMenu1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.kmn.MainApps.class).getContext().getResourceMap(WorkspaceModel.class);
        ItemOutput.setText(resourceMap.getString("ItemOutput.text")); // NOI18N
        ItemOutput.setToolTipText(resourceMap.getString("ItemOutput.toolTipText")); // NOI18N
        ItemOutput.setName("ItemOutput"); // NOI18N
        ItemOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewOutput(evt);
            }
        });
        RightMenu1.add(ItemOutput);

        ItemPatient.setText(resourceMap.getString("ItemPatient.text")); // NOI18N
        ItemPatient.setToolTipText(resourceMap.getString("ItemPatient.toolTipText")); // NOI18N
        ItemPatient.setName("ItemPatient"); // NOI18N
        ItemPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lookupPatient(evt);
            }
        });
        RightMenu1.add(ItemPatient);

        ItemSave.setText(resourceMap.getString("ItemSave.text")); // NOI18N
        ItemSave.setToolTipText(resourceMap.getString("ItemSave.toolTipText")); // NOI18N
        ItemSave.setName("ItemSave"); // NOI18N
        ItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save(evt);
            }
        });
        RightMenu1.add(ItemSave);

        ItemDelete.setText(resourceMap.getString("ItemDelete.text")); // NOI18N
        ItemDelete.setToolTipText(resourceMap.getString("ItemDelete.toolTipText")); // NOI18N
        ItemDelete.setName("ItemDelete"); // NOI18N
        ItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete(evt);
            }
        });
        RightMenu1.add(ItemDelete);

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Patient Name", "Patient BRM", "Remark", "Doc ID", "Data Output"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OpenMenu(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                OpenMenu(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/save.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/kmn/gui/workspace/resources/WorkspaceModel"); // NOI18N
        jButton1.setText(bundle.getString("jButton1.save")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText(bundle.getString("jButton2.cancel")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/login_user.png"))); // NOI18N
        jButton3.setText(bundle.getString("jButton3.lookup")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lookupPatient(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/loading.gif"))); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/file.png"))); // NOI18N
        jButton4.setText("View Output");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewOutput(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
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
                    .addComponent(jLabel1)
                    .addComponent(jButton4))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void close(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close
        // Cancel Button
        if (this.modelinterface != null) this.modelinterface.close();
        if(this.dicomInterface != null) this.dicomInterface.close();
        if(this.commInterface != null) this.commInterface.close();
        int index = this.owner.getSelectedIndex();
        this.owner.remove(index);
    }//GEN-LAST:event_close
    //To select the path of image file
    private File file;
    
    private void viewOutput(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewOutput
        // View Output
        int row = jTable1.getSelectedRow();
        JFrame mainFrame = MainApps.getApplication().getMainFrame();
        
        if (row > -1) {
            String filePath = (String) jTable1.getValueAt(row, 5);
            try {
                file = new File(filePath+OUTPUT_PDF);
                if(!file.exists()) {
                    file = new File(filePath+OUTPUT_JPG);
                }
                if(!file.exists()) {
                    file = new File(filePath);
                    if(!file.exists()) {
                        JOptionPane.showMessageDialog(this, MSG_NO_FILE);
                    } else {
                        XmlViewer xv = new XmlViewer(this, file);
                        xv.setLocationRelativeTo(mainFrame);
                        
                    }
                } else {
                    ViewOutput vo = new ViewOutput(this, file);
                    vo.setLocationRelativeTo(mainFrame);
                }
            } catch (NullPointerException ex) {   
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            }catch (ParserConfigurationException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_viewOutput
    
    private void lookupPatient(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lookupPatient
        // Lookup Patient
        int row = jTable1.getSelectedRow();
        if (row > -1) {
            try {
                this.onMessage(MSG_LOADING_PATIENTS);
                LookupPatients lookupPatients = new LookupPatients(this);
                lookupPatients.setVisible(true);
            } catch (SOAPException ex) {
                String msg = MSG_ERROR + ex.getMessage() + 
                        MSG_CAUSE + ex.getCause().getCause().getCause().getMessage();
                JOptionPane.showMessageDialog(this, msg);
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                this.onMessage(MSG_EMPTY);
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_lookupPatient

    private void save(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save
        int row = jTable1.getSelectedRow();
        if (row > -1) {
            String patientId = (String) jTable1.getValueAt(row, 0);
            if(patientId == null || patientId.isEmpty()){
                JOptionPane.showMessageDialog(this, MSG_INPUT_PATIENT);
            } else {
                this.cs = new ClientService();
                try {
                    String filePath = (String) jTable1.getValueAt(row, 5);
                    String branchId = "8";
                    String patientCode = (String) jTable1.getValueAt(row, 2); 
                    String patientName = (String) jTable1.getValueAt(row, 1);
                    DateTime dt = (DateTime) jTable1.getValueAt(row, 3);
                    String remark = cs.convertDateTimeToString(dt); 
                    int equipmentId = Integer.valueOf(this.equip.getCode());
                    int imageId = 0;
                    DateTime trxDate = dt;
                    DateTime timeStamp = trxDate;
                    String dataLocation = filePath;
                    String creatorId = "";
                    if(UserSession.getInstance().getSecuser() != null)
                        creatorId = UserSession.getInstance().getSecuser().getUsrLoginname();
                    
                    File dataOutput = null;
                    String xmlData;
                    if(filePath.contains(OUTPUT_XML)) {
                        //autoreff
                        //dataOutput = new File(filePath);
                        //xmlData = cs.getStringFromXmlFile(filePath);
                        xmlData = cs.getParsedStringFromXmlFile(filePath);
                        cs.createXml(xmlData, filePath);
                        dataOutput = new File(filePath);
                    } else {
                        //dicom File 
                        dataOutput = new File(filePath+OUTPUT_PDF);
                        dataLocation = filePath+OUTPUT_PDF;
                        if(!dataOutput.exists()) {
                            dataOutput = new File(filePath+OUTPUT_JPG);
                            dataLocation = filePath+OUTPUT_JPG; 
                        }
                        //xmlData = cs.getStringFromXmlFile(filePath+OUTPUT_XML);
                        xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><dicom><attr>EMPTY</attr></dicom>";
                        //xmlData = "0";
                    }
                    
                    if(cs.storeResults(branchId, patientId, patientCode, patientName, remark, equipmentId, imageId, trxDate, timeStamp
                            , dataLocation, dataOutput, xmlData, creatorId)) {
                        JOptionPane.showMessageDialog(this, MSG_SAVE_SUCCESS);
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.removeRow(row);
                    } else {
                        JOptionPane.showMessageDialog(this, MSG_SAVE_FAILED);
                    }
                } catch (SOAPException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                        Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_save

    private void OpenMenu(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OpenMenu
        if(evt.isPopupTrigger())
            RightMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_OpenMenu

    private void delete(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete
        int row = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(row);
    }//GEN-LAST:event_delete


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemDelete;
    private javax.swing.JMenuItem ItemOutput;
    private javax.swing.JMenuItem ItemPatient;
    private javax.swing.JMenuItem ItemSave;
    private javax.swing.JPopupMenu RightMenu1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    protected javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    //@Override
    public void onSend() {
        throw new UnsupportedOperationException(MSG_NOT_SUPPORTED);
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
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_DIR);
        DateTime trxDate = new DateTime(now);
        DatatypeFactory factory = null;
        try {
            factory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        String trxDateStr = factory.newXMLGregorianCalendar(trxDate.toGregorianCalendar()).toXMLFormat();
        String path = TEMP_DIR+"/"+sdf.format(now)+OUTPUT_XML;
        ClientService cs = new ClientService();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        if(cs.convertMessageToXml(MSG_EMPTY, message, path, equip.getCode())!=null) {
            //cs.convertMessageToXml(MSG_EMPTY, message, path);
            if(message.contains(TEMP_DIR)) {
                model.addRow(new Object[]{null,null,null,trxDate,null,message});
            } else {
                model.addRow(new Object[]{null,null,null,trxDate,null,path});
            }
            while(!jLabel1.getText().isEmpty()) {
                try {
                    synchronized (this) {
                        wait(1000);
                    }
                    jLabel1.setText(MSG_EMPTY);
                } catch (InterruptedException e) {
                }
            }
            //statusBox.setVisible(false);
        }
    }

    //@Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException(MSG_NOT_SUPPORTED);
    }

    //@Override
    public void onMessage(String message) {
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
        try {
            if (equip.getInterfaceType().equalsIgnoreCase(DICOM)) {
                this.modelinterface = new DicomInterface(this, LOCAL_IP, Integer.valueOf(equip.getPort()), AP_NAME, TEMP_DIR, 4);
             } else {
                this.modelinterface = new CommInterface(this, equip.getCom(), Integer.valueOf(equip.getRate())
                        , Integer.valueOf(equip.getDataBit()), getStopBitInt(equip.getStopBit())
                        , getParityInt(equip.getParity()),
                        getFlowInt(equip.getFlow()));
            }
            this.modelinterface.connect();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    
    public Integer getStopBitInt(String string) {
        int result = 1;
        if(string.equalsIgnoreCase(STOPBITS_2)) {
            result = SerialPort.STOPBITS_2;
        } else if(string.equalsIgnoreCase(STOPBITS_1_5)) {
            result = SerialPort.STOPBITS_1_5;
        } else if(string.equalsIgnoreCase(STOPBITS_1)) {
            result = SerialPort.STOPBITS_1;
        }
        return result;
    }
    
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
}
