package com.BlogApp.Config;

import com.BlogApp.Security.CustomUserDetailService;
import com.BlogApp.Security.JWTAutheticationEntryPoint;
import com.BlogApp.Security.JwtAutheticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private JWTAutheticationEntryPoint autheticationEntryPoint;

	@Bean
	public JwtAutheticationFilter jwtAutheticationFilter() {
		return new JwtAutheticationFilter();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(autheticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			//.antMatchers( "/api/**","v2/api-docs/**").permitAll()
			.anyRequest()
			.permitAll();

		http.addFilterBefore(jwtAutheticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder());
	}

	//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//	UserDetails	akshay=User.builder().username("akshay").password(passwordEncoder().encode("ak")).roles("USER").build();
//	UserDetails	admin=User.builder().username("admin").password(passwordEncoder().encode("ak")).roles("ADMIN").build();
//	return new InMemoryUserDetailsManager(akshay,admin);
//	}


	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
