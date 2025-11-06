package master.mira.acsi.TaskManager.contoller;

import master.mira.acsi.TaskManager.dto.UserDTO;
import master.mira.acsi.TaskManager.model.User;
import master.mira.acsi.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Récupérer tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // Récupérer un utilisateur par ID

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            // Validation basique
            if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Convertir DTO en entité
            User user = userDTO.toEntity();

            // Sauvegarder
            User savedUser = userService.createUser(user);

            // Log
            System.out.println("✅ Utilisateur créé: " + savedUser.getEmail());

            // Retourner le DTO (sans le mot de passe)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserDTO.fromEntity(savedUser));

        } catch (Exception e) {
           // System.err.println("❌ Erreur création utilisateur: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Mettre à jour un utilisateur

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            // Convertir DTO en entité
            User user = userDTO.toEntity();
            user.setId(id);

            // Mettre à jour
            User updatedUser = userService.updateUser(id, user);

            if (updatedUser == null) {
                return ResponseEntity.notFound().build();
            }

            // Log
           // System.out.println("✅ Utilisateur mis à jour: " + updatedUser.getEmail());

            return ResponseEntity.ok(UserDTO.fromEntity(updatedUser));

        } catch (Exception e) {
           // System.err.println("❌ Erreur mise à jour utilisateur: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Supprimer un utilisateur

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
         //   System.out.println("✅ Utilisateur supprimé: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
         //   System.err.println("❌ Erreur suppression utilisateur: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}