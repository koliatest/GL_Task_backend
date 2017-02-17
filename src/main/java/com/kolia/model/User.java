package com.kolia.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Appliance> applianceList = new HashSet<Appliance>(0);

    @JsonManagedReference
    public Set<Appliance> getApplianceList() {
        return applianceList;
    }

    public void setApplianceList(HashSet<Appliance> applianceList) {
        this.applianceList = applianceList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
