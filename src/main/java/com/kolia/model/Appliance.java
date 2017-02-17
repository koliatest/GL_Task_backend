package com.kolia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "appliances")
@Entity
public class Appliance {

    @GeneratedValue
    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appliance")
    private List<InfoEntry> infoEntries = new ArrayList<InfoEntry>(0);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk")
    @JsonIgnore
    private User user;

    @JsonBackReference
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonManagedReference
    public List<InfoEntry> getInfoEntries() {
        return infoEntries;
    }

    public void setInfoEntries(List<InfoEntry> infoEntries) {
        this.infoEntries = infoEntries;
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
        if (!(obj instanceof Appliance))
            return false;
        Appliance other = (Appliance) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
