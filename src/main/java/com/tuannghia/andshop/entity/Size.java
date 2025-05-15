package com.tuannghia.andshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "size")
public class Size extends AbstractBase {

    @Column(name = "name")
    private String name;

}
