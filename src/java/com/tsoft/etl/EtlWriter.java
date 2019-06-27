/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.etl;

import com.tsoft.dao.hibernate.service.DefaultHibernateService;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtlWriter implements ItemWriter {
    @Autowired
    private DefaultHibernateService dhs;

    public EtlWriter() {
    }

    public void write(List list) throws Exception {
        this.dhs.saveAll(list);
    }
}
