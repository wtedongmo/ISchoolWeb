/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;

import com.tsoft.exceptions.BusinessException;
import com.tsoft.service.process.ActionProcess;
import com.tsoft.service.process.EmptyCustomActionProcess;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.enumerations.TypeService;
import com.tsoft.web.model.FormModel;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class ProcessController implements ApplicationContextAware {
    private ApplicationContext context;
    @Autowired
    private ObjectUtils objectUtils;
    @Autowired
    ConversionServiceFactoryBean csfb;

    public ProcessController() {
    }

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public ActionProcess lookupActionProcess(String process) throws Exception {
        if (this.context.containsBean(process)) {
            return (ActionProcess)this.context.getBean(process, ActionProcess.class);
        } else {
            throw new BusinessException("Service " + process + " non existant");
        }
    }

    public Object submitProcess(String process, HttpSession session, HttpServletRequest request) throws Exception {
        ActionProcess ap = this.lookupActionProcess(process);
        ServletRequestDataBinder binder = new ServletRequestDataBinder(ap);
        binder.setConversionService(this.csfb.getObject());
        binder.bind(request);
        binder.validate();
        BindingResult results = binder.getBindingResult();
        String error = "";
        if (results.hasFieldErrors()) {
            error = (String)results.getFieldErrors().stream().map((fe) -> {
                return fe.getField() + ":" + fe.getRejectedValue() + " " + fe.getDefaultMessage();
            }).reduce(error, String::concat);
            throw new Exception(error);
        } else {
            return ap.run(session, request);
        }
    }

    @RequestMapping(
        value = {"/{process}/processreport"},
        method = {RequestMethod.POST},
        produces = {"application/octet-stream"}
    )
    public Object submitProcessReport(@PathVariable String process, HttpSession session, HttpServletRequest request) throws Exception {
        return this.submitProcess(process, session, request);
    }

    @RequestMapping(
        value = {"/{process}/processrun"},
        method = {RequestMethod.POST}
    )
    public Object submitProcessAction(@PathVariable String process, HttpSession session, HttpServletRequest request) throws Exception {
        return this.submitProcess(process, session, request);
    }

    @RequestMapping(
        value = {"/{process}/processmodel"},
        method = {RequestMethod.GET}
    )
    public JSONObject getprocessmodel(HttpSession session, @PathVariable String process, HttpServletRequest request) throws Exception {
        ActionProcess ap = this.lookupActionProcess(process);
        JSONObject jo = new JSONObject();
        jo.put("url", ap.form());
        jo.put("dataform", this.objectUtils.objectToJson(ap, request, true));
        FormModel fm = (FormModel)ap.formModel(session, request);
        fm.setTypeservice(ap instanceof EmptyCustomActionProcess ? TypeService.CUSTOM : (ap instanceof EmptyReportProcess ? TypeService.REPORT : TypeService.ACTION));
        fm.setCategorie(process);
        jo.put("form", fm);
        return jo;
    }

    @RequestMapping(
        value = {"/{process}/process_initdata"},
        method = {RequestMethod.GET}
    )
    public Object getInitDataProcess(HttpSession session, @PathVariable String process, HttpServletRequest request) throws Exception {
        ActionProcess ap = this.lookupActionProcess(process);
        JSONObject jo = new JSONObject();
        jo.put("url", ap.form());
        jo.put("dataform", this.objectUtils.objectToJson(ap, request, true));
        FormModel fm = (FormModel)ap.formModel(session, request);
        fm.setTypeservice(ap instanceof EmptyCustomActionProcess ? TypeService.CUSTOM : (ap instanceof EmptyReportProcess ? TypeService.REPORT : TypeService.ACTION));
        fm.setCategorie(process);
        jo.put("form", fm);
        return jo;
    }
}
