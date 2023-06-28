package az.tarlan.taskms.service;

import az.tarlan.taskms.dto.request.TaskCreationRequest;
import az.tarlan.taskms.dto.request.TaskUpdateRequest;
import az.tarlan.taskms.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> getAllTasks();

    TaskResponse createTask(TaskCreationRequest creationRequest);

    TaskResponse updateTask(Long id, TaskUpdateRequest updateRequest);

    void deleteTask(Long id);

    List<TaskResponse> searchOnAssignee (String assignee);
    List<TaskResponse> searchOnStatus (String status);
    List<TaskResponse> searchOnBoard (String board);
    List<TaskResponse> searchOnTitle (String title);
}
