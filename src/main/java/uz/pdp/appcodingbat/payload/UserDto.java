package uz.pdp.appcodingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotNull
    private String email;

    @Size(min = 8,message = "the password must be at least 8 characters long")
    @NotNull
    private String password;
}
