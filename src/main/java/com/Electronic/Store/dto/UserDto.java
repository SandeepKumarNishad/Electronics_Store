package com.Electronic.Store.dto;

import com.Electronic.Store.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3,max = 20,message = "Invalid name")
    private String name;

//    @Email(message = "email is Invalid!!")
//    @NotBlank(message = "Required email")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$",message = "Invalid email Id ")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 3,max=20)
    private String password;
    @Size(min = 4,max = 6,message = "Invalid gender")
    private String gender;

    @NotBlank(message = "write something about yourself")
    private String about;

    @ImageNameValid
    private String imageName;
}
