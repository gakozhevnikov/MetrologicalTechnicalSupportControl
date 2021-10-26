package com.kga.metrologicaltechnicalsupportcontrol;

import org.springframework.util.Assert;

/**Для определения наличия Id*/
public interface HasId {
    Long getId();

    void setId (Long id);

    default boolean isNew(){return getId() == null;}

    // doesn't work for hibernate lazy proxy
    default Long id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
