/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.utils.web.datatables;


import javax.servlet.http.HttpServletRequest;

public class DataTablesParamUtility {
    public DataTablesParamUtility() {
    }

    public static JQueryDataTableParamModel getParam(HttpServletRequest request) {
        if (request.getParameter("draw") != null && !"".equals(request.getParameter("draw"))) {
            JQueryDataTableParamModel param = new JQueryDataTableParamModel();
            param.sEcho = request.getParameter("draw");
            param.iDisplayStart = Integer.parseInt(request.getParameter("start"));
            param.iDisplayLength = Integer.parseInt(request.getParameter("length"));
            param.iSortColumnIndex = Integer.parseInt(request.getParameter("order[0][column]"));
            param.sSortDirection = request.getParameter("order[0][dir]");
            return param;
        } else {
            return null;
        }
    }
}
