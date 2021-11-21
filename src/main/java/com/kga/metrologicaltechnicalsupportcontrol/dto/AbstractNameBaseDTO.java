package com.kga.metrologicaltechnicalsupportcontrol.dto;

import com.kga.metrologicaltechnicalsupportcontrol.BaseName;
import com.kga.metrologicaltechnicalsupportcontrol.Title;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractNameBaseDTO extends AbstractBaseDTO implements BaseName {

    private String title;

    public AbstractNameBaseDTO(String title) {
        this.title = title;
    }

    public AbstractNameBaseDTO(Long id, String title) {
        super(id);
        this.title = title;
    }

}
