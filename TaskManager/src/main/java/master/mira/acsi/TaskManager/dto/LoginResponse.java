package master.mira.acsi.TaskManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

}