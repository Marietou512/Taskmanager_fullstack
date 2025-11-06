package master.mira.acsi.TaskManager.contoller;

import master.mira.acsi.TaskManager.dto.ProjectDTO;
import master.mira.acsi.TaskManager.model.Project;
import master.mira.acsi.TaskManager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Récupérer tous les projets

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(ProjectDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectDTOs);
    }

    // Récupérer un projet par ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProjectDTO.fromEntity(project));
    }

    // Création d'un nouveau projet

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            // Validation basique
            if (projectDTO.getName() == null || projectDTO.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Convertir DTO en entité
            Project project = projectDTO.toEntity();

            // Sauvegarder
            Project savedProject = projectService.createProject(project);

            // Log
           // System.out.println("✅ Projet créé: " + savedProject.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ProjectDTO.fromEntity(savedProject));

        } catch (Exception e) {
           // System.err.println("❌ Erreur création projet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Mettre à jour un projet

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        try {
            // Convertir DTO en entité
            Project project = projectDTO.toEntity();
            project.setId(id);

            // Mettre à jour
            Project updatedProject = projectService.updateProject(id, project);

            if (updatedProject == null) {
                return ResponseEntity.notFound().build();
            }

            // Log
          //  System.out.println("✅ Projet mis à jour: " + updatedProject.getName());

            return ResponseEntity.ok(ProjectDTO.fromEntity(updatedProject));

        } catch (Exception e) {
           // System.err.println("❌ Erreur mise à jour projet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprimer un projet
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            //System.out.println("✅ Projet supprimé: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
           // System.err.println("❌ Erreur suppression projet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}