package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.toUserDto(users);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = repository.save(UserMapper.toNewUser(userDto));
        return UserMapper.toUserDto(user);
    }
}
