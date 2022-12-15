package domain.mappers;

import domain.dtos.UserDto;
import domain.entities.User;

import java.util.UUID;

public class UserMapper {
    public static User dtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUid(UUID.randomUUID().toString());
        user.setRole(userDto.getRole());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());

        return user;
    }
}
