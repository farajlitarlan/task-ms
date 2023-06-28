package az.tarlan.taskms.controller;

import az.tarlan.taskms.dto.request.TaskCreationRequest;
import az.tarlan.taskms.dto.request.TaskUpdateRequest;
import az.tarlan.taskms.dto.response.TaskResponse;
import az.tarlan.taskms.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        log.info("Find all tasks request accepted");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/search/assignee")
    public ResponseEntity<List<TaskResponse>> getTaskByAssignee(@RequestParam String assignee) {
        log.info("Task search with assignee accepted");
        return ResponseEntity.ok(taskService.searchOnAssignee(assignee));
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<TaskResponse>> getTaskByStatus(@RequestParam String status) {
        log.info("Task search with status accepted");
        return ResponseEntity.ok(taskService.searchOnStatus(status));
    }

    @GetMapping("/search/board")
    public ResponseEntity<List<TaskResponse>> getTaskByBoard(@RequestParam String board) {
        log.info("Task search with board accepted");
        return ResponseEntity.ok(taskService.searchOnBoard(board));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<TaskResponse>> getTaskByTitle(@RequestParam String title) {
        log.info("Task search with title accepted");
        return ResponseEntity.ok(taskService.searchOnTitle(title));
    }

    @PostMapping("/new")
    public ResponseEntity<TaskResponse> createNewTask(@RequestBody TaskCreationRequest request) {
        log.info("Task creation request accepted");
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id, @RequestBody TaskUpdateRequest request
    ) {
        log.info("Task update request accepted");
        return ResponseEntity.accepted().body(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        log.info("Task deletion request accepted");
        taskService.deleteTask(id);
    }
}
