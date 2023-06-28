package az.tarlan.taskms.repository;

import az.tarlan.taskms.enums.TaskBoard;
import az.tarlan.taskms.enums.TaskStatus;
import az.tarlan.taskms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBoard(TaskBoard board);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssigneeEqualsIgnoreCase(String assignee);

    List<Task> findByTitleEqualsIgnoreCase(String title);

}
