package domain.dtos;

import domain.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    String name;
    String surname;
    String email;
    String code;
    String password;
    Role role;
}
