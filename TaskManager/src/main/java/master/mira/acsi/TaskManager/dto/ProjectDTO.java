package master.mira.acsi.TaskManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import master.mira.acsi.TaskManager.model.Project;
import master.mira.acsi.TaskManager.model.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private Long ownerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convertir une entité Project en ProjectDTO

    public static ProjectDTO fromEntity(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setOwnerId(project.getOwnerId());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());

        return dto;
    }

    // Convertir un ProjectDTO en entité Project

    public Project toEntity() {
        Project project = new Project();
        project.setId(this.id);
        project.setName(this.name);
        project.setDescription(this.description);
        project.setStatus(this.status);
        project.setOwnerId(this.ownerId);
        project.setStartDate(this.startDate);
        project.setEndDate(this.endDate);

        return project;
    }
}