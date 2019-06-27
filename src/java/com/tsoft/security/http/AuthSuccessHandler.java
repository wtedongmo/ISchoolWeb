/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.http;

import com.tsoft.security.model.User;
import com.tsoft.security.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger LOGGER = Logger.getLogger(AuthSuccessHandler.class.getName());
    @Autowired
    UserService us;

    public AuthSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(200);
        LOGGER.log(Level.INFO, "{0} got is connected ", authentication.getName());

        try {
            this.us.setUserSession(request.getSession(), (User)authentication.getPrincipal());
        } catch (Exception var5) {
            Logger.getLogger(AuthSuccessHandler.class.getName()).log(Level.SEVERE, (String)null, var5);
        }

        PrintWriter writer = response.getWriter();
        writer.write(authentication.getName());
        writer.flush();
    }
}
