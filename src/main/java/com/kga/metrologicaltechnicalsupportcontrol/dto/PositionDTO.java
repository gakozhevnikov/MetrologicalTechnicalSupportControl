package com.kga.metrologicaltechnicalsupportcontrol.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PositionDTO extends AbstractTitleBaseDTO{

    public PositionDTO(Long id) {
        super(id);
    }

    public PositionDTO(String title) {
        super(title);
    }

    public PositionDTO(Long id, String title) {
        super(id, title);
    }
}
