package com.kga.metrologicaltechnicalsupportcontrol.model.documents;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**Документ (сертификат, свидетельство) об утверждении типа*/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class TypeApproval extends AbstractBaseDocument {

    private static final Logger log = LoggerFactory.getLogger(TypeApproval.class);

    /**Номер документа*/
    @Column
    private String indexDocument;

    /**Наименование средства измерения (РМГ 26 п. 6.2 measuring instruments)*/
    @Column
    private String nameMeasuringInstruments;

    /**Обозначение типа СИ(РМГ 26 п. 6.2 measuring instruments)*/
    @Column
    private String designationOfMeasuringInstruments;

    /**Дата утверждения сертификата*/
    @Column(nullable = false)
    private LocalDateTime dateOfApproval;

    /**Межповерочный интервал, количество, Он может отсутствовать*/
    @Column
    private Integer vmiInterval;//СДелать адаптер под свой период с учетом всяких штук

    /**Единица измерения межповерочного интервала*/
    @Column
    private ChronoUnit vmiUnit;

    /**Приказ об утверждении типа*/
    @OneToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TypeApprovalOrder typeApprovalOrder;

    /**Описание типа*/
    @OneToOne (cascade = CascadeType.ALL)
    private TypeDescriptionDocument typeDescription;

    /**Наименование методики поверки, verification of a measuring instrument (VMI) -
     * Поверка (РМГ 29) 10 пункт */
    @OneToOne
    private VmiProcedure vmiProcedure;

    @OneToOne
    private Equipment equipment;

}
