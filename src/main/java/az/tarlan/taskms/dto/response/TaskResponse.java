package az.tarlan.taskms.dto.response;

import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskResponse(String title,
                           String description,
                           TaskStatus status,
                           String assignee,
                           TaskBoard board,
                           @JsonFormat(pattern = "yyyy-MM-dd")
                           LocalDate dueDate) {
}
