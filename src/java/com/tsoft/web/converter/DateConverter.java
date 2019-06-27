/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.converter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

public class DateConverter implements GenericConverter {
    public DateConverter() {
    }

    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> result = new HashSet();
        result.add(new ConvertiblePair(String.class, Date.class));
        result.add(new ConvertiblePair(Date.class, String.class));
        return result;
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            return targetType.getType().equals(Date.class) ? new Date((String)source) : null;
        } catch (Exception var5) {
            return null;
        }
    }
}