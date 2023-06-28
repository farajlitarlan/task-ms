package az.tarlan.taskms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequest(
        @Email(message = "Email is not valid")
        String email,
        @Size(min = 6, message = "Password size at least 6 character size")
        String password) {
}
