package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.SalaryRepository;
import com.jelwery.morri.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
