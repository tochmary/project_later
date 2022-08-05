package ru.practicum.user;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        String regDate = DateTimeFormatter
                .ofPattern("yyyy.MM.dd hh:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(user.getRegistrationDate());
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                regDate,
                user.getState()
        );
    }

    public static List<UserDto> toUserDto(List<User> userList) {
        return userList.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public static User toNewUser(UserDto userDto) {
        return new User(
                null,
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                null,
                userDto.getState()
                );
    }
}
