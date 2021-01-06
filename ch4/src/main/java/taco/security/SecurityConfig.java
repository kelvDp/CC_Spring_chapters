package taco.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // this just starts the bare-bones security config file
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // for jdbc auth
//    @Autowired
//    DataSource dataSource;

    // for custom auth
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // if only a handful of users, none of which are likely to change. In that case, it may be simple
        //enough to define those users as part of the security configuration.

        // IN-MEMORY STORE:
//        auth.inMemoryAuthentication().withUser("buzz").password("infinity").authorities("ROLE_USER")
//                .and()
//                .withUser("woody").password("bullseye").authorities("ROLE_USER");

        // The in-memory user store is convenient for testing purposes or for very simple
        // applications, but it doesn’t allow for easy editing of users

        // User information is often maintained in a relational database, and a JDBC-based user
        // store seems appropriate.

        // JDBC
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "SELECT username, password, enabled FROM Users WHERE username = ?"
//                ).authoritiesByUsernameQuery(
//                        "SELECT username, authority FROM UserAuthorities WHERE username = ?"
//        ).passwordEncoder(new BCryptPasswordEncoder());

        // LDAP-BACKED USER STORE:
//        auth.ldapAuthentication()
//                .userSearchBase("ou = people")
//                .userSearchFilter("(uid = {0})")
//                .groupSearchBase("ou = people")
//                .groupSearchFilter("member = {0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("passcode");

        // CUSTOM AUTH:
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    } // password encoder for the user detail service

    // this configure method ensures that requests for /design and /orders are only available
    // to authenticated users; all other requests should be permitted for all users
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/design", "/orders")
//                .hasRole("ROLE_USER")
//                .antMatchers("/", "/**").permitAll();

        // using SpEL based security constraints
        http.authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .access("permitAll")// using this way, u can customize constraints
                .and().formLogin().loginPage("/login") // tells spring using custom login page
                .and().logout().logoutSuccessUrl("/")
                .and().headers().frameOptions().sameOrigin(); // needed for h2 console

        // call to authorizeRequests() returns an object
        // on which you can specify URL paths and patterns and the security requirements
        //for those paths. In this case, you specify two security rules:
        //* Requests for /design and /orders should be for users with a granted authority
        //of ROLE_USER.
        //* All requests should be permitted to all users.
    }
}
