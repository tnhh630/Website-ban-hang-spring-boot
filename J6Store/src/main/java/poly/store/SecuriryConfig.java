package poly.store;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.http.HttpMethod.POST;
import poly.store.entity.Account;
import poly.store.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecuriryConfig extends WebSecurityConfigurerAdapter  {
@Autowired 
AccountService accountService;
@Autowired 
BCryptPasswordEncoder pe;
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// TODO Auto-generated method stub
			auth.userDetailsService(username -> {
				try {
					Account user = accountService.findById(username);
					String password = pe.encode(user.getPassword());
					String[] roles = user.getAuthorities().stream()
							.map(er -> er.getRole().getId())
							.collect(Collectors.toList()).toArray(new String[0]);
					return User.withUsername(username).password(password).roles(roles).build();
				} catch (NoSuchElementException e) {
					throw new UsernameNotFoundException(username + "not found!");
				}
			});
		}
	@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().cors().disable();	
			http.authorizeRequests().antMatchers(POST,"/rest/accounts/create").permitAll();
			http.authorizeHttpRequests()
			.antMatchers("/order/**").authenticated()
			.antMatchers("/admin/**").hasAnyRole("STAF","DIRE")
			.antMatchers("/rest/authorities").hasRole("DIRE")
			.anyRequest().permitAll();
			
			http.formLogin()
			.loginPage("/security/login/form")
			.loginProcessingUrl("/security/login")
			.defaultSuccessUrl("/security/login/success",false)
			.failureUrl("/security/login/error");
			
			http.rememberMe()
			.tokenValiditySeconds(86400);
			
			http.exceptionHandling()
			.accessDeniedPage("/security/unauthoried");
			
			http.logout()
			.logoutUrl("/security/logoff")
			.logoutSuccessUrl("/security/logoff/success");
			
			//OAUTH2 ĐĂNG NHẬP MẠNG XÃ HỘI
			http.oauth2Login()
			// địa chỉ đăng nhập -> form đăng nhập
			.loginPage("/security/login/form")
			// đăng nhập thành công
			.defaultSuccessUrl("/oauth2/login/success",true)
			// đăng nhập thất bại
			.failureUrl("/security/login/error")
			// khai vào link href trong form đăng nhập
			.authorizationEndpoint()
			.baseUri("/oauth2/authorization");
		}
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//cho phép truy xuất rest api từ bên ngoài (domain khác)
	@Override
		public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
		}
}
