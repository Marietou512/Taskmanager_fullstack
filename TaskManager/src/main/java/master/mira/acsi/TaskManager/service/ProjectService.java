package master.mira.acsi.TaskManager.service;

import master.mira.acsi.TaskManager.model.Project;
import master.mira.acsi.TaskManager.model.Task;
import master.mira.acsi.TaskManager.repository.ProjectRepository;
import master.mira.acsi.TaskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Récupérer tous les projets

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Récupérer un projet par ID

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    // Récupérer les projets dont l'utilisateur est owner (pour MANAGER)

    public List<Project> getProjectsByOwnerId(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    // Récupérer les projets où l'utilisateur a des tâches assignées (pour USER)

    public List<Project> getProjectsWithUserTasks(Long userId) {
        // Récupérer tous les projets
        List<Project> allProjects = projectRepository.findAll();

        // Filtrer ceux où l'utilisateur a des tâches
        return allProjects.stream()
                .filter(project -> {
                    List<Task> tasks = taskRepository.findByProjectId(project.getId());
                    return tasks.stream().anyMatch(task -> userId.equals(task.getAssigneeId()));
                })
                .collect(Collectors.toList());
    }

    // Vérifier si un utilisateur a des tâches dans un projet

    public boolean userHasTasksInProject(Long userId, Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().anyMatch(task -> userId.equals(task.getAssigneeId()));
    }

    // Créer un nouveau projet

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Mettre à jour un projet

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project == null) {
            return null;
        }

        // Mettre à jour les champs
        if (projectDetails.getName() != null) {
            project.setName(projectDetails.getName());
        }

        if (projectDetails.getDescription() != null) {
            project.setDescription(projectDetails.getDescription());
        }

        if (projectDetails.getStatus() != null) {
            project.setStatus(projectDetails.getStatus());
        }

        if (projectDetails.getOwnerId() != null) {
            project.setOwnerId(projectDetails.getOwnerId());
        }

        if (projectDetails.getStartDate() != null) {
            project.setStartDate(projectDetails.getStartDate());
        }

        if (projectDetails.getEndDate() != null) {
            project.setEndDate(projectDetails.getEndDate());
        }

        return projectRepository.save(project);
    }

    // Supprimer un projet

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}