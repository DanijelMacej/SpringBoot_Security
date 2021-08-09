package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.service.IUserService;

@Configuration
@EnableWebSecurity

public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private IUserService userService;
	
	
	
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.authenticationProvider(authenticationProvider());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
		.antMatchers("/registration").permitAll()
		.antMatchers("admin").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/mylogin")
		.loginProcessingUrl("/loginProcess")
		.successForwardUrl("/loginProcess")
		.usernameParameter("myemail")
		.passwordParameter("myPassword")
		.failureUrl("/mylogin?error")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		//kada korisnik klikne na logout poslace ga mylogin stranu
		.logoutRequestMatcher( new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/mylogin?logout")
		.permitAll();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		return bCryptPasswordEncoder;
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider  authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	
	

}
