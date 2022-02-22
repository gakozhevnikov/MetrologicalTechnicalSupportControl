package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;


import java.util.List;

public interface BaseModelService<T, ID> {

    T save(T entity);

    List<T> saveAllAndFlush(List<T> tList);

    void deleteAll();

    void deleteById(ID id);

    List<T> findAll();

    T findById(Long id);

}
