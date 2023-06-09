package com.ojas.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;



@Entity
@Table(name="usertab")
public class User
{
  

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

 
    private String name;

   
    private String username;

   
    private String password;

  @ElementCollection(fetch=FetchType.EAGER)
  @CollectionTable
  (
            name="rolestab",
            joinColumns=@JoinColumn(name="id")
         )
  @Column(name="role")
    private Set<String> roles;

public User(String name, String username, String password, Set<String> roles) {
	super();
	this.name = name;
	this.username = username;
	this.password = password;
	this.roles = roles;
}

public User() {
	super();
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
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

public Set<String> getRoles() {
	return roles;
}

public void setRoles(Set<String> roles) {
	this.roles = roles;
}

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", roles="
			+ roles + "]";
}


    

}
