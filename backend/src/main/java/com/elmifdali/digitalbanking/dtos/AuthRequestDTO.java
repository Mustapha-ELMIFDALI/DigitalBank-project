package com.elmifdali.digitalbanking.dtos;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
