/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.model;

import com.tsoft.utils.web.datatables.DatatableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class ListModel extends RubriqueModel {
    private boolean isSearchable;
    private String mappedby;
    private List<DatatableColumnModel> columns = new ArrayList();

    public ListModel() {
    }

    public String getMappedby() {
        return this.mappedby;
    }

    public void setMappedby(String mappedby) {
        this.mappedby = mappedby;
    }

    public boolean isIsSearchable() {
        return this.isSearchable;
    }

    public void setIsSearchable(boolean isSearchable) {
        this.isSearchable = isSearchable;
    }

    public List<DatatableColumnModel> getColumns() {
        return this.columns;
    }

    public void setColumns(List<DatatableColumnModel> columns) {
        this.columns = columns;
    }

    public void addColumns(DatatableColumnModel column) {
        this.columns.add(column);
    }
}
