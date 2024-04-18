package com.learnSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnSphere.entity.Comments;
import com.learnSphere.entity.Users;
import com.learnSphere.services.CommentService;
import com.learnSphere.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ServiceController {
	@Autowired
	UserService uService;
	@Autowired
	CommentService cService;
	
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute Users user) {
		boolean emailExists=uService.checkEmail(user.getEmail());
		if(emailExists==false) {
			uService.addUser(user);
			System.out.println("user registered successfully!");
			return "redirect:/login";
		}
		else {
			System.out.println("user already exists!");
			return "redirect:/register";
		}
		
	}
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,
				@RequestParam("password")String password,
				HttpSession session) {
		
		if(uService.checkEmail(email)) {
		boolean val=uService.validate(email, password);
		//if user is valid
		if(val==true) {	
			Users user = uService.getUser(email); // Assuming you have a method to get the User object
            session.setAttribute("loggedInUser", user); // Saving the User object in session
			if(uService.getUserRole(email).equals("trainer")) {
				return "trainerHome";
			}
			else {
				return "studentHome";
			}
		}
		
		else {
			System.out.println("incorrect credentials, try again!");
			return "login";
		}
	}
		else {
			return "login";
		}
}
	 @PostMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate(); // Invalidate the session
	        return "login"; // Redirect to login page
	    }
	
	
}
