package master.mira.acsi.TaskManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import master.mira.acsi.TaskManager.model.User;
import master.mira.acsi.TaskManager.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    // Convertir une entité User en UserDTO


    public static UserDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        dto.setRole(user.getRole());

        return dto;
    }

    // Convertir un UserDTO en entité User

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRole(this.role);

        return user;
    }
}