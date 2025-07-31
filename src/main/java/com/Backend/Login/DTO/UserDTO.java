package com.Backend.Login.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String email;
    private String role;

    public UserDTO(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}

