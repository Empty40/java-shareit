package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EmailException;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users;

    private final Map<Long, String> email;
    private static long userId;

    public UserRepositoryImpl() {
        users = new HashMap<>();
        email = new HashMap<>();
    }

    private long generateId() {
        return ++userId;
    }

    @Override
    public User create(User user) {
        if (email.containsValue(user.getEmail())) {
            throw new EmailException("EmailException (Пользователь с таким email уже существует!)");
        }

        user.setId(generateId());
        email.put(user.getId(), user.getEmail());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(Long id, User user) {
        checkUser(id);

        if (email.containsValue(user.getEmail()) && email.containsKey(id) && !email.get(id).equals(user.getEmail())) {
            throw new EmailException("EmailException (Пользователь с таким email уже существует!)");
        }

        if (user.getName() != null) {
            users.get(id).setName(user.getName());
        }
        if (user.getEmail() != null) {
            users.get(id).setEmail(user.getEmail());
            email.put(id, user.getEmail());
        }
        return users.get(id);
    }

    @Override
    public User getUserById(Long id) {
        checkUser(id);
        return users.get(id);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
        email.remove(id);
    }

    private void checkUser(Long userId) {
        if (!users.containsKey(userId)) {
            throw new EntityNotFoundException("Такого пользователя не существует!");
        }
    }
}

