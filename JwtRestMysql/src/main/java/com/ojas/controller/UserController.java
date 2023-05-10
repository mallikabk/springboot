package com.ojas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.entity.User;
import com.ojas.entity.UserRequest;
import com.ojas.entity.UserResponse;
import com.ojas.service.UserService;
import com.ojas.utils.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private JwtUtil util;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		Integer id = service.saveUser(user);
		String body = "User '" + id + "' saved";
		// return new ResponseEntity<String>(body, HttpStatus.OK);
		return ResponseEntity.ok(body);

	}

	@GetMapping("/getAll")
	public List<User> getAll() {
		return service.getAll();

	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));// valiadte  if it is valid jwt token generated
		String token = util.generateJwtToken(request.getUsername());
		User obj=new User();
		return ResponseEntity.ok(new UserResponse(token, "Success fully generated"));

	}
	
	//after login
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p){
		return ResponseEntity.ok("Hello User !"+p.getName());
		
	}
}
