package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import java.util.Set;

public interface SecondTypeParameterReturnSet<T, ID, ST> extends BaseModelService<T,ID> {

    Set<T> findByObject(ST object);
}
