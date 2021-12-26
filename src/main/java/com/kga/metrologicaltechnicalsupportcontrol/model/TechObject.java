package com.kga.metrologicaltechnicalsupportcontrol.model;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.Title;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**Объект, сооружение, установка*/
@Getter
@Setter
@Data
@ToString
@Entity
public class TechObject implements Comparable<TechObject>, HasId, Title {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    /*@ManyToOne
    private WorkPlan workPlan;*/

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "techObject")
    private List<Position> positions;

    @Override
    public int compareTo(TechObject o) {
        return this.title.compareTo(o.title);
    }


    @Override
    public void setTitle(String title) {
        this.title=title;
    }
}
