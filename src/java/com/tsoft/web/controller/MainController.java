/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public abstract class MainController {
    public MainController() {
    }

    protected List getSessionCategorieData(HttpSession session, String categorie) {
        List listresult = (List)session.getAttribute("list" + categorie);
        if (listresult == null) {
            listresult = new ArrayList();
        }

        return (List)listresult;
    }

    protected Object getSessionCategorieElement(HttpSession session, String categorie, int row) {
        List listresult = this.getSessionCategorieData(session, categorie);

        try {
            return listresult.get(row);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }
}
