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
        constructDcm2JpgExt(iPath, oPath, 1);
    }
    
    public Dcm2JpgExt(String iPath, String oPath, int frameNumber) {
        super();
        constructDcm2JpgExt(iPath, oPath, frameNumber);
    }
    
    private void constructDcm2JpgExt(String iPath, String oPath, int frameNumber) {
        File dest = new File(oPath);
        long t1 = System.currentTimeMillis();
        int count = 1;
        File src = new File(iPath);
        if (!src.exists()){
            System.out.println("Cannot find the file specified: " + iPath);
        }
        try {
            if(frameNumber!=0){
                super.setFrameNumber(frameNumber);
            }else{
                for(int i=1; i<=11; i++){
                    System.out.println("Source: " + iPath);
                    System.out.println("Frame Number: " + i);
                    super.setFrameNumber(i);
                    super.convert(src, dest);
                    System.out.println("Destination: " + oPath+i+".jpg");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage()+"\nReverting to render the first frame.");
            constructDcm2JpgExt(iPath, oPath, 1);
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
