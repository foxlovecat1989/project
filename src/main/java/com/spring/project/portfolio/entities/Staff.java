package com.spring.project.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("staffs")
    @JoinTable(name = "STAFF_ROLES",
            joinColumns = @JoinColumn(name = "STAFF_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"))
    private Set<Role> roles;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("staffs")
    @JoinTable(name = "STAFF_INVESTOR",
            joinColumns = @JoinColumn(name = "STAFF_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "INVESTOR_ID", referencedColumnName = "id"))
    private Set<Investor> investors = new HashSet<>();

    public Staff() {
    }

    public Staff(String name) {
        this.name = name;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Investor> getInvestors() {
        return investors;
    }

    public void setInvestors(Set<Investor> investors) {
        this.investors = investors;
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Staff{" + "id=" + id + ", name=" + name + ", roles=" + roles);
        sb.append(", Investor={ ");
        investors.stream().mapToInt(investor -> investor.getId()).forEach(id-> sb.append(id).append(", "));
        sb.append(" }");
        return sb.toString();
    }

}
