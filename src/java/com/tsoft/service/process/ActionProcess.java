/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.service.process;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ActionProcess extends Serializable {
    String libelle();

    String description();

    Object run(HttpSession var1, HttpServletRequest var2) throws Exception;

    String form() throws Exception;

    Class rubrique() throws Exception;

    Object formModel(HttpSession var1, HttpServletRequest var2) throws Exception;

    Object initData(HttpSession var1, HttpServletRequest var2) throws Exception;
}
