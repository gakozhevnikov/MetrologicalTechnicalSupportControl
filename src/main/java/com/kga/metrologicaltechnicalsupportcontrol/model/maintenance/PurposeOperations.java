package com.kga.metrologicaltechnicalsupportcontrol.model.maintenance;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**Назначение операции, для обслуживания, чек-листа, дополнительных работ*/
@Data
@Getter
@Setter
@ToString
@Entity
public class PurposeOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**Наименование, обозначение, символ назначения*/
    @Column(nullable = false)
    private String designation;

    /**Для создания связи между видом обслуживания и операциями назначения*/
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "purposeOperations")
    private List<MaintenanceOperations> maintenanceOperations;

}
