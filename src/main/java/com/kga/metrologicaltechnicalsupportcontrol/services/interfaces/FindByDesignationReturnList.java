package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import java.util.List;

public interface FindByDesignationReturnList<T,ID> extends BaseModelService<T, ID>{
    List<T> findByDesignationReturnList(String designation);
}
