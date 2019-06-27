/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.init;


import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.Profil;
import com.tsoft.security.model.User;
import com.tsoft.security.model.UserProfil;
import com.tsoft.security.service.UpdateApp;
import com.tsoft.utils.enumerations.DataLifeCycle;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityRealData implements RealData {
    @Autowired
    HibernateServiceWrapper service;
    @Autowired
    HibernateEntityProperties emm;
    @Autowired
    UpdateApp ua;

    public SecurityRealData() {
    }

    public void populateData(HttpServletRequest req) throws Exception {
        User u = new User();
        u.setUsername("admin");
        u.setPassword(u.getUsername());
        u.setAccountNonExpired(true);
        u.setCycle_vie(DataLifeCycle.ACTIF);
        u.setNom_prenom(u.getUsername());
        this.service.create(u);
        Profil p = new Profil();
        p.setLibelle("ADMIN");
        p.setCycle_vie(DataLifeCycle.ACTIF);
        this.service.create(p);
        Profil p1 = new Profil();
        p1.setLibelle("USER");
        p1.setCycle_vie(DataLifeCycle.ACTIF);
        this.service.create(p1);
        UserProfil up = new UserProfil();
        up.setCodeProfil(p);
        up.setCodeuser(u);
        up.setCycle_vie(DataLifeCycle.ACTIF);
        this.service.create(up);
        this.ua.run(req.getSession(), req);
    }
}
