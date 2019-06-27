/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model.superclass;

import com.tsoft.annotations.form.Secure;
import com.tsoft.utils.enumerations.DataLifeCycle;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class LifeCycleEntity extends AuditEntity {
    @Column
    @Enumerated(EnumType.STRING)
    @Secure
    protected DataLifeCycle cycle_vie;

    public LifeCycleEntity() {
    }

    public DataLifeCycle getCycle_vie() {
        return this.cycle_vie;
    }

    public void setCycle_vie(DataLifeCycle cycle_vie) {
        this.cycle_vie = cycle_vie;
    }
}
