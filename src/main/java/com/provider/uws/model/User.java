package com.provider.uws.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "uws_user")
public class User extends BaseEntity {

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "active")
    Boolean active;
}
