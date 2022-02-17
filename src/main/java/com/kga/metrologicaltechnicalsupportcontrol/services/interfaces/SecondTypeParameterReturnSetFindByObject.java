package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import java.util.Set;

public interface SecondTypeParameterReturnSetFindByObject<T, ID, ST> extends BaseModelService<T,ID> {
    T findByTitle (String title);
    Set<T> findByObject(ST object);
}
