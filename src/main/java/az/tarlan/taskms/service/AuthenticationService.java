package az.tarlan.taskms.service;

import az.tarlan.taskms.dto.request.LoginRequest;
import az.tarlan.taskms.dto.request.RegisterRequest;
import az.tarlan.taskms.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}
