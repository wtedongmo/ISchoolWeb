//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.metamodel;

import com.tsoft.exceptions.NoSuchCategorieException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public interface EntityProperties {
    List<Field> getfields(String var1) throws Exception;

    List<Field> getfieldsOnetoMany(String var1) throws Exception;

    List<Field> getfieldsManytoOne(String var1) throws Exception;

    Object getinstance(String var1) throws Exception;

    Class getCategorieClass(String var1) throws Exception;

    boolean isSearchable(String var1) throws Exception;

    boolean isMultipart(String var1) throws Exception;

    Field getfieldlibelle(String var1) throws Exception;

    Field getfieldCode(String var1) throws Exception;

    String[] getfieldsearchable(String var1) throws Exception;

    List<Field> getfieldsByAnnotation(String var1, Class<? extends Annotation> var2) throws NoSuchCategorieException;
}
