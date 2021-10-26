package com.kga.metrologicaltechnicalsupportcontrol.model.maintenance;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**Вид сервиса, ТО-1, ежедне-,  группировка операций чек-листа, группировка дополнительных работ и т.д.*/
@Entity
@Data
public class TypeService implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**Наименование, обозначение, символ обслуживания*/
    @Column(nullable = false)
    private String designation;

    /**Для создания связи между видом обслуживания и операциями обслуживания*/
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "typeService")
    private List<MaintenanceOperations> maintenanceOperations;

}
