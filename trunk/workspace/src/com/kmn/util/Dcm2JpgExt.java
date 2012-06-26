/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import java.io.File;
import java.io.IOException;
import org.dcm4che2.tool.dcm2jpg.Dcm2Jpg;

/**
 *
 * @author valeo
 */
public class Dcm2JpgExt extends Dcm2Jpg {

    public Dcm2JpgExt() {
        super();
    }
    
    public Dcm2JpgExt(String iPath, String oPath) {
        super();
        File dest = new File(oPath);
        long t1 = System.currentTimeMillis();
        int count = 1;
        File src = new File(iPath);
        if (!src.exists()){
            System.out.println("Cannot find the file specified: " + iPath);
        }
        try {
            System.out.println("Source: " + iPath);
            super.convert(src, dest);
            System.out.println("Destination: " + oPath);
        } catch (IOException e) {
            System.err.println("dcm2jpg: Failed to convert " 
                    + iPath + ": " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Error e) {
            e.printStackTrace(System.err);
        } 
        long t2 = System.currentTimeMillis();
        System.out.println("\nconverted " + count + " files in " + (t2 - t1)
                / 1000f + " s.");
    }
}