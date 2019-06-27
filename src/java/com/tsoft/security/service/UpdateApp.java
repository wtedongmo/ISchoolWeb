/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.service;


import com.tsoft.annotations.form.Label;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.Menu;
import com.tsoft.security.model.Rubrique;
import com.tsoft.security.model.Services;
import com.tsoft.service.process.ActionProcess;
import com.tsoft.service.process.EmptyActionProcess;
import com.tsoft.service.process.EmptyCustomActionProcess;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.enumerations.TypeService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service
public class UpdateApp extends EmptyActionProcess {
    @Autowired
    HibernateEntityProperties emm;

    public UpdateApp() {
    }

    public String libelle() {
        return "Mise à jour Applicative";
    }

    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        List<Rubrique> rubriques = new ArrayList();

        for(Iterator var4 = this.emm.getMapclasscategories().entrySet().iterator(); var4.hasNext(); this.hibernateService.saveAll(rubriques)) {
            Entry<String, Class<?>> ent = (Entry)var4.next();
            Rubrique r = (Rubrique)this.hibernateService.getOne(Rubrique.class, "select m from Rubrique m where m.reference='" + (String)ent.getKey() + "'");
            if (r == null) {
                r = new Rubrique();
                r.setLibelle(((Class)ent.getValue()).isAnnotationPresent(Label.class) ? ((Label)((Class)ent.getValue()).getAnnotation(Label.class)).value() : (String)ent.getKey());
                r.setReference((String)ent.getKey());
                String packagesss = ((Class)ent.getValue()).getPackage().getName();
                packagesss = packagesss.substring(0, packagesss.lastIndexOf("."));
                packagesss = packagesss.substring(packagesss.lastIndexOf(".") + 1, packagesss.length());
                Menu m = (Menu)this.hibernateService.getOne(Menu.class, "select m from Menu m where m.libelle='" + packagesss + "'");
                if (m == null) {
                    m = new Menu();
                    m.setLibelle(packagesss);
                    this.hibernateService.create(m);
                }

                r.setCodeMenu(m);
                rubriques.add(r);
            }
        }

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

        Services s;
        for(Iterator var10 = wac.getBeansOfType(ActionProcess.class).entrySet().iterator(); var10.hasNext(); this.hibernateService.saveOrUpdate(s)) {
            Entry<String, ActionProcess> ac = (Entry)var10.next();
            s = (Services)this.hibernateService.getOne(Services.class, "select s from Services s where s.reference='" + (String)ac.getKey() + "'");
            if (s == null) {
                s = new Services();
                s.setLibelle(((ActionProcess)ac.getValue()).libelle());
                s.setReference((String)ac.getKey());
                s.setDescription(((ActionProcess)ac.getValue()).description());
                s.setType(ac.getValue() instanceof EmptyCustomActionProcess ? TypeService.CUSTOM : (ac.getValue() instanceof EmptyReportProcess ? TypeService.REPORT : TypeService.ACTION));
                Rubrique rr = (Rubrique)this.hibernateService.getOne(Rubrique.class, "select m from Rubrique m where m.reference='" + ((ActionProcess)ac.getValue()).rubrique().getSimpleName() + "'");
                s.setRubrique(rr);
            }
        }

        return true;
    }

    public Class rubrique() throws Exception {
        return Services.class;
    }
}
