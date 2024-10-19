package com.jelwery.morri.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    final private JWT jwtUtil = new JWT();
//    @Autowired
//    private UserDetailsService userDetailsService;


//   final  private UserDetailsService userDetailsService = new UserDetailsService() {
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            return null;
//        }
//    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
            String role = jwtUtil.extractRole(jwt);
        }

//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Get user details from the database (or any other source)
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
//
//            if (userDetails != null && jwtUtil.validateToken(jwt)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }

        filterChain.doFilter(request, response);
    }
}
