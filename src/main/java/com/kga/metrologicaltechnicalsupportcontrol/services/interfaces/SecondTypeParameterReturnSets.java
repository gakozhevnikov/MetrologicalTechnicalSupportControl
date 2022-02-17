package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import java.util.Set;

public interface SecondTypeParameterReturnSets<T, ID, ST> extends BaseModelService<T,ID> {
    Set<T> findByTitle (ST object);
    Set<T> findByObject(ST object);
}
