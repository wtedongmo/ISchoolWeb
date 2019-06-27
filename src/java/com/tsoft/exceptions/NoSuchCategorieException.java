/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.exceptions;

/**
 *
 * @author tchipi
 */
@SuppressWarnings("serial")
public class NoSuchCategorieException  extends  Exception{

    public NoSuchCategorieException(String message) {
        super(message);
    }

    public NoSuchCategorieException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
