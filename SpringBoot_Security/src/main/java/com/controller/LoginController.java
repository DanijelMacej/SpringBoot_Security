package com.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping("/mylogin")
	public String loginPage() {
		
		return "thymeleaf/login";
	}
	
	
	
	@PostMapping("/loginProcess")
	public String procesPage() {
		
	
		
		return "redirect:/home";
	}
	
	
	@GetMapping("/home")
	public String homePage(Principal principal,Authentication authentication, Model model) {
		
		String name = principal.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		model.addAttribute("role", authorities);
		model.addAttribute("name", name);
	
		
		return "home";
	}
	
	
	@GetMapping("/admin")
	public String adminPage(Model model,Principal principal,Authentication authentication){
		
		String name = principal.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		

		model.addAttribute("role", authorities);
		model.addAttribute("name", name);
		
		return "admin-page";
	}


}
