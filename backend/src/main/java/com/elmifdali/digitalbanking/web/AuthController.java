package com.elmifdali.digitalbanking.web;

import com.elmifdali.digitalbanking.dtos.AuthRequestDTO;
import com.elmifdali.digitalbanking.dtos.AuthResponseDTO;
import com.elmifdali.digitalbanking.entities.AppRole;
import com.elmifdali.digitalbanking.entities.AppUser;
import com.elmifdali.digitalbanking.security.JwtUtil;
import com.elmifdali.digitalbanking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(
                authentication.getName(),
                authentication.getAuthorities()
        );

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer", authentication.getName(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/users/addRole")
    public ResponseEntity<AppRole> addRole(@RequestBody AppRole role) {
        return ResponseEntity.ok(userService.saveRole(role));
    }

    @PostMapping("/users/{username}/roles/{roleName}")
    public ResponseEntity<Void> addRoleToUser(@PathVariable String username,
                                               @PathVariable String roleName) {
        userService.addRoleToUser(username, roleName);
        return ResponseEntity.ok().build();
    }
}
