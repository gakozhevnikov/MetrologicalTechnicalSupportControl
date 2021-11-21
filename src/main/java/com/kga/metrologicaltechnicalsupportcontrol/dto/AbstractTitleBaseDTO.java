package com.kga.metrologicaltechnicalsupportcontrol.dto;

import com.kga.metrologicaltechnicalsupportcontrol.Title;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public abstract class AbstractTitleBaseDTO extends AbstractBaseDTO implements Title {

    @NotEmpty
    private String title;

    public AbstractTitleBaseDTO(String title) {
        this.title = title;
    }

    public AbstractTitleBaseDTO(Long id, String title) {
        super(id);
        this.title = title;
    }

}
