package master.mira.acsi.TaskManager.repository;

import master.mira.acsi.TaskManager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Trouver les projets par owner ID

    List<Project> findByOwnerId(Long ownerId);
}