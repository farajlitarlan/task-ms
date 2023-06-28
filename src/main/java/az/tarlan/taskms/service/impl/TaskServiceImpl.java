package az.tarlan.taskms.service.impl;

import az.tarlan.taskms.dto.request.TaskCreationRequest;
import az.tarlan.taskms.dto.request.TaskUpdateRequest;
import az.tarlan.taskms.dto.response.TaskResponse;
import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import az.tarlan.taskms.exception.RecordNotFoundException;
import az.tarlan.taskms.mapper.TaskMapper;
import az.tarlan.taskms.model.Task;
import az.tarlan.taskms.repository.TaskRepository;
import az.tarlan.taskms.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskMapper.INSTANCE::taskEntityToDto).toList();
    }

    @Override
    public TaskResponse createTask(TaskCreationRequest creationRequest) {
        log.info("Task creation started");
        LocalDate taskDueDate = LocalDate.now().plusDays(14L);
        Task task = Task.builder()
                .title(creationRequest.title())
                .description(creationRequest.desc())
                .assignee(creationRequest.assignee())
                .dueDate(taskDueDate)
                .board(creationRequest.board())
                .status(creationRequest.status())
                .build();
        taskRepository.save(task);
        log.info("Task saved, success response created!");
        return TaskMapper.INSTANCE.taskEntityToDto(task);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskUpdateRequest updateRequest) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Task not found"));
        log.info("Task update in progress");
        Task updated = updateLogic(updateRequest, existing);
        Task saved = taskRepository.save(updated);
        log.info("Task update finished");
        return TaskMapper.INSTANCE.taskEntityToDto(saved);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Task not found"));
        log.warn("Task founded and deleted");
        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponse> searchOnAssignee(String assignee) {
        List<Task> tasks = taskRepository.findByAssigneeEqualsIgnoreCase(assignee);
        if (!tasks.isEmpty()) {
            log.info("By assignee {}", tasks);
            return tasks.stream().map(TaskMapper.INSTANCE::taskEntityToDto).toList();
        }
        throw new RecordNotFoundException("Tasks not found on this assignee");
    }

    @Override
    public List<TaskResponse> searchOnStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        List<Task> tasks = taskRepository.findByStatus(taskStatus);
        if (!tasks.isEmpty()) {
            log.info("By status {}", tasks);
            return tasks.stream().map(TaskMapper.INSTANCE::taskEntityToDto).toList();
        }
        throw new RecordNotFoundException("Tasks not found in this status");
    }

    @Override
    public List<TaskResponse> searchOnBoard(String board) {
        TaskBoard taskBoard = TaskBoard.valueOf(board.toUpperCase());
        List<Task> tasks = taskRepository.findByBoard(taskBoard);
        if (!tasks.isEmpty()) {
            log.info("By board {}", tasks);
            return tasks.stream().map(TaskMapper.INSTANCE::taskEntityToDto).toList();
        }
        throw new RecordNotFoundException("Tasks not found in this board");
    }

    @Override
    public List<TaskResponse> searchOnTitle(String title) {
        List<Task> tasks = taskRepository.findByTitleEqualsIgnoreCase(title);
        if (!tasks.isEmpty()) {
            log.info("By title {}", tasks);
            return tasks.stream().map(TaskMapper.INSTANCE::taskEntityToDto).toList();
        }
        throw new RecordNotFoundException("Tasks not found on this title");
    }


    private Task updateLogic(TaskUpdateRequest updateRequest, Task existing) {
        if (updateRequest.title() != null) {
            existing.setTitle(updateRequest.title());
        }
        if (updateRequest.desc() != null) {
            existing.setDescription(updateRequest.desc());
        }
        if (updateRequest.status() != null) {
            existing.setStatus(updateRequest.status());
        }
        if (updateRequest.board() != null) {
            existing.setBoard(updateRequest.board());
        }
        if (updateRequest.assignee() != null) {
            existing.setAssignee(updateRequest.assignee());
        }
        if (updateRequest.dueDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate dueDate = LocalDate.parse(updateRequest.dueDate(), formatter);
            existing.setDueDate(dueDate);
        }
        return existing;
    }
}
