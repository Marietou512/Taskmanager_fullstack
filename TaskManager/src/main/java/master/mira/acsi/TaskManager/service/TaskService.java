package master.mira.acsi.TaskManager.service;

import master.mira.acsi.TaskManager.model.Task;
import master.mira.acsi.TaskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Récupérer toutes les tâches

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Récupérer une tâche par ID

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Récupérer les tâches d'un projet

    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    // Récupérer les tâches assignées à un utilisateur

    public List<Task> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    // Créer une nouvelle tâche

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Mettre à jour une tâche

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return null;
        }

        // Mettre à jour les champs
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }

        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }

        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }

        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }

        if (taskDetails.getProjectId() != null) {
            task.setProjectId(taskDetails.getProjectId());
        }

        if (taskDetails.getAssigneeId() != null) {
            task.setAssigneeId(taskDetails.getAssigneeId());
        }

        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }

        return taskRepository.save(task);
    }

    // Supprimer une tâche

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}