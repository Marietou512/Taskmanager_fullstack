package master.mira.acsi.TaskManager.service;

import master.mira.acsi.TaskManager.model.User;
import master.mira.acsi.TaskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les utilisateurs

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par ID

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Récupérer un utilisateur par email

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Créer un nouvel utilisateur

    public User createUser(User user) {
        // Hasher le mot de passe avant de sauvegarder
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    // Mettre à jour un utilisateur

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        // Mettre à jour les champs
        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }

        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            // Hasher le nouveau mot de passe
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }

        return userRepository.save(user);
    }

    // Supprimer un utilisateur

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}