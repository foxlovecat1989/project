package com.spring.project.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Investor implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Integer balance;

    @Column
    private String code;

    @Column
    private Boolean enabled;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "investor", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("investor")
    private Set<Portfolio> portfolios;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "investor", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("investor")
    private Set<Watch> watchs;

    public Investor() {
    }

    public Investor(String username) {
        this.username = username;
    }

    public Investor(String username, String password, String email, Integer balance, Date date) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
        this.date = date;
    }

    public Investor(String username, String password, String email, Integer balance, Boolean enabled, Date date) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
        this.enabled = enabled;
        this.date = date;
    }
    
 
    public Investor(String username, String password, String email, Integer balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
        enabled = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public Set<Watch> getWatchs() {
        return watchs;
    }

    public void setWatchs(Set<Watch> watchs) {
        this.watchs = watchs;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Investor{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", balance=" + balance + ", code=" + code + ", enabled=" + enabled + ", date=" + date + ", roles=" + roles + ", portfolios=" + portfolios + ", watchs=" + watchs + '}';
    }

    

}
