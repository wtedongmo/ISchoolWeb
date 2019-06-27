/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils;


import java.io.File;

public class FileUtils {
    public FileUtils() {
    }

    public static String getUploadedDir() {
        File uploadedfile = new File("." + File.separator + "uploadedfile");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }

        return uploadedfile.getAbsolutePath();
    }

    public static String getReportFile(String filename) {
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }

        return uploadedfile.getAbsolutePath() + File.separator + filename;
    }

    public static File getUploadedFile(String filename) {
        return new File(getUploadedDir() + File.separator + filename);
    }
}
