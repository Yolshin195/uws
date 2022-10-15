package com.provider.uws.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@Builder
public class Wallet extends BaseEntity {

    @ManyToOne
    User user;
}
