package com.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.UserRegistrationDto;
import com.model.Role;
import com.model.User;
import com.repository.UserRepository;



@Service
public class UserServiceImpl implements IUserService {
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User save(UserRegistrationDto registrationDto) {
		String encode = passwordEncoder.encode(registrationDto.getPassword());
		registrationDto.setPassword(encode);
		
		User user = new User(registrationDto.getFirstname(), registrationDto.getLastname(), registrationDto.getEmail(), registrationDto.getPassword(), java.util.Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}
	
	
	
	public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role>roles){
		
    List<SimpleGrantedAuthority> collectRole = roles.stream().map(role -> new  SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			
    
    return collectRole;
	}
	

	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		User findByEmail = userRepository.findByEmail(email);
		
		if (findByEmail ==null) {
			
			throw new BadCredentialsException("Invalid email/password");
			
		}
		return new org.springframework.security.core.userdetails.User(findByEmail.getEmail(), findByEmail.getPassword(), getAuthorities(findByEmail.getRoles()));
	}

}
