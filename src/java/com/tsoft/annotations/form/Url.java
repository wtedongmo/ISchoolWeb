/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.annotations.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author tchipi
 */
@Target(value = {ElementType.METHOD ,ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Url {
   public String name() default ""; 
}
