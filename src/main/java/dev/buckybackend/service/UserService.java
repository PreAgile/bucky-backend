package dev.buckybackend.service;

import dev.buckybackend.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public abstract User getUserById(Long id);
    public abstract void saveUser(User user);
    public abstract void updateUser(User user);
    public abstract void deleteUser(Long id);
    public abstract List<User> getAllUsers();
}
