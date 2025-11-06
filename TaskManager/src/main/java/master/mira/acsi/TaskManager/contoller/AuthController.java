
package master.mira.acsi.TaskManager.contoller;

import master.mira.acsi.TaskManager.dto.LoginRequest;
import master.mira.acsi.TaskManager.dto.LoginResponse;
import master.mira.acsi.TaskManager.model.User;
import master.mira.acsi.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Endpoint de connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Tentative de connexion: " + loginRequest.getEmail());

            // Verifier que l'email et le mot de passe sont fournis
            if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Email requis"));
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Mot de passe requis"));
            }

            // Rechercher l'utilisateur par email
            User user = userService.getUserByEmail(loginRequest.getEmail());

            if (user == null) {
                System.out.println(" Utilisateur non trouve: " + loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Utilisateur non trouve"));
            }

            // Verifier le mot de passe
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                System.out.println(" Mot de passe incorrect pour: " + loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Email ou mot de passe incorrect"));
            }

            // Connexion reussie
            System.out.println("✅ Connexion reussie: " + user.getEmail() + " (" + user.getRole() + ")");

            // Creer la reponse (ne pas inclure le mot de passe !)
            LoginResponse response = new LoginResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole().toString()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la connexion: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur serveur"));
        }
    }

    /**
     * Creer un message d'erreur formate
     */
    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}