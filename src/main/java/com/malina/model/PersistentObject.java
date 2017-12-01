package com.malina.model;

import javax.persistence.*;

/**
 * Created by pawel on 23.11.17.
 */
@MappedSuperclass
public class PersistentObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
