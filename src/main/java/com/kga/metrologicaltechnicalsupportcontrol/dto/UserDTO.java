package com.kga.metrologicaltechnicalsupportcontrol.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;
    private String userName;

}
