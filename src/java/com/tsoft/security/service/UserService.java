/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.service;


import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.security.model.Droit;
import com.tsoft.security.model.User;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends HibernateService<User> implements UserDetailsService {
    public UserService() {
    }

    public void delete(User entity) throws Exception {
        throw new BusinessException("Suppression des Utilisateurs interdite");
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User modeluser = (User)this.getOne(User.class, "select u from User u where u.username like '" + username + "' ");
            if (modeluser == null) {
                throw new UsernameNotFoundException("Username or Password incorrect");
            } else {
                return modeluser;
            }
        } catch (Exception var3) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, (String)null, var3);
            throw new UsernameNotFoundException(var3.getMessage());
        }
    }

    public void changeUserPassword(User principal, String newpassword, int nbremois) throws Exception {
        principal.setPassword(newpassword);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        principal.setDate_changementpwd(Date.from(now.atZone(ZoneId.of("UTC")).toInstant()));
        principal.setDate_expirationpwd(Date.from(now.plusMonths((long)nbremois).atZone(ZoneId.of("UTC")).toInstant()));
        this.update(principal);
    }

    public boolean isAdmin(User principal) {
        return principal.getAuthorities().stream().anyMatch((ga) -> {
            return ga.getAuthority().equalsIgnoreCase("ADMIN");
        });
    }

    public Set<Droit> retrieveUserRight(User principal) throws Exception {
        Set<Droit> listrights = new HashSet();
        Iterator var3 = principal.getAuthorities().iterator();

        while(var3.hasNext()) {
            GrantedAuthority ga = (GrantedAuthority)var3.next();
            String query = "select m from Droit m where m.codeProfil.libelle like '" + ga.getAuthority() + "'" + "             and   m.cycle_vie  like '" + DataLifeCycle.ACTIF + "'";
            List<Droit> listrights1 = this.getAll((Class)null, query);
            if (listrights1 != null) {
                listrights.addAll(listrights1);
            }
        }

        return listrights;
    }

    public void setUserSession(HttpSession session, User principal) throws Exception {
        session.setAttribute("listuserright", this.retrieveUserRight(principal));
    }

    public void resetFailAttempts(String name) throws Exception {
        User u = (User)this.loadUserByUsername(name);
        u.setLogin_attempts(new Byte("0"));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        u.setLastaccessdate(Date.from(now.atZone(ZoneId.of("UTC")).toInstant()));
        this.update(u);
    }

    public void updateFailAttempts(String name) throws Exception {
        User u = (User)this.loadUserByUsername(name);
        u.setLogin_attempts((byte)(u.getLogin_attempts() + 1));
        this.update(u);
    }
}
