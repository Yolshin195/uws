package com.provider.uws.model;

public enum TransactionTypeEnum {
    CREDIT(1),
    DEBIT(2);

    private final Integer value;

    TransactionTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static TransactionTypeEnum valueOf(Integer id) {
        for (TransactionTypeEnum type : TransactionTypeEnum.values()) {
            if (type.getValue().equals(id)) {
                return type;
            }
        }

        return null;
    }
}
