package com.kga.metrologicaltechnicalsupportcontrol.model.documents;


import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**Класс для хранение файлов в формате байт в базе*/
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractBaseDocument implements HasId {



    /**Уникальный номер идентификатор*/
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    /**Файл документа*/
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] documentFile;

    public AbstractBaseDocument() {
    }

    public AbstractBaseDocument(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public AbstractBaseDocument(Long id, byte[] documentFile) {
        this.id = id;
        this.documentFile = documentFile;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }


}
