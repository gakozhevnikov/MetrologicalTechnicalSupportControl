package com.kga.metrologicaltechnicalsupportcontrol.model;

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class EquipmentWithAttributes implements HasId, Comparable<EquipmentWithAttributes>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**Заводской, серийный, присвоенный т.д. индивидуальный номер*/
    @Column
    private String serialNumber;

    /**Дата метрологического контроля. verification of a measuring instrument (VMI)*/
    @Column
    private LocalDateTime dateVMI;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Position position;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public int compareTo(EquipmentWithAttributes ewa) {
        return this.serialNumber.compareTo(ewa.serialNumber);
    }


}
