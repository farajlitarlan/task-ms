package az.tarlan.taskms.services;

import az.tarlan.taskms.dto.request.TaskCreationRequest;
import az.tarlan.taskms.dto.request.TaskUpdateRequest;
import az.tarlan.taskms.dto.response.TaskResponse;
import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import az.tarlan.taskms.exception.RecordNotFoundException;
import az.tarlan.taskms.model.Task;
import az.tarlan.taskms.repository.TaskRepository;
import az.tarlan.taskms.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl underTest;

    private TaskCreationRequest testCreation() {
        return TaskCreationRequest.builder()
                .status(TaskStatus.TESTING)
                .assignee("")
                .title("")
                .desc("")
                .board(TaskBoard.QA)
                .build();
    }

    private Task testEntity() {
        return Task.builder().id(1L)
                .board(TaskBoard.DEV)
                .title("Task ms")
                .status(TaskStatus.TESTING)
                .assignee("Tarlan Farajli")
                .build();
    }

    private TaskUpdateRequest testUpdate() {
        return TaskUpdateRequest.builder().board(TaskBoard.QA).build();
    }

    @Test
    void taskCreationSuccessTest() {
        underTest.createTask(testCreation());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void taskUpdateSuccessTest() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(testEntity()));
        underTest.updateTask(1L, testUpdate());
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void taskUpdate_NotFoundTest() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(RecordNotFoundException.class, () -> underTest.updateTask(2L, testUpdate()));
    }

    @Test
    void taskDeleteSuccessTest() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(testEntity()));
        underTest.deleteTask(1L);
        verify(taskRepository, times(1)).delete(any());
    }

    @Test
    void taskSearchOnStatus() {
        when(taskRepository.findByStatus(any())).thenReturn(List.of(testEntity()));
        List<TaskResponse> responses = underTest.searchOnStatus("testing");
        Assertions.assertEquals(1, responses.size());
        verify(taskRepository, times(1)).findByStatus(any());
    }

    @Test
    void taskSearchOnBoard() {
        when(taskRepository.findByBoard(any())).thenReturn(List.of(testEntity()));
        List<TaskResponse> responses = underTest.searchOnBoard("dev");
        Assertions.assertEquals(1, responses.size());
        verify(taskRepository, times(1)).findByBoard(any());
    }

    @Test
    void taskSearchOnAssignee() {
        when(taskRepository.findByAssigneeEqualsIgnoreCase(any())).thenReturn(List.of(testEntity()));
        List<TaskResponse> responses = underTest.searchOnAssignee("tarlan farajli");
        Assertions.assertEquals(1, responses.size());
        verify(taskRepository, times(1)).findByAssigneeEqualsIgnoreCase(any());
    }

    @Test
    void taskSearchOnTitle() {
        when(taskRepository.findByTitleEqualsIgnoreCase(any())).thenReturn(List.of(testEntity()));
        List<TaskResponse> responses = underTest.searchOnTitle("task ms");
        Assertions.assertEquals(1, responses.size());
        verify(taskRepository, times(1)).findByTitleEqualsIgnoreCase(any());
    }
}
