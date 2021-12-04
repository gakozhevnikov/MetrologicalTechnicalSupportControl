package com.kga.metrologicaltechnicalsupportcontrol.model.maintenance;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**Операции обслуживания*/
@Data
@Getter
@Setter
@ToString
@Entity
public class MaintenanceOperations implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**Операция в обслуживании*/
    @Column(nullable = false)
    private String operation;

    /**Номер последовательности выполнения операции*/
    @Column(nullable = false)
    private Integer sequence;

    //продумать про базовые общие операции

    /**Вид обслуживания к которому относиться данная операция*/
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeService typeService;

    /**Назначение операции к которому относиться данная операция*/
    @ManyToOne(fetch = FetchType.EAGER)
    private PurposeOperations purposeOperations;

    /**Оборудование к которому относиться операция*/
    @ManyToMany( mappedBy = "maintenanceOperations")
    private List<Equipment> equipment;

}
