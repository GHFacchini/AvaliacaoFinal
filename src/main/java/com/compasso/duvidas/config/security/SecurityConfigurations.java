package com.compasso.duvidas.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.compasso.duvidas.repositories.UsuarioRepository;


@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
		
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll() //UTILIZADO PARA DAR ACESSO AO CONSOLE DO H2
		.antMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/fechar/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.PUT, "/cursos/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/cursos/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/respostas/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/sprints").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/sprints/**").hasRole("MODERADOR") //testar
		.antMatchers(HttpMethod.GET, "/sprints").hasRole("MODERADOR")
		.antMatchers(HttpMethod.PUT, "/sprints/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/sprints/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/turmas").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/turmas/**").hasRole("MODERADOR") //testar
		.antMatchers(HttpMethod.GET, "/turmas").hasRole("MODERADOR")
		.antMatchers(HttpMethod.PUT, "/turmas/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/turmas/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/usuarios").hasRole("MODERADOR") //testar
		.antMatchers(HttpMethod.GET, "/usuarios").hasRole("MODERADOR")
		.antMatchers(HttpMethod.PUT, "/usuarios/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/usuarios/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.POST, "/perfis").hasRole("MODERADOR") //testar
		.antMatchers(HttpMethod.GET, "/perfis").hasRole("MODERADOR")
		.antMatchers(HttpMethod.PUT, "/perfis/*").hasRole("MODERADOR")
		.antMatchers(HttpMethod.DELETE, "/perfis/*").hasRole("MODERADOR")
		.anyRequest().authenticated()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
}
