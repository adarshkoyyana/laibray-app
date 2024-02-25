package com.libraryapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryapp.entities.User;
import com.libraryapp.services.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	@Autowired
	UserService accService;
	
	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
	    Exception exception = (Exception) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	    if (exception != null) {
	        String errorMessage = exception.getMessage();
	        System.out.println("Error message: " + errorMessage); // Add this line for debugging
	        model.addAttribute("errorMessage", errorMessage);
	        request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	    }
	    return "security/login";
	}
//	@GetMapping(value="/login")
//	public String login() {
//		return "security/login.html";
//	}

	
	@GetMapping(value="/logout")
	public String logout() {
		return "security/logout.html";
	}
	
	@GetMapping(value="/register")
	public String register(Model model) {
		model.addAttribute("newAccount", new User());
		return "security/register.html";
	}
	
//	@PostMapping(value="/register/save")
//	public String saveNewAccount(User account) {
//		account.setPassword(pwEncoder.encode(account.getPassword()));
//		accService.save(account);
//		return "redirect:/register";
//	}
//	
//	@GetMapping(value="/register/accountcreated")
//	public String accountCreated() {
//		return "security/register.html";
//	}	
	@PostMapping("/register/save")
    public String saveNewAccount(User account, Model model) {
        account.setPassword(pwEncoder.encode(account.getPassword()));
        accService.save(account);
        model.addAttribute("successMessage", "Account created successfully. Please login.");
        return "redirect:/register";
    }

    @GetMapping("/register/accountcreated")
    public String accountCreated(@RequestParam(name = "successMessage", required = false) String successMessage, Model model) {
        model.addAttribute("successMessage", successMessage);
        return "security/account-created";
    }
}
 
