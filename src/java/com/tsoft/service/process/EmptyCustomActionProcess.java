/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.service.process;


import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import com.tsoft.metamodel.HibernateEntityProperties;
import com.tsoft.sql.SQLQueryService;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.web.FormUtils;
import com.tsoft.web.model.FormModel;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class EmptyCustomActionProcess implements ActionProcess, InitializingBean {
    @Autowired
    protected ResourceLoader resourceLoader;
    @Autowired
    protected HibernateServiceWrapper hibernateService;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected SQLQueryService sqc;
    @Autowired
    protected FormUtils formUtils;
    @Autowired
    protected ObjectUtils objectUtils;
    @Autowired
    protected HibernateEntityProperties hep;

    public EmptyCustomActionProcess() {
    }

    public String description() {
        return "";
    }

    public Object initData(HttpSession session, HttpServletRequest request) throws Exception {
        return null;
    }

    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object formModel(HttpSession session, HttpServletRequest request) throws Exception {
        FormModel fm = new FormModel();
        fm.setLibelle(this.libelle());
        return fm;
    }

    public void afterPropertiesSet() throws Exception {
    }

    public ResponseEntity<byte[]> download(String pathfile) throws IOException {
        Resource resource = this.resourceLoader.getResource("file:" + pathfile);
        InputStream in = resource.getInputStream();

        ResponseEntity var5;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(resource.contentLength());
            headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            var5 = new ResponseEntity(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }

        return var5;
    }
}
