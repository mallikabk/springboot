package com.jwt.servericeImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.model.User;
import com.jwt.repo.UserRepo;

@Service
public class UserServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepo uRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void uploadUserDetail(String name, String email, String password, List<String> roles) {
		String encodedPassword=bCryptPasswordEncoder.encode(password);
		User user = new User(name, encodedPassword, email, roles);
		uRepo.save(user);

	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> op = uRepo.findByEmail(username);
		User user = op.get();
		org.springframework.security.core.userdetails.User us = null;
		//
		if (op.isPresent()) {
			// from entity user we are getting roles
			List<String> role = user.getRoles();
			// store the roles
			Set<GrantedAuthority> gas = new HashSet();
			for (String roles : role) {
				gas.add(new SimpleGrantedAuthority(roles));
			}

			us = new org.springframework.security.core.userdetails.User(username, user.getPassword(), gas);

		} else {
			throw new UsernameNotFoundException("username  not found " + user.getUserName());
		}

		return us;

	}
}
