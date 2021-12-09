package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String citizenship;
    private String passportNumber;
    private String name;
    private String surname;


    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
