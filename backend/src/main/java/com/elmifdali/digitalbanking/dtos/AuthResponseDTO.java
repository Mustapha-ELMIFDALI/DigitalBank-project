package com.elmifdali.digitalbanking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType;
    private String username;
    private List<String> roles;
}
