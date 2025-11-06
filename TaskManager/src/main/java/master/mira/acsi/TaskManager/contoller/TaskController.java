package master.mira.acsi.TaskManager.contoller;

import master.mira.acsi.TaskManager.dto.TaskDTO;
import master.mira.acsi.TaskManager.model.Task;
import master.mira.acsi.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Récupérer toutes les tâches

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    /**
     * Récupérer les tâches de l'utilisateur connecté (pour le role USER)
     * Utilise le header X-User-Id ou un paramètre
     */
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskDTO>> getMyTasks(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestParam(value = "userId", required = false) Long userIdParam) {

        // Utiliser le header ou le paramètre
        Long actualUserId = userId != null ? userId : userIdParam;

        if (actualUserId == null) {
           // System.err.println("❌ Aucun userId fourni pour /my-tasks");
            return ResponseEntity.badRequest().build();
        }

     //   System.out.println("🔍 Recherche des tâches pour l'utilisateur: " + actualUserId);

        List<Task> tasks = taskService.getTasksByAssigneeId(actualUserId);
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());

       // System.out.println("✅ " + taskDTOs.size() + " tâche(s) trouvée(s)");

        return ResponseEntity.ok(taskDTOs);
    }

    // Récupérer une tâche par ID

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    /// Récupérer les tâches d'un projet

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksByProjectId(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    // Créer une nouvelle tâche

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            // Validation basique
            if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Convertir DTO en entité
            Task task = taskDTO.toEntity();

            // Sauvegarder
            Task savedTask = taskService.createTask(task);

            // Log
           // System.out.println("✅ Tâche créée: " + savedTask.getTitle());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(TaskDTO.fromEntity(savedTask));

        } catch (Exception e) {
           // System.err.println("❌ Erreur création tâche: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Mettre à jour une tâche

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            // Convertir DTO en entité
            Task task = taskDTO.toEntity();
            task.setId(id);

            // Mettre à jour
            Task updatedTask = taskService.updateTask(id, task);

            if (updatedTask == null) {
                return ResponseEntity.notFound().build();
            }

            // Log
            //System.out.println("✅ Tâche mise à jour: " + updatedTask.getTitle());

            return ResponseEntity.ok(TaskDTO.fromEntity(updatedTask));

        } catch (Exception e) {
           // System.err.println("❌ Erreur mise à jour tâche: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Supprimer une tâche

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
           // System.out.println("✅ Tâche supprimée: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
         //   System.err.println("❌ Erreur suppression tâche: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}