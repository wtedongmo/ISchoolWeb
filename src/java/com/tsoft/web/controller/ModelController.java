/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.web.controller;

import com.google.common.collect.Sets;
import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Secure;
import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import com.tsoft.exceptions.BusinessException;
import com.tsoft.exceptions.NoSuchCategorieException;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.security.model.Droit;
import com.tsoft.security.model.Rubrique;
import com.tsoft.security.model.Services;
import com.tsoft.security.model.User;
import com.tsoft.security.service.UserService;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.enumerations.Permission;
import com.tsoft.utils.enumerations.TypeService;
import com.tsoft.utils.web.FieldUtils;
import com.tsoft.utils.web.FormUtils;
import com.tsoft.utils.web.datatables.DatatableColumnModel;
import com.tsoft.web.converter.RubriquePropertyEditor;
import com.tsoft.web.model.FormModel;
import com.tsoft.web.model.InputModel;
import com.tsoft.web.model.InputType;
import com.tsoft.web.model.ListModel;
import com.tsoft.web.model.SelectModel;
import com.tsoft.web.model.Tab;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class ModelController extends MainController {
    @Autowired
    HibernateEntityProperties epi;
    @Autowired
    private DefaultHibernateService dhs;
    @Autowired
    private UserService userservice;
    @Autowired
    private FormUtils fu;
    @Autowired
    ObjectUtils objectUtils;
    @Autowired
    RubriquePropertyEditor rpe;

    public ModelController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Rubrique.class, this.rpe);
    }

    @RequestMapping(
        value = {"/{categorie}/listmodel"},
        method = {RequestMethod.GET}
    )
    public ListModel getlistmodel(@PathVariable Rubrique categorie, HttpSession session, HttpServletRequest req) throws NoSuchCategorieException, Exception {
        if (!this.hasViewPermission(categorie, session)) {
            throw new BusinessException("Access Denied to the rubrique " + categorie.getLibelle());
        } else {
            ListModel result = new ListModel();
            DatatableColumnModel cm = new DatatableColumnModel();
            cm.setName("Index");
            cm.setbVisible(false);
            cm.setbSortable(false);
            cm.setsTitle(cm.getName());
            cm.setData(cm.getName());
            result.addColumns(cm);
            String[] columns = new String[0];
            if (StringUtils.hasText(categorie.getGrille_description())) {
                columns = categorie.getGrille_description().split(",");
            } else {
                columns = this.epi.getfieldsName(categorie.getReference());
            }

            String[] var7 = columns;
            int var8 = columns.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String column = var7[var9];
                InputModel im = this.fu.getInputModelFromField(column, categorie.getReference());
                Field f = ReflectionUtils.findField(this.epi.getCategorieClass(categorie.getReference()), im.getName());
                if (!f.isAnnotationPresent(Secure.class) && !FieldUtils.getInputType(f).equals(InputType.FILE)) {
                    cm = new DatatableColumnModel();
                    cm.setsTitle(im.getLabel());
                    cm.setName(im.getName());
                    if (f.isAnnotationPresent(JoinColumn.class)) {
                        cm.setData(f.getName() + "." + (this.epi.getfieldlibelle(f.getType().getSimpleName()) != null ? this.epi.getfieldlibelle(f.getType().getSimpleName()).getName() : this.epi.getfieldCode(f.getType().getSimpleName()).getName()));
                    } else {
                        cm.setData(f.getName());
                    }

                    if (FieldUtils.isRequiredField(f)) {
                        result.getColumns().add(1, cm);
                    } else {
                        result.addColumns(cm);
                    }
                }
            }

            result.setColumns(result.getColumns().subList(0, result.getColumns().size() > 6 ? 6 : result.getColumns().size()));

            for(int i = 0; i < result.getColumns().size(); ++i) {
                ((DatatableColumnModel)result.getColumns().get(i)).setaTargets(i);
            }

            result.setCategorie(categorie.getReference());
            result.setLibelle(categorie.getLibelle());
            result.setIsSearchable(this.epi.isSearchable(categorie.getReference()));
            result.setServices(this.getRubriqueServices(categorie, session, req));
            return result;
        }
    }

    @RequestMapping(
        value = {"/{categorie}/formmodel1/{sfield}"},
        method = {RequestMethod.GET}
    )
    public FormModel getformmodel1(@PathVariable Rubrique categorie, HttpSession session, @PathVariable String sfield) throws Exception {
        return this.getformmodel(categorie, sfield, session);
    }

    @RequestMapping(
        value = {"/{categorie}/formmodel"},
        method = {RequestMethod.GET}
    )
    public FormModel getformmodel(@PathVariable Rubrique categorie, String fieldtoputreadonly, HttpSession session) throws Exception {
        FormModel result = new FormModel();
        List<Tab> listtabs = new ArrayList();
        result.setAccess(this.hasWritePermission(categorie, session));
        result.setCategorie(categorie.getLibelle());
        result.setLibelle(this.epi.getfieldlibelle(categorie.getReference()) != null ? this.epi.getfieldlibelle(categorie.getReference()).getName() : null);
        result.setIsmultipart(this.epi.isMultipart(categorie.getReference()));
        if (StringUtils.hasText(categorie.getForm_description())) {
            String[] tabs = categorie.getForm_description().split("\n");
            String[] var7 = tabs;
            int var8 = tabs.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String tab = var7[var9];
                String tabvalue = tab.split("=")[1];
                String tabname = tab.split("=")[0];
                String inputs = tabvalue.split(";")[0];
                Tab t = new Tab();
                t.setNom(tabname);
                t.setNbrepannels(Integer.parseInt(tabvalue.split(";")[1]));
                String[] var15 = inputs.split(",");
                int var16 = var15.length;

                for(int var17 = 0; var17 < var16; ++var17) {
                    String input = var15[var17];
                    InputModel im = this.fu.getInputModelFromField(input, categorie.getReference());
                    t.addInput(im);
                    if (im.getType().equals(InputType.SELECT)) {
                        result.getSelectmodels().put(im.getName(), this.fu.getSelects(im.getName(), this.epi.getCategorieClass(categorie.getReference())));
                    }

                    if (im.getType().equals(InputType.SELECTENUM)) {
                        result.getSelectmodels().put(im.getName(), FieldUtils.getSelectsEnumerated(im.getName(), this.epi.getCategorieClass(categorie.getReference())));
                    }
                }

                listtabs.add(t);
            }
        } else {
            List<InputModel> lims = new ArrayList();
            List<InputModel> limsec = new ArrayList();
            Iterator var24 = this.epi.getfields(categorie.getReference()).iterator();

            while(var24.hasNext()) {
                Field f = (Field)var24.next();
                InputModel im = this.fu.getInputModelFromField(f, fieldtoputreadonly != null ? f.getName().equalsIgnoreCase(fieldtoputreadonly) : false);
                if (FieldUtils.getInputType(f).equals(InputType.SELECT)) {
                    result.getSelectmodels().put(f.getName(), this.fu.getSelects(f));
                }

                if (FieldUtils.getInputType(f).equals(InputType.SELECTENUM)) {
                    result.getSelectmodels().put(f.getName(), FieldUtils.getSelectsEnumerated(f));
                }

                if (FieldUtils.isRequiredField(f)) {
                    lims.add(0, im);
                } else if (f.isAnnotationPresent(Secure.class)) {
                    limsec.add(im);
                } else {
                    lims.add(im);
                }
            }

            Tab t = new Tab();
            t.setNom("Infos Générales");
            t.setNbrepannels(lims.size() % 4 == 0 ? 4 : (lims.size() % 3 == 0 ? 3 : 2));
            t.setInputs(lims);
            listtabs.add(t);
            if (!limsec.isEmpty()) {
                Tab t1 = new Tab();
                t1.setNom("Sécurité");
                t1.setNbrepannels(2);
                t1.setInputs(limsec);
                listtabs.add(t1);
            }
        }

        result.setTabset(listtabs);
        Iterator var21 = this.epi.getfieldsManytoOne(categorie.getReference()).iterator();

        Field f;
        while(var21.hasNext()) {
            f = (Field)var21.next();
            result.getJoincolumns().add(f.getName());
        }

        var21 = this.epi.getfieldsOnetoMany(categorie.getReference()).iterator();

        while(var21.hasNext()) {
            f = (Field)var21.next();
            SelectModel sm = new SelectModel();
            sm.setCode(f.getName());
            sm.setLibelle(f.isAnnotationPresent(Label.class) ? ((Label)f.getAnnotation(Label.class)).value() : f.getName());
            result.addRelations(sm);
        }

        return result;
    }

    public boolean hasWritePermission(Rubrique categorie, HttpSession session) {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (this.userservice.isAdmin(principal)) {
            return true;
        } else {
            boolean result = false;
            Set<Droit> listusermenu = (Set)session.getAttribute("listuserright");
            if (listusermenu == null) {
                listusermenu = new HashSet();
            }

            Iterator var6 = ((Set)listusermenu).iterator();

            while(var6.hasNext()) {
                Droit m = (Droit)var6.next();
                if (m.getCodeRubrique() != null && m.getCodeRubrique().equals(categorie)) {
                    if (m.getCodeService().getReference().equals(Permission.READ_ONLY)) {
                        break;
                    }

                    if (m.getCodeService().getReference().equals(Permission.READ_WRITE)) {
                        return true;
                    }
                }
            }

            return result;
        }
    }

    public boolean hasViewPermission(Rubrique categorie, HttpSession session) {
        boolean result = false;
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (this.userservice.isAdmin(principal)) {
            return true;
        } else {
            Set<Droit> listusermenu = (Set)session.getAttribute("listuserright");
            return listusermenu.stream().anyMatch((m) -> {
                return categorie.equals(m.getCodeRubrique());
            }) ? true : result;
        }
    }

    public JSONArray getRubriqueServices(Rubrique categorie, HttpSession session, HttpServletRequest req) throws Exception {
        Set<Services> result = new HashSet();
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (this.userservice.isAdmin(principal)) {
            result = Sets.newHashSet(categorie.getServices());
        } else {
            Set<Droit> droits = (Set)session.getAttribute("listuserright");
            Iterator var7 = droits.iterator();

            while(var7.hasNext()) {
                Droit d = (Droit)var7.next();
                if (categorie.equals(d.getCodeRubrique()) && d.getCodeService().getType().equals(TypeService.ACTION)) {
                    result.add(d.getCodeService());
                }
            }
        }

        return this.objectUtils.listToJson(result, req);
    }
}
