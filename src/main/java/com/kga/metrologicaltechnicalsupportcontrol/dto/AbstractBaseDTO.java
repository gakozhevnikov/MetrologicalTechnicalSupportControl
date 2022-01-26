package com.kga.metrologicaltechnicalsupportcontrol.dto;

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public abstract class AbstractBaseDTO implements HasId {

    @NotEmpty
    private Long id;

    public AbstractBaseDTO() {
    }

    public AbstractBaseDTO(Long id) {
        this.id = id;
    }

}
