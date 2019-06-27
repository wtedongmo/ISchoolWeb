/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils.web.datatables;


public class DatatableColumnModel {
    private boolean bVisible = true;
    private int aTargets;
    private boolean bSortable = true;
    private String sTitle;
    private String name;
    private String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatatableColumnModel() {
    }

    public String getsTitle() {
        return this.sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public boolean isbVisible() {
        return this.bVisible;
    }

    public void setbVisible(boolean bVisible) {
        this.bVisible = bVisible;
    }

    public int getaTargets() {
        return this.aTargets;
    }

    public void setaTargets(int aTargets) {
        this.aTargets = aTargets;
    }

    public boolean isbSortable() {
        return this.bSortable;
    }

    public void setbSortable(boolean bSortable) {
        this.bSortable = bSortable;
    }
}
