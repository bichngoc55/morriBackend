package com.jelwery.morri.Service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.SalaryRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalaryRepository salaryRepository;
     
    @Autowired
    private AbsenceRepository absenceRepository;
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder()  ;

 

}
