package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dto.UserRegistrationDto;
import com.service.IUserService;

@Controller
//@RequestMapping("/registration")
public class UserRegistrationController {
	
	
	@Autowired
	IUserService iUserService;
	
	
	@ModelAttribute("userRegistrationDto")
	public UserRegistrationDto getUserRegistrationDto() {
		
		UserRegistrationDto dto = new UserRegistrationDto();
		return dto;
	}
	
//	@GetMapping
	@RequestMapping(value ="/registration" ,method = RequestMethod.GET)
	public String showRegistrationForm() {
		
		
		//naziv stranice u templates, kada je strana u templatesu ne moramo praviti viewResolver vec ce spring automatski prepoznati gde je strana
		return "thymeleaf/registration";
	}
	
//	@PostMapping
	@RequestMapping(value ="/registrationProcess" , method = RequestMethod.POST)
	public String registrationUserAccount(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {
		
		iUserService.save(userRegistrationDto);
		
		return "redirect:/registration?success";
		
	}

}
