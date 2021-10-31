package com.kga.metrologicaltechnicalsupportcontrol.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TechObjectDTO {
    private Long id;
    @NotEmpty
    private String title;
}
