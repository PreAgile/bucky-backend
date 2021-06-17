package dev.buckybackend.api.user;

import dev.buckybackend.domain.User;
import dev.buckybackend.dto.UserDto;
import dev.buckybackend.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping(value = "/api/v1/users/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        AtomicReference<UserDto> returnDto = new AtomicReference<>(new UserDto());
        Optional<User> userById = Optional.ofNullable(userService.getUserById(id));
        userById.ifPresent(user -> {
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getStudio(),
                    user.getMemo(),
                    user.getCreate_time(),
                    user.getRecent_login_time(),
                    user.getRole(),
                    user.getImages()
            );
            returnDto.set(userDto);
        });
        return returnDto.get();
    }

    @GetMapping(value = "/api/v1/users")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/api/v1/users")
    public void createUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
    }

    @PutMapping(value = "/api/v1/users/{id}")
    public void updateUser(@RequestBody @Valid User user,
                                @PathVariable("id") Long id) {
        Optional<User> findUpdateUser = Optional.ofNullable(userService.getUserById(id));
        findUpdateUser.ifPresent(u -> {
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            u.setStudio(user.getStudio());
            u.setMemo(user.getMemo());
            u.setCreate_time(user.getCreate_time());
            u.setRecent_login_time(user.getRecent_login_time());
            u.setRole(user.getRole());
            u.setImages(user.getImages());
            userService.updateUser(u);
        });
    }

    @DeleteMapping(value = "/api/v1/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}