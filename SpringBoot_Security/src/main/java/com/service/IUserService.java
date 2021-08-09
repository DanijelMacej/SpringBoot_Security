package com.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dto.UserRegistrationDto;
import com.model.User;

public interface IUserService extends UserDetailsService {
	
	
	User save (UserRegistrationDto registrationDto);
	
	
	

}
