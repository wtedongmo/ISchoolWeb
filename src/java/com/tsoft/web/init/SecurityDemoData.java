/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.init;


import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.User;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityDemoData implements DemoData {
    @Autowired
    HibernateServiceWrapper service;
    @Autowired
    HibernateEntityProperties emm;

    public SecurityDemoData() {
    }

    public void populateData(HttpServletRequest req) throws Exception {
        DataFactory df = new DataFactory();
        Date minDate = df.getDate(1980, 1, 1);
        Date maxDate = df.getDate(1950, 1, 1);

        for(int i = 0; i < 15; ++i) {
            User u = new User();
            u.setUsername(df.getLastName());
            u.setPassword(u.getUsername());
            u.setAccountNonExpired(true);
            u.setCycle_vie(DataLifeCycle.ACTIF);
            u.setNom_prenom(df.getName());
            u.setDateNaissance(df.getDateBetween(minDate, maxDate));
            u.setEmailAdresse(df.getEmailAddress());
            u.setTelephoneMobile("237 " + df.getNumberText(8));
            u.setLieuNaissance(df.getCity());
            this.service.create(u);
        }

    }
}
