package services;

import domain.dtos.UserDto;
import domain.entities.User;

public interface UserService {
    void addUser(UserDto user);
    User getUser(String uid);
}
