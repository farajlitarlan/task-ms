package az.tarlan.taskms.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(String status, String message, LocalDateTime errorTime) {
}
