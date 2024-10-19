package com.jelwery.morri.Config;

import com.jelwery.morri.Model.ROLE;
import com.jelwery.morri.Utils.JWT;
//import com.jelwery.morri.Utils.JwtRequestFilter;
import com.jelwery.morri.Utils.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/service/**").permitAll()
                        .requestMatchers("/jewelry/**").permitAll()
                        .requestMatchers("/customer/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(ROLE.ADMIN.name())
                        .requestMatchers("/supplier/**").hasAuthority(ROLE.ADMIN.name())
                        .requestMatchers("/inventory/**").hasAuthority(ROLE.INVENTORY_STAFF.name())
                        .requestMatchers("/inventory/**").hasAuthority(ROLE.ADMIN.name())
                        .requestMatchers("/sales/**").hasAuthority(ROLE.SALE_STAFF.name())
                        .requestMatchers("/sales/**").hasAuthority(ROLE.ADMIN.name())
                        .anyRequest().authenticated()


                )
//                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
