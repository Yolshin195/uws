package com.provider.uws.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uws_wallet")
public class Wallet extends BaseEntity {

    @Column(name = "virtual_wallet_number")
    String number;

    @Column(name = "pin")
    String pin;

    @Column(name = "balance")
    Long balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    Provider provider;
}
