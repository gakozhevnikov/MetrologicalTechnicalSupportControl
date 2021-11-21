package com.kga.metrologicaltechnicalsupportcontrol.dto;

import com.kga.metrologicaltechnicalsupportcontrol.BaseName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public abstract class AbstractNameBaseDTO extends AbstractBaseDTO implements BaseName {

    @NotEmpty
    private String name;

    public AbstractNameBaseDTO(String name) {
        this.name = name;
    }

    public AbstractNameBaseDTO(Long id, String name) {
        super(id);
        this.name = name;
    }

}
