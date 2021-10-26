package com.kga.metrologicaltechnicalsupportcontrol.model.documents;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**Описание типа */
@Getter
@Setter
@Data
@Entity
public class TypeDescriptionDocument extends AbstractBaseDocument {

    /**Номер в реестре*/
    @Column(nullable = false)
    private String indexInRegistry;

    @OneToOne (cascade = CascadeType.ALL)
    private TypeApproval typeApproval;


}
