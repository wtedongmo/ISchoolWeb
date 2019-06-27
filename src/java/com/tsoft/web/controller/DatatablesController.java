/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;


import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.web.datatables.DataTablesParamUtility;
import com.tsoft.utils.web.datatables.JQueryDataTableParamModel;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class DatatablesController extends MainController {
    @Autowired
    HibernateEntityProperties emm;
    @Autowired
    ObjectUtils objectUtils;

    public DatatablesController() {
    }

    @RequestMapping(
        value = {"/datatables/{categorie}"},
        method = {RequestMethod.GET}
    )
    public JSONObject serverside(@PathVariable String categorie, HttpServletRequest request, HttpSession session) throws Exception {
        final List<Field> listfieldscolumns = this.emm.getfields(categorie);
        JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
        List listresult = this.getSessionCategorieData(session, categorie);
        int totalrecords = listresult.size();
        final int sortColumnIndex = param.iSortColumnIndex;
        final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
        Collections.sort(listresult, new Comparator() {
            public int compare(Object o1, Object o2) {
                try {
                    Field ff = (Field)listfieldscolumns.get(sortColumnIndex != 0 ? sortColumnIndex - 1 : sortColumnIndex);
                    Object co1 = DatatablesController.this.objectUtils.getFieldValue(ff, o1);
                    if (co1 == null) {
                        return -1;
                    } else {
                        Object co2 = DatatablesController.this.objectUtils.getFieldValue(ff, o2);
                        if (co2 == null) {
                            return 1;
                        } else {
                            int value = ((Comparable)co1).compareTo(co2);
                            return value * sortDirection;
                        }
                    }
                } catch (Exception var7) {
                    var7.printStackTrace();
                    return 0;
                }
            }
        });
        if (listresult.size() < param.iDisplayStart + param.iDisplayLength) {
            listresult = listresult.subList(param.iDisplayStart, listresult.size());
        } else {
            listresult = listresult.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("draw", param.sEcho);
        jsonResponse.put("recordsTotal", totalrecords);
        jsonResponse.put("recordsFiltered", totalrecords);
        jsonResponse.put("data", this.getDatatableList(listresult, categorie, param.iDisplayStart, request));
        return jsonResponse;
    }

    @RequestMapping(
        value = {"/datatables1/{categorie}"},
        method = {RequestMethod.GET}
    )
    public JSONArray ajaxsource(@PathVariable String categorie, HttpServletRequest request, HttpSession session) throws Exception {
        List listresult = this.getSessionCategorieData(session, categorie);
        return this.getDatatableList(listresult, categorie, 0, request);
    }

    public JSONArray getDatatableList(List list, String categorie, int displaystart, HttpServletRequest req) throws Exception {
        JSONArray array = new JSONArray();
        if (list != null && !list.isEmpty()) {
            int i = displaystart;
            Iterator var7 = list.iterator();

            while(var7.hasNext()) {
                Object objectlist = var7.next();
                JSONObject jo = this.objectUtils.objectToJson(objectlist, req, false);
                jo.put("Index", i);
                ++i;
                array.add(jo);
            }

            return array;
        } else {
            return array;
        }
    }
}
