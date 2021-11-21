package com.kga.metrologicaltechnicalsupportcontrol.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
public class EquipmentDTO extends AbstractBaseDTO{

    @NotEmpty
    private String title;

}
