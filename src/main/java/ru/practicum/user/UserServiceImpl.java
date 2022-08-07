package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.toUserDto(users);
    }

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = repository.save(UserMapper.toNewUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserShortWithIP> getUsersEmailWithIp(String email) {
        return repository.findAllByEmailContainingIgnoreCaseWithIP(email);
    }
}
