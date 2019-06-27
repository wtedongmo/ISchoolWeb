/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class StringUtils {
    public StringUtils() {
    }

    public static String coollabel(String label) {
        if (!label.equalsIgnoreCase("code") && !label.equalsIgnoreCase("type")) {
            String coollabel = label.replaceAll("_", " ").replaceAll("code", " ").trim();
            return FirstLetterCapital(coollabel);
        } else {
            return FirstLetterCapital(label);
        }
    }

    public static String wrap(String st) {
        return '\'' + st + '\'';
    }

    public static String FileToString(File f) throws IOException {
        String result = "";
        BufferedReader b = null;

        String line;
        try {
            for(b = new BufferedReader(new FileReader(f)); (line = b.readLine()) != null; result = result + line + "\n") {
                ;
            }
        } finally {
            if (b != null) {
                b.close();
            }

        }

        return result;
    }

    public static String FirstLineFile(File f) throws IOException {
        String result = "";
        BufferedReader b = null;

        try {
            b = new BufferedReader(new FileReader(f));
            result = b.readLine();
        } finally {
            if (b != null) {
                b.close();
            }

        }

        return result;
    }

    public static String InputstreamToString(InputStream in) {
        String result = "";
        Scanner sc = null;

        for(sc = new Scanner(in); sc.hasNext(); result = result + sc.nextLine() + "\n") {
            ;
        }

        return result;
    }

    public static String getStackTrace(Exception t) {
        String stackTrace = null;

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception var4) {
            ;
        }

        return stackTrace;
    }

    public static String FirstLetterCapital(String string) {
        String result = string.toUpperCase().substring(0, 1).concat(string.substring(1));
        return result;
    }

    public static void main(String[] args) {
        String categorie = "Rubriques_$$_jvstd3a_16";
        if (categorie.indexOf("_$$_") != -1) {
            categorie = categorie.substring(0, categorie.indexOf("_$$_"));
        }

        System.out.println(categorie);
        System.out.println(getStackTrace(new NullPointerException("test de la finction")));
    }
}
