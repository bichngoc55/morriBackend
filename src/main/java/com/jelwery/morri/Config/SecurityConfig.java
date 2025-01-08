package com.jelwery.morri.Config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jelwery.morri.Utils.JwtRequestFilter; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtAuthFilter;

    public SecurityConfig(JwtRequestFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/service/**", "/jewelry/**", "/billBan/**","/inventory/**").permitAll()
                        .requestMatchers("/customer/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/supplier/**").permitAll()
                        // .requestMatchers("/inventory/**").hasAnyRole("ADMIN", "INVENTORY_STAFF")
                        .requestMatchers("/sales/**").hasAnyRole("ADMIN", "SALE_STAFF")
                        .requestMatchers("/phieuDichVu/**","/schedule/**", "/attendance/**","/user/**",
                        "/salary/**","/bonusPenalty/**", "/absence/**", "/attendance-record/**","/billMua/**","/productMuaLai/**","/cart/**", "/termAndCondition/**").permitAll()

                        .anyRequest().authenticated()
                ) 
                .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
