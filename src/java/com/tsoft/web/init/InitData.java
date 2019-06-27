/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.init;

import javax.servlet.http.HttpServletRequest;

public interface InitData {
    void populateData(HttpServletRequest var1) throws Exception;
}
