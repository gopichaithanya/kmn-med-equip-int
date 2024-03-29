/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import com.kmn.MainApps;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author valeo
 */
public class ViewOutput extends javax.swing.JDialog {
    public static String PDF_FILE_EXT = ".pdf";
    public static String JPG_FILE_EXT = ".jpg";
    public static String XML_FILE_EXT = ".xml";
    WorkspaceModel wm;
    Workspace ws;
    /**
     * Creates new form ViewOutput
     */
    public ViewOutput(WorkspaceModel wm, File file) {
        super(MainApps.getApplication().getMainFrame(), true);
        initComponents();
        
        this.wm = wm;
        if(file == null) {
            //renderXml((String) wm.jTable1.getValueAt(wm.jTable1.getSelectedRow(), 5));
        } else if(file.getName().contains(PDF_FILE_EXT)) {
            renderPdf(file);
        } else if (file.getName().contains(JPG_FILE_EXT)) {
            renderImage(file);
        } else if (file.getName().contains(XML_FILE_EXT)) {
            renderXml(file);
        }
    }
    
    public ViewOutput(Workspace ws, File file) {
        super(MainApps.getApplication().getMainFrame(), true);
        initComponents();
        
        this.ws = ws;
        if(file == null) {
            //renderXml((String) wm.jTable1.getValueAt(wm.jTable1.getSelectedRow(), 5));
        } else if(file.getName().contains(PDF_FILE_EXT)) {
            renderPdf(file);
        } else if (file.getName().contains(JPG_FILE_EXT)) {
            renderImage(file);
        } else if (file.getName().contains(XML_FILE_EXT)) {
            renderXml(file);
        }
    }
    
    private void renderXml(String xmlStr) {
        JOptionPane.showMessageDialog(this, xmlStr);
    }
    
    private void renderXml(File file) {
//        try {
//            CommViewer cv = new CommViewer(this, file);
//            this.jLabel1.setVisible(false);
//            this.add(cv);
//            this.pack();
//            this.setVisible(true);
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(ViewOutput.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(ViewOutput.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ViewOutput.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private void renderImage(File file) {
        ImageIcon gbr = new ImageIcon(file.getAbsolutePath());
        jLabel1.setIcon(gbr);
        this.setVisible(true);
    }
    
    private void renderPdf(File file) {
        try {
            renderPdfAsImage(file);
//            PagePanel panel = new PagePanel();
//            this.remove(jLabel1);
//            jScrollPane1.add(panel);
//            //this.add(panel);
//            this.pack();
//            this.setVisible(true);
//
//            //load a pdf from a byte buffer
//            RandomAccessFile raf = new RandomAccessFile(file, "r");
//            FileChannel channel = raf.getChannel();
//            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//            PDFFile pdffile = new PDFFile(buf);
//
//            // show the first page
//            PDFPage page = pdffile.getPage(0);
//            panel.showPage(page);
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void renderPdfAsImage(File file) throws FileNotFoundException, IOException {
        //load a pdf from a byte buffer
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        // draw the first page to an image
        PDFPage page = pdffile.getPage(0);
        //get the width and height for the doc at the default zoom 
        Rectangle rect = new Rectangle(0,0,
                (int)page.getBBox().getWidth(),
                (int)page.getBBox().getHeight());
        
        //generate the image
        Image img = page.getImage(
                rect.width, rect.height, //width & height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true  // block until drawing is done
                );
        jLabel1.setIcon(new ImageIcon(img));
        this.pack();
        this.setVisible(true);
    }
    public ViewOutput() {
        initComponents();
    }
    
    public void viewImage(File file) {
        ImageIcon image = new ImageIcon(file.getAbsolutePath());
        jLabel1.setIcon(image);
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
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Output Viewer");

        jLabel1.setAlignmentY(0.0F);
        jLabel1.setAutoscrolls(true);
        jScrollPane1.setViewportView(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
