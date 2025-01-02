package com.jelwery.morri.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document; 

import java.time.LocalDateTime; 

@Document(collection="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NotNull
    private String username;
    @Indexed(unique = true)
    @NotNull(message = "email field is required")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotNull(message = "Password cant be null")
    private String password;
    private String name;
    private LocalDateTime dateOfBirth;
    private String faceId;
    private GENDER gender;
    private String phoneNumber;  
    private String cccd;
    private String avaURL;
    private String address;
    @CreatedDate
    private LocalDateTime ngayVaoLam;
    private ROLE role;
    private String luongCoBan;   


}
