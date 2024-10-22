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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    private List<Absence> absences;
    @Indexed(unique = true)
    private String cccd;
    private String address;
    @CreatedDate
    private LocalDateTime ngayVaoLam;
    private ROLE role;
    private String luongCoBan;
    @DBRef
    private ArrayList<Salary> bangLuong;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // Assuming role is of type ROLE
    }

public class Absence {
     @CreatedDate
    private LocalDate date;
    private String reason;
    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }
 
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

}
