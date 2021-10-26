package com.kga.metrologicaltechnicalsupportcontrol.model;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**Объект, сооружение, установка*/
@Getter
@Setter
@Data
@Entity
public class TechObject implements Comparable<TechObject>, HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    /*@ManyToOne
    private WorkPlan workPlan;*/

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "techObject")
    private List<Equipment> equipments;

    @Override
    public int compareTo(TechObject o) {
        return this.title.compareTo(o.title);
    }


}
