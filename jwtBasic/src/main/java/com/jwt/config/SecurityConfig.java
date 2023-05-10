package com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService uService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(uService).passwordEncoder(bCryptPasswordEncoder);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/home", "/reg", "/upload", "/index").permitAll().antMatchers("/admin")
				.hasAuthority("ADMIN").antMatchers("/emp").hasAuthority("EMPLOYEE").antMatchers("/std")
				.hasAuthority("STUDENT").anyRequest().authenticated().and()

				.formLogin().defaultSuccessUrl("/welcome", true)

//Logout Details
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

//Exception Details
				.and().exceptionHandling().accessDeniedPage("/denied");
	}

}
