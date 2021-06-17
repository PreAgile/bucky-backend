package dev.buckybackend.serviceImpl;

import dev.buckybackend.domain.User;
import dev.buckybackend.repository.UserRepository;
import dev.buckybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // 차후에 유저변경을 어떻게 할건지 논의해야됨.
        saveUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(user.get().getId());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //중복 스튜디오 검증
    private void validateDuplicateUser(User user) {
        Optional<User> validUser = userRepository.findById(user.getId());
        validUser.ifPresent(u -> {
            if(u.getName().equals(user.getName())) {
                throw new IllegalStateException("Duplicate User Name = " + u.getName());
            }
        });

    }
}
