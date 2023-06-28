package az.tarlan.taskms.service.impl;

import az.tarlan.taskms.dto.request.LoginRequest;
import az.tarlan.taskms.dto.request.RegisterRequest;
import az.tarlan.taskms.dto.response.AuthenticationResponse;
import az.tarlan.taskms.enums.Role;
import az.tarlan.taskms.enums.TokenType;
import az.tarlan.taskms.exception.RecordNotFoundException;
import az.tarlan.taskms.model.User;
import az.tarlan.taskms.repository.TokenRepository;
import az.tarlan.taskms.repository.UserRepository;
import az.tarlan.taskms.service.AuthenticationService;
import az.tarlan.taskms.service.JwtService;
import az.tarlan.taskms.token.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("Register process is starting");
        User user = User.builder()
                .companyName(request.companyName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .build();
        var savedUser = userRepository.save(user);
        String jwt = jwtService.generateToken(savedUser);
        saveUserToken(user, jwt);
        log.info("User saved, authentication completed successfully!");
        return AuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RecordNotFoundException("User cannot found"));
        String jwt = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);
        log.info("Logged in successfully!");
        return AuthenticationResponse.builder().token(jwt).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
