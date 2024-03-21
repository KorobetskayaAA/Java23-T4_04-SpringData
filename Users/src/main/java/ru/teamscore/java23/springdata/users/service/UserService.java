package ru.teamscore.java23.springdata.users.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.springdata.users.entity.User;
import ru.teamscore.java23.springdata.users.exception.UserNotExistsException;
import ru.teamscore.java23.springdata.users.exception.UsernameExistsException;
import ru.teamscore.java23.springdata.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final int PAGE_SIZE = 10;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String email) {
        if (usernameExists(username)) {
            throw new UsernameExistsException(username);
        }
        return userRepository.save(new User(username, email));
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<User> getUsers(int page, String sorting, boolean desc) {
        return userRepository.findAll(PageRequest.of(
                page, PAGE_SIZE,
                Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sorting))
        ).toList();
    }

    public User edit(User user) {
        if (!usernameExists(user.getUsername())) {
            throw new UserNotExistsException(user.getUsername());
        }
        return userRepository.save(user);
    }

    public void ban(String username) {
        if (!usernameExists(username)) {
            throw new UserNotExistsException(username);
        }
        userRepository.updateBannedByUsername(username);
    }
}
