/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.tsoft.annotations.form.Select;
import com.tsoft.security.model.superclass.LifeCycleEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class UserProfil extends LifeCycleEntity {
    private static final long serialVersionUID = 1L;
    @JoinColumn(
        name = "code_profil",
        referencedColumnName = "code"
    )
    @ManyToOne
    @Select
    private Profil codeProfil;
    @JoinColumn(
        name = "code_user",
        referencedColumnName = "code"
    )
    @ManyToOne
    private User codeuser;

    public UserProfil() {
    }

    public UserProfil(Integer code) {
        this.code = code;
    }

    public Profil getCodeProfil() {
        return this.codeProfil;
    }

    public void setCodeProfil(Profil codeProfil) {
        this.codeProfil = codeProfil;
    }

    public User getCodeuser() {
        return this.codeuser;
    }

    public void setCodeuser(User codeuser) {
        this.codeuser = codeuser;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.code != null ? this.code.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof UserProfil)) {
            return false;
        } else {
            UserProfil other = (UserProfil)object;
            return (this.code != null || other.code == null) && (this.code == null || this.code.equals(other.code));
        }
    }

    public String toString() {
        return "com.tsoft.model.Userprofil[ code=" + this.code + " ]";
    }
}
