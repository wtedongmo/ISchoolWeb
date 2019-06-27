/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;

import com.google.common.collect.Sets;
import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.security.model.Droit;
import com.tsoft.security.model.Menu;
import com.tsoft.security.model.Rubrique;
import com.tsoft.security.model.User;
import com.tsoft.security.service.UserService;
import com.tsoft.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = {"/app"},
    produces = {"application/json"}
)
public class UserController {
    @Autowired
    DefaultHibernateService service;
    @Autowired
    ObjectUtils ou;
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
        value = {"/login"},
        method = {RequestMethod.GET}
    )
    ResponseEntity<String> isCurrentUserLoggedIn() {
        return new ResponseEntity(this.getLoggedInUser() != null ? this.getLoggedInUser().getUsername() : null, this.getLoggedInUser() != null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
        value = {"/menu"},
        method = {RequestMethod.GET}
    )
    JSONObject getUserRubriques(HttpSession session, HttpServletRequest req) throws Exception {
        List<Rubrique> listrubriques = new ArrayList();
        Set<Menu> listmenus = new HashSet();
        if (this.userService.isAdmin(this.getLoggedInUser())) {
            listrubriques = this.service.getAll(Rubrique.class);
            listmenus = Sets.newHashSet(this.service.getAll(Menu.class));
        } else {
            Set<Droit> useright = (Set)session.getAttribute("listuserright");
            Iterator var6 = useright.iterator();

            while(var6.hasNext()) {
                Droit d = (Droit)var6.next();
                ((List)listrubriques).add(d.getCodeRubrique());
                listmenus.add(d.getCodeRubrique().getCodeMenu());
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("menus", this.ou.listToJson(listmenus, req));
        jo.put("rubriques", this.ou.listToJson((Collection)listrubriques, req));
        return jo;
    }

    public User getLoggedInUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                user = (User)principal;
            }
        }

        return user;
    }

    @RequestMapping(
        value = {"/forgotpassword"},
        method = {RequestMethod.POST}
    )
    public ResponseEntity<String> forgotpassword(@RequestParam String username, @RequestParam String reponse) throws Exception {
        User principal = (User)this.userService.loadUserByUsername(username);
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        if (!encoder.isPasswordValid(principal.getReponse_question_secrete(), reponse, (Object)null)) {
            return new ResponseEntity("Reponse incorrecte", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            this.userService.changeUserPassword(principal, username, 1);
            return new ResponseEntity("", HttpStatus.OK);
        }
    }

    @RequestMapping(
        value = {"/changepasswordexp"},
        method = {RequestMethod.POST}
    )
    public ResponseEntity<String> changepasswordexp(@RequestParam String username, @RequestParam String oldpassword, @RequestParam String password, @RequestParam String question, @RequestParam String reponse) throws Exception {
        User principal = (User)this.userService.loadUserByUsername(username);
        principal.setQuestion_secrete(question);
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        principal.setReponse_question_secrete(encoder.encodePassword(reponse, (Object)null));
        return this.changepwd(oldpassword, password, principal, 6);
    }

    @RequestMapping(
        value = {"/changepassword"},
        method = {RequestMethod.POST}
    )
    public ResponseEntity<String> changepassword(@RequestParam String oldpassword, @RequestParam String newpassword) {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.changepwd(oldpassword, newpassword, principal, 6);
    }

    private ResponseEntity<String> changepwd(String oldpassword, String newpassword, User principal, int calendaramount) {
        try {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            if (!encoder.isPasswordValid(principal.getPassword(), oldpassword, (Object)null)) {
                return new ResponseEntity("Ancien Mot de passe incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (oldpassword.equalsIgnoreCase(newpassword)) {
                return new ResponseEntity("Ancien Mot de passe identique au nouveau", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                this.userService.changeUserPassword(principal, newpassword, calendaramount);
                return new ResponseEntity("", HttpStatus.OK);
            }
        } catch (Exception var6) {
            return new ResponseEntity(var6.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
