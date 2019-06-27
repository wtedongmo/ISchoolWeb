/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.converter;


import com.tsoft.dao.hibernate.service.HibernateService;
import com.tsoft.metamodel.HibernateEntityProperties;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.stereotype.Component;

@Component
public class JoinColumnConverter implements ConditionalGenericConverter {
    @Autowired
    @Qualifier("defaultHibernateService")
    HibernateService service;
    @Autowired
    HibernateEntityProperties emm;

    public JoinColumnConverter() {
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.hasAnnotation(ManyToOne.class) || sourceType.hasAnnotation(ManyToOne.class) || targetType.hasAnnotation(JoinColumn.class) || sourceType.hasAnnotation(JoinColumn.class);
    }

    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(source==null) return null;
        
        if (sourceType.getType().equals(String.class) && source != null) {
            try {
                Object code = null;

                try {
                    code = Integer.parseInt((String)source);
                } catch (Exception var6) {
                    code = source;
                }

                return this.service.getById(code, targetType.getType());
            } catch (Exception var7) {
                return null;
            }
        } else {
            if (source != null) {
                ;
            }

            return null;
        }
    }
}
