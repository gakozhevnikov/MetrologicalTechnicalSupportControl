package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;


import java.util.List;
/**ST - the second type of parameter, второй типа параметра для выбора объекта при запросе по передаваемому объекту.
 * */
public interface BaseModelService<T, ID, ST> {

    T save(T entity);

    List<T> saveAllAndFlush(List<T> tList);

    void deleteAll();

    void deleteById(ID id);

    List<T> findAll();

    T getById(Long id);

    T findByTitle (String title);

    T findByObject(ST object);

}
