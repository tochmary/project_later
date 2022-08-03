package ru.practicum.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private long id = 1;
    private final Map<Long, User> users = new HashMap<>();
    //private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);


    @Override
    public List<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        long idUser = generateId();
        user.setId(idUser);
        users.put(idUser, user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    private long generateId() {
        return id++;
    }
}
/*
package ru.practicum.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeUserRepository implements UserRepository {
    private static final List<User> FAKE_USERS = createManyFakeUsers(3);

    @Override
    public List<User> findAll() {
        return FAKE_USERS;
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("Метод save() ещё не готов");
    }

    private static List<User> createManyFakeUsers(int count) {
        List<User> fakeUsers = new ArrayList<>();
        for (long id = 1; id <= count; id++) {
            fakeUsers.add(createFakeUser(id));
        }
        return Collections.unmodifiableList(fakeUsers);
    }

    private static User createFakeUser(long id) {
        User fakeUser = new User();
        fakeUser.setId(id);
        fakeUser.setEmail("mail" + id + "@example.com");
        fakeUser.setName("Akakiy Akakievich #" + id);
        return fakeUser;
    }
}
 */
