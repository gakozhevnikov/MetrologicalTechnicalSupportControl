package com.kga.metrologicaltechnicalsupportcontrol.dto;

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractBaseDTO implements HasId {

    private Long id;

    public AbstractBaseDTO() {
    }

    public AbstractBaseDTO(Long id) {
        this.id = id;
    }

}
