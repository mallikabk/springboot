package com.ojas.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ojas.entity.User;
import com.ojas.repo.UserRepository;
import com.ojas.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired(required = true)
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Override
	public Integer saveUser(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return repo.save(user).getId();
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<User> findByUsername(String username) {

		return repo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
Optional<User> opt=findByUsername(username);
System.out.println("===================>"+opt);
if(!opt.isPresent()) {
	throw new UsernameNotFoundException("User not exist");
}
User user= opt.get();

		return new org.springframework.security.core.userdetails.User(username,user.getPassword(),
				user.getRoles().stream().map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList()));
	}

}
