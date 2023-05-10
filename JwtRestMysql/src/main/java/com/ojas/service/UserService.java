package com.ojas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ojas.entity.User;
@Service
public interface UserService {
Integer saveUser(User user);

List<User> getAll();
Optional<User> findByUsername(String username);
}
