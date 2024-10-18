package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document(collection="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotNull
    private String username;
    @Indexed(unique = true)
    @NotNull(message = "Salary field is required")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotNull(message = "Password cant be null")
    private String password;
    private String name;
    private LocalDateTime dateOfBirth;
    private GENDER gender;
    private String phoneNumber;
    @Indexed(unique = true)
    private String cccd;
    private String address;
    @CreatedDate
    private LocalDateTime ngayVaoLam;
    private ROLE role;
    private String luongCoBan;
    @DBRef
    private ArrayList<Salary> bangLuong;



}
