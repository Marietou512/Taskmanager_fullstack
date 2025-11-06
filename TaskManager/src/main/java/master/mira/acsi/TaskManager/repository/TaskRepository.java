package master.mira.acsi.TaskManager.repository;

import master.mira.acsi.TaskManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Trouver toutes les tâches d'un projet

    List<Task> findByProjectId(Long projectId);

    // Trouver les tâches assignées à un utilisateur

    List<Task> findByAssigneeId(Long assigneeId);
}