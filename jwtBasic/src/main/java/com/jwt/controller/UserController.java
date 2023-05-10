package com.jwt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jwt.servericeImpl.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserServiceImpl service;
	@GetMapping("/reg")
	public String showReg() {
		return "registationPage.html";
		
	}
	@PostMapping("/upload")
	public String upload(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("roles") List<String> roles, HttpSession session) {
		//String encodePwd = pwdEncoder.encode(password);
		service.uploadUserDetail(name,email,password,roles);
		String msg="SuccessFully Registerd";
		session.setAttribute("result", msg);
		String text="User : "+name+" , created with roles:"+roles;
		//boolean sent=emailUtil.send(email, "WELCOME TO USER!", text);
//		System.out.println(sent);
//		if(sent) {
//			msg+="-Email Also sent";
//		}else {
//			msg+="-Email failed to send";
//		}
		return "registationPage";
		
	}
//	@GetMapping("/upload")
//	public String fileUpload(HttpSession session) throws IllegalStateException, IOException {
//		session.setAttribute("result", "No Such Operation Allowed ");
//		return "registationPage";
//	}
}

