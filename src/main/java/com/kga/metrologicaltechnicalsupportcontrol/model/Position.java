package com.kga.metrologicaltechnicalsupportcontrol.model;

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.Title;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**Сущность для хранения и работы с обозначениями позиций установки оборудования на объектах */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Position implements HasId, Title, Comparable<Position>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    TechObject techObject;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "position")
    private List<EquipmentWithAttributes> equipmentWithAttributes;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public int compareTo(Position position) {
        return this.title.compareTo(position.title);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title=title;
    }
}
