package com.spring.news.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet; 
import java.util.Set; 

@Entity // Indicates that this class is an entity to be managed by JPA
@Table(name = "roles") // Specifies the name of the table in the database where role information will be stored
public class Role {
    
    @Id // Indicates that the roleId field is the primary key for the Role entity
    @GeneratedValue(strategy = GenerationType.AUTO) // Specifies the strategy for generating unique identifiers for roleId
    private int roleId; 

    private String name; 

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) // Indicates a many-to-many relationship between Role and User
    private Set<User> users = new HashSet<>(); // Set of users associated with this role

    // Constructors, getters, and setters are usually added here but are omitted for brevity
    
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter for users associated with this role
    public Set<User> getUsers() {
        return users;
    }

    // Setter for users associated with this role
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
