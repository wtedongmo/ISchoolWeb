/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public class MyPropertyFilter extends SimpleBeanPropertyFilter {
    public MyPropertyFilter() {
    }

    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (!writer.getName().equals("intValue")) {
            writer.serializeAsField(pojo, jgen, provider);
        } else {
            writer.serializeAsField(pojo, jgen, provider);
            writer.serializeAsOmittedField(pojo, jgen, provider);
        }
    }

    protected boolean include(BeanPropertyWriter writer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected boolean include(PropertyWriter writer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
