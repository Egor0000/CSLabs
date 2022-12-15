package domain.entities;

import domain.enums.Role;
import lombok.Data;

@Data
public class User {
    String uid;
    String name;
    String surname;
    Role role;
}
