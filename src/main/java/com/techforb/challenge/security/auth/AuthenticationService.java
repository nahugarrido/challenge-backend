package com.techforb.challenge.security.auth;

import com.techforb.challenge.security.config.JwtService;
import com.techforb.challenge.enums.Role;
import com.techforb.challenge.entity.User;
import com.techforb.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .userID(request.getUserID())
                .email(request.getEmail())
                .picture("https://media.licdn.com/dms/image/D4D03AQFFcabjKm-eUw/profile-displayphoto-shrink_800_800/0/1690233724841?e=1695859200&v=beta&t=XTP4kFWWTNUM8mUHgWzmj9O_I0a-2gr5VOmDoXKOjeM")
                .balance(BigDecimal.valueOf(0))
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserID(), request.getPassword())
        );
        var user = repository.findByUserID(request.getUserID()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
