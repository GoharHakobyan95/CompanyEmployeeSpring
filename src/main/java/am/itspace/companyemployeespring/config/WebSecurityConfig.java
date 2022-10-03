package am.itspace.companyemployeespring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/companies")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/employees/add").authenticated()
                .antMatchers("/companies/add").authenticated()
                .antMatchers("/companies/delete").authenticated()
                .anyRequest()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
