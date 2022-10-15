package com.provider.uws.model;

public enum TransactionTypeEnum {
    CREDIT(1),
    DEBIT(2);

    private final Integer id;

    TransactionTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
