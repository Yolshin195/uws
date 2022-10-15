package com.provider.uws.model;

import com.provider.uws.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "uws_customer")
public class Customer extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;
}
