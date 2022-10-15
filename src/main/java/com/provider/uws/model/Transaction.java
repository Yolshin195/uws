package com.provider.uws.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uws_transaction")
public class Transaction extends BaseEntity {
    @Column(name = "transaction_id")
    Long transactionId;

    @Column(name = "balance_before_transaction")
    Long balanceBeforeSurgery;

    @Column(name = "balance_after_transaction")
    Long balanceAfterSurgery;

    @Column(name = "amount")
    Long amount;

    @Column(name = "transaction_type")
    Integer transactionType;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    Wallet wallet;

    public TransactionTypeEnum getTransactionType() {
        return TransactionTypeEnum.valueOf(transactionType);
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType.getValue();
    }
}
