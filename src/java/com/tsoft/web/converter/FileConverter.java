/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.converter;


import com.tsoft.annotations.form.Fichier;
import com.tsoft.utils.FileUtils;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileConverter implements ConditionalGenericConverter {
    public FileConverter() {
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.hasAnnotation(Fichier.class);
    }

    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(source==null) return null;
        
        MultipartFile mpf = (MultipartFile)source;
        if (mpf != null && !mpf.isEmpty()) {
            try {
                mpf.transferTo(FileUtils.getUploadedFile(mpf.getOriginalFilename()));
                return mpf.getOriginalFilename();
            } catch (IllegalStateException | IOException var6) {
                Logger.getLogger(FileConverter.class.getName()).log(Level.SEVERE, (String)null, var6);
                return null;
            }
        } else {
            return null;
        }
    }
}
