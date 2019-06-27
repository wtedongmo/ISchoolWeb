/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model.superclass;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SimpleEntity implements Serializable {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column
    protected Integer code;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.code);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            SimpleEntity other = (SimpleEntity)obj;
            return Objects.equals(this.code, other.code);
        }
    }

    public SimpleEntity() {
    }

    public SimpleEntity(Integer code) {
        this.code = code;
    }
}
