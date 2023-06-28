package az.tarlan.taskms.dto.request;

import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import lombok.Builder;

@Builder
public record TaskCreationRequest(
        String title,
        String desc,
        TaskStatus status,
        TaskBoard board,
        String assignee
) {
}
