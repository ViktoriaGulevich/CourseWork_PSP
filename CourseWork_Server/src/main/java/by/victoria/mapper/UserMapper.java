package by.victoria.mapper;

import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.User;

public class UserMapper {
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        return userDto;
    }


    public User userDtoToUser(UserDto userDto) {
        User user = new User();

        Integer id = userDto.getId();
        if (id != null) {
            user.setId(id);
        }
        String username = userDto.getUsername();
        if (username != null) {
            user.setUsername(username);
        }
        String password = userDto.getPassword();
        if (password != null) {
            user.setPassword(password);
        }

        return user;
    }
}
