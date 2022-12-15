package services.impl;

import domain.dtos.UserDto;
import domain.entities.User;
import domain.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import repositories.UserRepository;
import services.UserService;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(UserDto user) {
        User toSave = UserMapper.dtoToEntity(user);
        UserRepository.getInstance().add(toSave.getUid(), toSave);
    }

    @Override
    public User getUser(String uid) {
        return UserRepository.getInstance().get(uid);
    }
}
