/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.http;

import com.tsoft.security.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    UserService us;

    public CustomDaoAuthenticationProvider() {
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    protected void doAfterPropertiesSet() throws Exception {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);

            try {
                this.us.resetFailAttempts(authentication.getName());
            } catch (Exception var5) {
                Logger.getLogger(CustomDaoAuthenticationProvider.class.getName()).log(Level.SEVERE, (String)null, var5);
            }

            return auth;
        } catch (BadCredentialsException var6) {
            try {
                this.us.updateFailAttempts(authentication.getName());
            } catch (Exception var4) {
                Logger.getLogger(CustomDaoAuthenticationProvider.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

            throw var6;
        } catch (AuthenticationException var7) {
            throw var7;
        }
    }
}
