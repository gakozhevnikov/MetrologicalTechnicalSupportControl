package com.kga.metrologicaltechnicalsupportcontrol.model;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**Годовой график работ заведенный в формат базы данных*/

@Getter
@Setter
@Data
@Entity
public class WorkPlan implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*@OneToMany
    private List<TechObject> techObject;*/

    /*@OneToMany
    private List<Equipment> equipment;*/

    /**Заводской, серийный, присвоенный т.д. индивидуальный номер*/
    @Column
    private String serialNumber;

    @Column
    private String januaryCategory;

    @Column
    private String januaryTenDayPeriod;

    @Column
    private String februaryCategory;

    @Column
    private String februaryTenDayPeriod;

    @Column
    private String marchCategory;

    @Column
    private String marchTenDayPeriod;

    @Column
    private String aprilCategory;

    @Column
    private String aprilTenDayPeriod;

    @Column
    private String mayCategory;

    @Column
    private String mayTenDayPeriod;

    @Column
    private String juneCategory;

    @Column
    private String juneTenDayPeriod;

    @Column
    private String julyCategory;

    @Column
    private String julyTenDayPeriod;

    @Column
    private String augustCategory;

    @Column
    private String augustTenDayPeriod;

    @Column
    private String septemberCategory;

    @Column
    private String septemberTenDayPeriod;

    @Column
    private String octoberCategory;

    @Column
    private String octoberTenDayPeriod;

    @Column
    private String novemberCategory;

    @Column
    private String novemberTenDayPeriod;

    @Column
    private String decemberCategory;

    @Column
    private String decemberTenDayPeriod;
}
