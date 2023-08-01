package com.vikash.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    @NotEmpty
    @Size(min=4, message = "User Name Must be minimum of 4 character!!")
    private String name;
    @Email
    @Pattern(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$")
    private String email;
    @NotEmpty
    @Size(min = 4, max = 10, message = "Password must be minimum of 4 and maximum of 10 character!!")
    private String password;
    @NotEmpty
    @Size(min = 20, max = 50, message = "About must be min 20 and max 50 character!!")
    private String about;
}
