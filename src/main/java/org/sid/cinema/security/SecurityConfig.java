package org.sid.cinema.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder=passwordEncoder();
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("12345")).roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("123")).roles("USER");
		auth.inMemoryAuthentication().withUser("utilisateur1").password(passwordEncoder.encode("54321")).roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/**","/js/**","/images/**","/imageFilm/**","/categories/**","/cinemas/**","/films/**","/places/**","/projections/**","/salles/**","/villes/**","/tickets/**","/seances/**","/payerTickets/**").permitAll()
				.anyRequest()
					.authenticated().and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/cinema") 
				.failureUrl("/error.html");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}