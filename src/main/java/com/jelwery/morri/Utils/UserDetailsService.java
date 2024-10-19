//package com.jelwery.morri.Utils;
//
//import com.jelwery.morri.Model.User;
//import com.jelwery.morri.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        @Autowired
//        private UserRepository userRepository;
//
//        @Override
//        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//            Optional<User> user = userRepository.findByEmail(email);
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found with email: " + email);
//            }
//            return new org.springframework.security.core.userdetails.User(user(), user.getPassword(), user.getAuthorities());
//        }
//    }
//}
