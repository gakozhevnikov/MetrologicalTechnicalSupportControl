package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;


/**ST - the second type of parameter, второй типа параметра для выбора объекта при запросе по передаваемому объекту.
 * */
public interface SecondTypeParameter<T, ID, ST> extends BaseModelService<T,ID>{
    T findByObject(ST object);
}
