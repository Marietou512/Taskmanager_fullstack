package master.mira.acsi.TaskManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import master.mira.acsi.TaskManager.model.Task;
import master.mira.acsi.TaskManager.model.TaskStatus;
import master.mira.acsi.TaskManager.model.Priority;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private Long projectId;
    private Long assigneeId;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convertir une entité Task en TaskDTO

    public static TaskDTO fromEntity(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setProjectId(task.getProjectId());
        dto.setAssigneeId(task.getAssigneeId());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        return dto;
    }

    // Convertir un TaskDTO en entité Task

    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setStatus(this.status);
        task.setPriority(this.priority);
        task.setProjectId(this.projectId);
        task.setAssigneeId(this.assigneeId);
        task.setDueDate(this.dueDate);

        return task;
    }
}