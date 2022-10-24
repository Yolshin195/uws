package com.provider.uws.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "uws_customer")
public class Customer extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;
}
