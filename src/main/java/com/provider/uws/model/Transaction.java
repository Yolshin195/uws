package com.provider.uws.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
public class Transaction extends BaseEntity {
    @Column(name = "balance_before_transaction")
    Long balanceBeforeSurgery;

    @Column(name = "balance_after_transaction")
    Long balanceAfterSurgery;

    @Column(name = "amount")
    Long amount;

    @Column(name = "transaction_type")
    TransactionTypeEnum transactionType;

    @ManyToOne
    Wallet wallet;
}
