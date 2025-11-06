package master.mira.acsi.TaskManager;

import master.mira.acsi.TaskManager.model.*;
import master.mira.acsi.TaskManager.repository.UserRepository;
import master.mira.acsi.TaskManager.repository.ProjectRepository;
import master.mira.acsi.TaskManager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	/**
	 * Initialiser les utilisateurs, projets et tâches au démarrage
	 */
	@Bean
	public CommandLineRunner initDatabase(UserRepository userRepository,
										  ProjectRepository projectRepository,
										  TaskRepository taskRepository,
										  PasswordEncoder passwordEncoder) {
		return args -> {


			// Vérifier si des utilisateurs existent déjà
			if (userRepository.count() > 0) {
				System.out.println("✅ Utilisateurs déjà initialisés");
			} else {
				System.out.println("\n Initialisation des utilisateurs...");

				// Créer l'admin principal
				User admin = new User();
				admin.setFirstName("Marietou");
				admin.setLastName("CISSE");
				admin.setEmail("marietou@taskmanager.com");
				admin.setPassword(passwordEncoder.encode("mira512"));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);

				// Créer Sounna (USER)
				User sounna = new User();
				sounna.setFirstName("Sounna");
				sounna.setLastName("Maiga");
				sounna.setEmail("sounna@gmail.com");
				sounna.setPassword(passwordEncoder.encode("sounna"));
				sounna.setRole(Role.USER);
				userRepository.save(sounna);

				// Créer Salimata (MANAGER)
				User sali = new User();
				sali.setFirstName("Salimata");
				sali.setLastName("Togo");
				sali.setEmail("salimata@gmail.com");
				sali.setPassword(passwordEncoder.encode("salimata"));
				sali.setRole(Role.MANAGER);
				userRepository.save(sali);

				// Créer warda (MANAGER)
				User warda = new User();
				warda.setFirstName("Warda");
				warda.setLastName("Soulaimana");
				warda.setEmail("warda@gmail.com");
				warda.setPassword(passwordEncoder.encode("soulaimana"));
				warda.setRole(Role.MANAGER);
				userRepository.save(warda);

				User bob = new User();
				bob.setFirstName("Bob");
				bob.setLastName("Dupont");
				bob.setEmail("bob.dupont@company.com");
				bob.setPassword(passwordEncoder.encode("bob123"));
				bob.setRole(Role.USER);
				userRepository.save(bob);

				User charlie = new User();
				charlie.setFirstName("Charlie");
				charlie.setLastName("Bernard");
				charlie.setEmail("charlie.bernard@company.com");
				charlie.setPassword(passwordEncoder.encode("charlie123"));
				charlie.setRole(Role.USER);
				userRepository.save(charlie);

				User diana = new User();
				diana.setFirstName("Diana");
				diana.setLastName("Rousseau");
				diana.setEmail("diana.rousseau@company.com");
				diana.setPassword(passwordEncoder.encode("diana123"));
				diana.setRole(Role.USER);
				userRepository.save(diana);

			}

			// Récupérer les utilisateurs pour les assigner aux tâches
			User admin = userRepository.findByEmail("marietou@taskmanager.com").orElse(null);
			User sounna= userRepository.findByEmail("sounna@gmail.com").orElse(null);
			User sali = userRepository.findByEmail("salimata@gmail.com").orElse(null);
			User warda = userRepository.findByEmail("warda@gmail.com").orElse(null);
			User bob = userRepository.findByEmail("bob.dupont@company.com").orElse(null);
			User charlie = userRepository.findByEmail("charlie.bernard@company.com").orElse(null);
			User diana = userRepository.findByEmail("diana.rousseau@company.com").orElse(null);


			if (projectRepository.count() > 0) {
				System.out.println("✅ Projets déjà initialisés");
			} else {
				System.out.println("\n📁 Création des projets...");

				Project project1 = new Project();
				project1.setName("Refonte Site Web");
				project1.setDescription("Refonte complète du site web de l'entreprise avec Angular et Spring Boot");
				project1.setStartDate(LocalDate.of(2025, 1, 15));
				project1.setEndDate(LocalDate.of(2025, 6, 30));
				project1.setStatus(ProjectStatus.ACTIVE);
				project1.setOwnerId(admin != null ? admin.getId() : 1L);
				projectRepository.save(project1);

				Project project2 = new Project();
				project2.setName("Application Mobile");
				project2.setDescription("Développement d'une application mobile iOS/Android pour la gestion des tâches");
				project2.setStartDate(LocalDate.of(2025, 2, 1));
				project2.setEndDate(LocalDate.of(2025, 8, 31));
				project2.setStatus(ProjectStatus.ACTIVE);
				project2.setOwnerId(sali != null ? sali.getId() : 2L);
				projectRepository.save(project2);

				Project project3 = new Project();
				project3.setName("API REST v2");
				project3.setDescription("Migration de l'API REST vers la version 2 avec nouvelles fonctionnalités");
				project3.setStartDate(LocalDate.of(2025, 3, 1));
				project3.setEndDate(LocalDate.of(2025, 5, 15));
				project3.setStatus(ProjectStatus.PLANNING);
				project3.setOwnerId(warda != null ? warda.getId() : 1L);
				projectRepository.save(project3);

				Project project4 = new Project();
				project4.setName("Migration Cloud");
				project4.setDescription("Migration de l'infrastructure vers AWS avec Docker et Kubernetes");
				project4.setStartDate(LocalDate.of(2024, 11, 1));
				project4.setEndDate(LocalDate.of(2025, 1, 31));
				project4.setStatus(ProjectStatus.COMPLETED);
				project4.setOwnerId(admin != null ? admin.getId() : 1L);
				projectRepository.save(project4);

				Project project5 = new Project();
				project5.setName("Dashboard Analytics");
				project5.setDescription("Création d'un dashboard de visualisation de données avec charts et rapports");
				project5.setStartDate(LocalDate.of(2025, 4, 1));
				project5.setEndDate(LocalDate.of(2025, 7, 30));
				project5.setStatus(ProjectStatus.PLANNING);
				project5.setOwnerId(sali != null ? sali.getId() : 2L);
				projectRepository.save(project5);


			// Tâches pour Projet 1: Refonte Site Web
				Task task1_1 = new Task();
				task1_1.setTitle("Conception des maquettes UI/UX");
				task1_1.setDescription("Créer les maquettes Figma pour toutes les pages principales");
				task1_1.setStatus(TaskStatus.DONE);
				task1_1.setPriority(Priority.HIGH);
				task1_1.setDueDate(LocalDate.of(2025, 2, 15));
				task1_1.setProjectId(project1.getId());
				task1_1.setAssigneeId(warda != null ? warda.getId() : admin.getId());

				Task task1_2 = new Task();
				task1_2.setTitle("Développement du Frontend Angular");
				task1_2.setDescription("Implémenter les composants Angular avec le nouveau design");
				task1_2.setStatus(TaskStatus.IN_PROGRESS);
				task1_2.setPriority(Priority.HIGH);
				task1_2.setDueDate(LocalDate.of(2025, 4, 30));
				task1_2.setProjectId(project1.getId());
				task1_2.setAssigneeId(bob != null ? bob.getId() : sounna.getId());

				Task task1_3 = new Task();
				task1_3.setTitle("Développement Backend Spring Boot");
				task1_3.setDescription("Créer les API REST et la logique métier");
				task1_3.setStatus(TaskStatus.IN_PROGRESS);
				task1_3.setPriority(Priority.HIGH);
				task1_3.setDueDate(LocalDate.of(2025, 4, 30));
				task1_3.setProjectId(project1.getId());
				task1_3.setAssigneeId(charlie != null ? charlie.getId() : sounna.getId());

				Task task1_4 = new Task();
				task1_4.setTitle("Tests et validation");
				task1_4.setDescription("Tests unitaires, tests d'intégration et tests end-to-end");
				task1_4.setStatus(TaskStatus.TODO);
				task1_4.setPriority(Priority.MEDIUM);
				task1_4.setDueDate(LocalDate.of(2025, 6, 15));
				task1_4.setProjectId(project1.getId());
				task1_4.setAssigneeId(diana != null ? diana.getId() : sali.getId());

			// Tâches pour Projet 2: Application Mobile
				Task task2_1 = new Task();
				task2_1.setTitle("Étude de marché");
				task2_1.setDescription("Analyser les applications concurrentes et définir les features");
				task2_1.setStatus(TaskStatus.DONE);
				task2_1.setPriority(Priority.HIGH);
				task2_1.setDueDate(LocalDate.of(2025, 2, 15));
				task2_1.setProjectId(project2.getId());
				task2_1.setAssigneeId(sali.getId());

				Task task2_2 = new Task();
				task2_2.setTitle("Setup environnement React Native");
				task2_2.setDescription("Configurer le projet React Native avec Expo");
				task2_2.setStatus(TaskStatus.IN_PROGRESS);
				task2_2.setPriority(Priority.HIGH);
				task2_2.setDueDate(LocalDate.of(2025, 3, 1));
				task2_2.setProjectId(project2.getId());
				task2_2.setAssigneeId(bob != null ? bob.getId() : sounna.getId());

				Task task2_3 = new Task();
				task2_3.setTitle("Développement écran d'authentification");
				task2_3.setDescription("Créer les écrans de login, signup et mot de passe oublié");
				task2_3.setStatus(TaskStatus.IN_PROGRESS);
				task2_3.setPriority(Priority.HIGH);
				task2_3.setDueDate(LocalDate.of(2025, 3, 31));
				task2_3.setProjectId(project2.getId());
				task2_3.setAssigneeId(charlie != null ? charlie.getId() : sounna.getId());

				Task task2_4 = new Task();
				task2_4.setTitle("Intégration API backend");
				task2_4.setDescription("Connecter l'application mobile aux API REST");
				task2_4.setStatus(TaskStatus.TODO);
				task2_4.setPriority(Priority.MEDIUM);
				task2_4.setDueDate(LocalDate.of(2025, 5, 15));
				task2_4.setProjectId(project2.getId());
				task2_4.setAssigneeId(warda != null ? warda.getId() : admin.getId());

		// Tâches pour Projet 3: API REST v2
				Task task3_1 = new Task();
				task3_1.setTitle("Documentation API v2");
				task3_1.setDescription("Rédiger la documentation Swagger/OpenAPI");
				task3_1.setStatus(TaskStatus.TODO);
				task3_1.setPriority(Priority.MEDIUM);
				task3_1.setDueDate(LocalDate.of(2025, 3, 15));
				task3_1.setProjectId(project3.getId());
				task3_1.setAssigneeId(sali.getId());

				Task task3_2 = new Task();
				task3_2.setTitle("Refactoring controllers");
				task3_2.setDescription("Réorganiser les controllers avec les nouvelles conventions");
				task3_2.setStatus(TaskStatus.TODO);
				task3_2.setPriority(Priority.HIGH);
				task3_2.setDueDate(LocalDate.of(2025, 4, 1));
				task3_2.setProjectId(project3.getId());
				task3_2.setAssigneeId(charlie != null ? charlie.getId() : sounna.getId());

				Task task3_3 = new Task();
				task3_3.setTitle("Ajout système de cache");
				task3_3.setDescription("Implémenter Redis pour le cache des requêtes fréquentes");
				task3_3.setStatus(TaskStatus.TODO);
				task3_3.setPriority(Priority.LOW);
				task3_3.setDueDate(LocalDate.of(2025, 4, 30));
				task3_3.setProjectId(project3.getId());
				task3_3.setAssigneeId(bob != null ? bob.getId() : sounna.getId());

			// Tâches pour Projet 4: Migration Cloud (Complété)
			Task task4_1 = new Task();
				task4_1.setTitle("Setup AWS Infrastructure");
				task4_1.setDescription("Configurer VPC, EC2, RDS et S3");
				task4_1.setStatus(TaskStatus.DONE);
				task4_1.setPriority(Priority.HIGH);
				task4_1.setDueDate(LocalDate.of(2024, 11, 30));
				task4_1.setProjectId(project4.getId());
				task4_1.setAssigneeId(admin.getId());

				Task task4_2 = new Task();
				task4_2.setTitle("Containerisation Docker");
				task4_2.setDescription("Créer les Dockerfiles pour tous les services");
				task4_2.setStatus(TaskStatus.DONE);
				task4_2.setPriority(Priority.HIGH);
				task4_2.setDueDate(LocalDate.of(2024, 12, 15));
				task4_2.setProjectId(project4.getId());
				task4_2.setAssigneeId(sounna.getId());

				Task task4_3 = new Task();
				task4_3.setTitle("Déploiement Kubernetes");
				task4_3.setDescription("Déployer sur EKS avec configurations Helm");
				task4_3.setStatus(TaskStatus.DONE);
				task4_3.setPriority(Priority.HIGH);
				task4_3.setDueDate(LocalDate.of(2025, 1, 15));
				task4_3.setProjectId(project4.getId());
				task4_3.setAssigneeId(charlie != null ? charlie.getId() : sounna.getId());

				Task task4_4 = new Task();
				task4_4.setTitle("Tests de charge");
				task4_4.setDescription("Valider les performances avec JMeter");
				task4_4.setStatus(TaskStatus.DONE);
				task4_4.setPriority(Priority.MEDIUM);
				task4_4.setDueDate(LocalDate.of(2025, 1, 25));
				task4_4.setProjectId(project4.getId());
				task4_4.setAssigneeId(diana != null ? diana.getId() : sali.getId());

			// Tâches pour Projet 5: Dashboard Analytics

				Task task5_1 = new Task();
				task5_1.setTitle("Choix des librairies de charts");
				task5_1.setDescription("Évaluer Chart.js, D3.js et Recharts");
				task5_1.setStatus(TaskStatus.TODO);
				task5_1.setPriority(Priority.MEDIUM);
				task5_1.setDueDate(LocalDate.of(2025, 4, 10));
				task5_1.setProjectId(project5.getId());
				task5_1.setAssigneeId(sali.getId());

				Task task5_2 = new Task();
				task5_2.setTitle("Design des visualisations");
				task5_2.setDescription("Créer les maquettes des différents types de graphiques");
				task5_2.setStatus(TaskStatus.TODO);
				task5_2.setPriority(Priority.HIGH);
				task5_2.setDueDate(LocalDate.of(2025, 4, 30));
				task5_2.setProjectId(project5.getId());
				task5_2.setAssigneeId(warda != null ? warda.getId() : admin.getId());

				Task task5_3 = new Task();
				task5_3.setTitle("Développement composants charts");
				task5_3.setDescription("Créer les composants réutilisables pour les graphiques");
				task5_3.setStatus(TaskStatus.TODO);
				task5_3.setPriority(Priority.HIGH);
				task5_3.setDueDate(LocalDate.of(2025, 6, 15));
				task5_3.setProjectId(project5.getId());
				task5_3.setAssigneeId(bob != null ? bob.getId() : sounna.getId());

				Task task5_4 = new Task();
				task5_4.setTitle("Système d'export PDF");
				task5_4.setDescription("Permettre l'export des rapports en PDF");
				task5_4.setStatus(TaskStatus.TODO);
				task5_4.setPriority(Priority.LOW);
				task5_4.setDueDate(LocalDate.of(2025, 7, 20));
				task5_4.setProjectId(project5.getId());
				task5_4.setAssigneeId(charlie != null ? charlie.getId() : sounna.getId());

				// Sauvegarder toutes les tâches
				taskRepository.saveAll(Arrays.asList(
						task1_1, task1_2, task1_3, task1_4,
						task2_1, task2_2, task2_3, task2_4,
						task3_1, task3_2, task3_3,
						task4_1, task4_2, task4_3, task4_4,
						task5_1, task5_2, task5_3, task5_4
				));


			}

			};
	}
}