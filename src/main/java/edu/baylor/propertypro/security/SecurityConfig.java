package edu.baylor.propertypro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
		.and()
			.authorizeRequests()
				.antMatchers("/index.html", "/", "/login", "/*.js", "/favicon.ico").permitAll()
				.antMatchers(HttpMethod.POST, "/api/listings/*/request", "/api/listings/*/offer", "/api/listings/*/favorite").hasRole("BUYER")
				.antMatchers(HttpMethod.GET, "/api/listings/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/listings").hasRole("REALTOR")
				.antMatchers(HttpMethod.POST, "/api/reviews").hasRole("BUYER")
				.antMatchers(HttpMethod.POST, "/api/responses").hasRole("REALTOR")
				.antMatchers("/api/auth/register/*").permitAll()
				.antMatchers("/propertypro-stomp").permitAll()
				.antMatchers("/api/profile").authenticated()
				.anyRequest().permitAll()
		.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
