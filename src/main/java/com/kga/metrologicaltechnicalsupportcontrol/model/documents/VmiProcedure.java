package com.kga.metrologicaltechnicalsupportcontrol.model.documents;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**Методика поверки, verification of a measuring instrument (VMI) -
 * Поверка (РМГ 29) 10 пункт */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class VmiProcedure extends AbstractBaseDocument{

    /**Наименование методики поверки*/
    @Column
    private String nameDocument;

    @OneToOne(cascade = CascadeType.ALL)
    private TypeApproval typeApproval;

}
