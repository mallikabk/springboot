package com.jwt.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;
	private String password;
	private String email;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name ="rolestab",joinColumns = @JoinColumn(name="id"))
	private List<String> roles;
	public User(String userName, String password, String email, List<String> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	
	
}
