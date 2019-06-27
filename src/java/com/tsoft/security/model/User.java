/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsoft.annotations.form.Label;
import com.tsoft.annotations.form.Libelle;
import com.tsoft.annotations.form.ReadOnly;
import com.tsoft.annotations.form.Secret;
import com.tsoft.dao.Dao;
import com.tsoft.security.service.UserService;
import com.tsoft.utils.enumerations.DataLifeCycle;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Dao(UserService.class)
public class User extends Person implements UserDetails {
    @NotNull
    @Size(
        min = 1,
        max = 100
    )
    @Column(
        nullable = false,
        length = 100,
        unique = true
    )
    @Libelle
    private String username;
    @NotNull
    @Size(
        min = 1,
        max = 100
    )
    @Column(
        nullable = false,
        length = 100
    )
    private String password;
    @Column
    @ReadOnly
    @Label("Date Dernière Accès")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastaccessdate;
    @Column
    @ReadOnly
    @Label("Date Changement Mot de Passe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_changementpwd;
    @Column
    @ReadOnly
    @Label("Date Expiration Mot de Passe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_expirationpwd;
    @Column
    private boolean accountNonExpired;
    @Column
    @Label("Tentative de Connexion")
    private byte login_attempts;
    @Column
    @ReadOnly
    @Secret
    private String question_secrete;
    @Column
    @ReadOnly
    @Secret
    private String reponse_question_secrete;
    @JsonIgnore
    @OneToMany(
        mappedBy = "codeuser"
    )
    private List<UserProfil> ListProfil = new ArrayList();

    public User() {
    }

    public User(Integer code) {
        this.code = code;
    }

    public User(Integer code, String username, String password) {
        this.code = code;
        this.username = username;
        this.password = password;
    }

    public Date getLastaccessdate() {
        return this.lastaccessdate;
    }

    public void setLastaccessdate(Date lastaccessdate) {
        this.lastaccessdate = lastaccessdate;
    }

    public Date getDate_changementpwd() {
        return this.date_changementpwd;
    }

    public void setDate_changementpwd(Date date_changementpwd) {
        this.date_changementpwd = date_changementpwd;
    }

    public Date getDate_expirationpwd() {
        return this.date_expirationpwd;
    }

    public void setDate_expirationpwd(Date date_expirationpwd) {
        this.date_expirationpwd = date_expirationpwd;
    }

    public String getQuestion_secrete() {
        return this.question_secrete;
    }

    public void setQuestion_secrete(String question_secrete) {
        this.question_secrete = question_secrete;
    }

    public String getReponse_question_secrete() {
        return this.reponse_question_secrete;
    }

    public void setReponse_question_secrete(String reponse_question_secrete) {
        this.reponse_question_secrete = reponse_question_secrete;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public byte getLogin_attempts() {
        return this.login_attempts;
    }

    public void setLogin_attempts(byte login_attempts) {
        this.login_attempts = login_attempts;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList();
        this.ListProfil.stream().filter((up) -> {
            return up.getCycle_vie() != null && up.getCycle_vie().equals(DataLifeCycle.ACTIF);
        }).map((up) -> {
            return up.getCodeProfil().getLibelle();
        }).forEach((auth) -> {
            authorities.add(new SimpleGrantedAuthority(auth.length() == 0 ? "ANONYMOUS" : auth));
        });
        return authorities;
    }

    public List<UserProfil> getListProfil() {
        return this.ListProfil;
    }

    public void setListProfil(List<UserProfil> ListProfil) {
        this.ListProfil = ListProfil;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.login_attempts <= 6;
    }

    public boolean isCredentialsNonExpired() {
        if (this.date_expirationpwd != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(this.date_expirationpwd.toInstant(), ZoneId.of("UTC"));
            return ldt.isAfter(LocalDateTime.now(ZoneId.of("UTC")));
        } else {
            return false;
        }
    }

    public boolean isEnabled() {
        return this.getCycle_vie() == null ? false : this.getCycle_vie().equals(DataLifeCycle.ACTIF);
    }

    public static void main(String[] args) {
        UserService us = new UserService();
        Type t = us.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)t;
        Type categorietype = pt.getActualTypeArguments()[0];
        String subcategorie = ((Class)categorietype).getSimpleName();
        System.out.println(subcategorie + "    voici mes dos");
    }
}
