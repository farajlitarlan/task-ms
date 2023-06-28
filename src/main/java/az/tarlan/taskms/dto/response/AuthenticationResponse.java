package az.tarlan.taskms.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
