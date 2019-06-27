/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.etl;


import org.springframework.batch.item.ItemProcessor;

public class EtlProcessor implements ItemProcessor {
    public EtlProcessor() {
    }

    public Object process(Object i) throws Exception {
        return i;
    }
}
