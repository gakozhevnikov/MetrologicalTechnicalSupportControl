package com.kga.metrologicaltechnicalsupportcontrol.model.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**Приказ об утверждении типа*/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class TypeApprovalOrder extends AbstractBaseDocument {
    /**Номер приказа*/
    @Column (nullable = false)
    private String index;

    /**Дата приказа*/
    @Column(nullable = false)
    private LocalDateTime dateOfOrder;

    /**Пункт приказа*/
    @Column
    private String indexInOrder;

    @OneToOne(cascade = CascadeType.ALL)
    private TypeApproval typeApproval;
}
