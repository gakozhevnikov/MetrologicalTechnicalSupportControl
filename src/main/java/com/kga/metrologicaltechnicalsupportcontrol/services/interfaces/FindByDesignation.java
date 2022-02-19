package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

public interface FindByDesignation<T,ID> extends BaseModelService<T, ID>{
    T findByDesignation(String designation);
}
