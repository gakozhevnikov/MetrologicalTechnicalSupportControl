package com.kga.metrologicaltechnicalsupportcontrol.payload.request;

import com.kga.metrologicaltechnicalsupportcontrol.annotations.PasswordMatches;
import com.kga.metrologicaltechnicalsupportcontrol.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String name;
    @NotEmpty(message = "Please enter your lastname")
    private String lastName;
    @NotEmpty(message = "Please enter your username")
    private String userName;
    @NotEmpty(message = "Please enter your company")
    private String company;
    @NotEmpty(message = "Please enter your job title")
    private String jobTitle;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String confirmPassword;


}
