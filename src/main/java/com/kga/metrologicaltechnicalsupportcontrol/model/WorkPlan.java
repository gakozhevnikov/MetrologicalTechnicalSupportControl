package com.kga.metrologicaltechnicalsupportcontrol.model;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Month;
import java.util.List;

/**Годовой график работ заведенный в формат базы данных*/

@Getter
@Setter
@Data
@Entity
public class WorkPlan implements HasId, Comparable<WorkPlan> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*@OneToMany
    private List<TechObject> techObject;*/

    @OneToOne
    private EquipmentWithAttributes equipmentWithAttributes;

    @Column
    private String dateOfWork;

    @OneToOne
    private TypeService typeService;

    @Column
    private Month month;

    @Override
    public int compareTo(WorkPlan o) {//реализовать сравнение по нескольким полям
        return this.equipmentWithAttributes.compareTo(o.equipmentWithAttributes);
    }
}
