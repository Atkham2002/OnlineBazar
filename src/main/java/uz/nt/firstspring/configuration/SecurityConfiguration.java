package uz.nt.firstspring.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.nt.firstspring.security.JwtEntryPoint;
import uz.nt.firstspring.security.JwtFilter;
import uz.nt.firstspring.service.impl.UserService;

@Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(provider());
//                .inMemoryAuthentication()
//                .withUser("username")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .and()
//                .withUser("Faxriyor")
//                .password(passwordEncoder.encode("123"))
//                .roles("ADMIN");
    }

    private DaoAuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(userService);

        return provider;
    }



    //UserDetails
    //UserDetailsService
    //DaoAuthenticationProvider

}
