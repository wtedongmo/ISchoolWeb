/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;


import com.tsoft.web.init.DemoData;
import com.tsoft.web.init.RealData;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@RestController
@RequestMapping({"/app"})
public class InitDataController {
    public InitDataController() {
    }

    @RequestMapping(
        value = {"/init"},
        method = {RequestMethod.GET}
    )
    String initApp(HttpServletRequest req) throws Exception {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        Iterator var3 = wac.getBeansOfType(RealData.class).entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, RealData> ac = (Entry)var3.next();
            ((RealData)ac.getValue()).populateData(req);
        }

        return "Initialisation Terminée";
    }

    @RequestMapping(
        value = {"/demo"},
        method = {RequestMethod.GET}
    )
    String demoApp(HttpServletRequest req) throws Exception {
        this.initApp(req);
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        Iterator var3 = wac.getBeansOfType(DemoData.class).entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, DemoData> ac = (Entry)var3.next();
            ((DemoData)ac.getValue()).populateData(req);
        }

        return "Initialisation Terminée";
    }

    public static void main(String[] arg) {
        String packagesss = "com.tsoft.security.model";
        packagesss = packagesss.substring(0, packagesss.lastIndexOf("."));
        packagesss = packagesss.substring(packagesss.lastIndexOf(".") + 1, packagesss.length());
        System.out.println(packagesss);
    }
}
