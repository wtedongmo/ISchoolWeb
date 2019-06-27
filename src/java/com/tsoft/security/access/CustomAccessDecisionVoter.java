/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.access;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

public class CustomAccessDecisionVoter implements AccessDecisionVoter {
    public CustomAccessDecisionVoter() {
    }

    public boolean supports(ConfigAttribute attribute) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean supports(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int vote(Authentication authentication, Object object, Collection attributes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

