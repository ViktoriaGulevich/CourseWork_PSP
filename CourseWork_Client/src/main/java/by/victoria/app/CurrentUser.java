package by.victoria.app;

import by.victoria.model.dto.UserDto;

public class CurrentUser {
    private static CurrentUser currentUser;
    private UserDto userDto;

    private CurrentUser() {
    }

    public static UserDto getCurrentUserDto() {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        return currentUser.userDto;
    }

    public static void setCurrentUserDto(UserDto userDto) {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        currentUser.userDto = userDto;
    }

    public static void setCurrentUserDtoNull() {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        currentUser.userDto = null;
    }
}
