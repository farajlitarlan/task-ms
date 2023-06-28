package az.tarlan.taskms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest(
        String companyName,
        @Email(message = "This is not valid email address")
        @NotBlank(message = "Email cannot be blank")
        String email,
        @Size(min = 6, message = "Password must be at least 6 character long")
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
