package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import java.util.Set;

public interface FindByDesignationReturnSet<T,ID> extends BaseModelService<T, ID>{
    Set<T> findByDesignationReturnSet(String designation);
}
