package tacos;

import javax.sql.DataSource;

import org.apache.catalina.core.ApplicationContext;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	// This one is appropriate if you want to store the user details in memory
	// (recommended for simple apps)
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { //you can also call .role which will concatenate the ROLE_ to the
	 * role sent as param
	 * auth.inMemoryAuthentication().withUser("lucian").password("123").authorities(
	 * "ROLE_USER").and() .withUser("john").password("qwerty"); }
	 */

	/**
	 * 	This method configures our user store as a JDBC data source.
	 * 
	 * 	 It also sets the password encoder. Spring comes out of the box with several encoders:
	 * 
	 * * BCryptPasswordEncoder-	Applies	BCryptstrong	hashing	encryption 
	 * * NoOpPasswordEncoder-		Applies	no	encoding
	 * * Pbkdf2PasswordEncoder-		Applies	PBKDF2	encryption
	 * * SCryptPasswordEncoder-		Applies	SCrypt	hashing	encryption
	 * * StandardPasswordEncoder-	AppliesSHA-256hashingencryptionT
	 * 
	 */
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)
//				.usersByUsernameQuery("SELECT username, password, enabled FROM User WHERE username = ?")
//				.authoritiesByUsernameQuery("SELECT username, role FROM User_Role WHERE username  = ?").passwordEncoder(NoOpPasswordEncoder.getInstance());
//	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public PasswordEncoder encoder() {
		return new StandardPasswordEncoder("53cr3t");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().hasAnyRole("ADMIN").and().httpBasic();
	}

}