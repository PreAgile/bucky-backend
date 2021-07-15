package dev.buckybackend.api.user;

import dev.buckybackend.domain.SelectList;
import dev.buckybackend.domain.User;
import dev.buckybackend.dto.SelectListDto;
import dev.buckybackend.dto.UserDto;
import dev.buckybackend.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
                    user.getRole(),
                    user.getSelectLists()
            );
            returnDto.set(userDto);
        });
        return returnDto.get();
    }

    @GetMapping(value = "/api/v1/users")
    public List<UserDto> getAllUser() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(s -> {
            UserDto userDto = new UserDto(s.getId(), s.getName(), s.getEmail(), s.getStudio(), s.getMemo(), s.getRole(), s.getSelectLists());
            userDtoList.add(userDto);
        });
        return userDtoList;
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
            u.setSelectLists(user.getSelectLists());
            userService.updateUser(u);
        });
    }

    @DeleteMapping(value = "/api/v1/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    /* image like로 커버 가능하므로 미지원 */
//    @PostMapping(value = "/api/v1/users/selectList")
//    public void createSelectList(@RequestBody @Valid SelectListDto selectListDto) {
//        userService.saveSelectListByImageId(selectListDto.getUser_id(), selectListDto.getImage_id());
//    }

    @GetMapping(value = "/api/v1/users/selectList/{user_id}")
    public List<SelectListDto> getSelectList(@PathVariable("user_id") Long user_id) {
        return userService.getSelectListDtoByUserId(user_id);
    }

    /* image unlike로 커버 가능하므로 미지원 */
//    @DeleteMapping(value = "/api/v1/users/selectList")
//    public void deleteSelectList(@RequestBody @Valid SelectListDto selectListDto) {
//        userService.deleteSelectListByImageId(selectListDto.getUser_id(), selectListDto.getImage_id());
//    }
}
