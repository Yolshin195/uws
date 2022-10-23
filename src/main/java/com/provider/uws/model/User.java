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
@Table(name = "uws_user")
public class User extends BaseEntity {

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "active")
    Boolean active;
}
