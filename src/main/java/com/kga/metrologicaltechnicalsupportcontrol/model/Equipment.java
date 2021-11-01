package com.kga.metrologicaltechnicalsupportcontrol.model;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.model.documents.TypeApproval;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**Класс-модель для описания оборудования*/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Equipment implements HasId, Comparable<Equipment> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**Наименование оборудования*/
    @Column(nullable = false)
    private String title;

    /**Документ об утверждении типа. type approval  - утверждение типа*/
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "equipment")
    private TypeApproval typeApproval;

    /**Привязка к графику*//*
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkPlan workPlan;*/

    /**Заводской, серийный, присвоенный т.д. индивидуальный номер*//*
    @Column
    private String serialNumber;*/

    /**Объект, установка, помещение на котором установлено оборудование*/
    @ManyToOne(fetch = FetchType.EAGER)
    private TechObject techObject;

    /**Операции обслуживания*/
    @ManyToMany
    @JoinTable(name = "EQUIPMENT_MAINTENANCE_OPERATIONS",
                joinColumns = @JoinColumn(name = "EQUIPMENT_ID"),
    inverseJoinColumns = @JoinColumn (name = "MAINTENANCE_OPERATIONS_ID"))
    private List<MaintenanceOperations> maintenanceOperations;


    @Override
    public int compareTo(Equipment equipment) {
        return this.title.compareTo(equipment.title);
    }
}
