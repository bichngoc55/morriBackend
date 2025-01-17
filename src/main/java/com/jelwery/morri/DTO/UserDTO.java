package com.jelwery.morri.DTO;

import java.time.LocalDateTime;

import com.jelwery.morri.Model.GENDER;
import com.jelwery.morri.Model.ROLE;

import lombok.Data;

@Data
public class UserDTO {
  private String username;
    private String email;
    private String name;
    private LocalDateTime dateOfBirth;
    private GENDER gender;
    private String password; 
    private String phoneNumber;
    private String cccd;
    private String avaURL;
    private String address;
    private ROLE role;
    private String luongCoBan;
}
