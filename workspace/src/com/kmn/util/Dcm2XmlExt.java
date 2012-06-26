/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import java.io.File;
import java.io.IOException;
import javax.xml.transform.TransformerConfigurationException;
import org.dcm4che2.data.Tag;
import org.dcm4che2.tool.dcm2xml.Dcm2Xml;

/**
 *
 * @author valeo
 */
public class Dcm2XmlExt extends Dcm2Xml {

    public Dcm2XmlExt() {
        super();
    }
    
    public Dcm2XmlExt(String iPath, String oPath) {
        super();
        File ifile = new File(iPath);
        File ofile = new File(oPath);
        super.setBaseDir(ofile.getAbsoluteFile().getParentFile());
        super.setExclude(new int[] {Tag.PixelData});
        long t1 = System.currentTimeMillis();
        try {
            super.convert(ifile, ofile);
        } catch (TransformerConfigurationException e) {
            System.err.println("dcm2xml: Configuration Error: " 
                    + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("dcm2xml: Failed to convert " 
                    + ifile + ": " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
        long t2 = System.currentTimeMillis();
        if (ofile != null)
            System.out.println("Finished conversion of " + ifile + " to "
                        + ofile + " in " + (t2 - t1) + "ms");      
    }
    
}
