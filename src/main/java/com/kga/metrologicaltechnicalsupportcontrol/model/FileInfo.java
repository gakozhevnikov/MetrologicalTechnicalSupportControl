package com.kga.metrologicaltechnicalsupportcontrol.model;
/*https://javarush.ru/groups/posts/2476-sokhranenie-faylov-v-prilozhenie-i-dannihkh-o-nikh-na-bd?post=full#discussion*/

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**Модель загрузки/выгрузки файла*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
//@Entity
public class FileInfo implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Long size;
    @Column
    private String key;
    @Column
    private LocalDate uploadDate;
}
