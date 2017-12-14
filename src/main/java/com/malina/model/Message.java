package com.malina.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by pawel on 14.12.17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends PersistentObject{

    @OneToOne
    private User author;
    private Date date = new Date();

    @Lob
    private String content;
}
