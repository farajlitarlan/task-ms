package az.tarlan.taskms.dto.request;

import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import lombok.Builder;

@Builder
public record TaskUpdateRequest(
        String title,
        String desc,
        TaskBoard board,
        TaskStatus status,
        String assignee,
        String dueDate
) {
}
