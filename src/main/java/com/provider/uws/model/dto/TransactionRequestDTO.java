package com.provider.uws.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO extends RequestDTO {

    private Long transactionId;

    private LocalDateTime transactionTime;

    private String pin;

    private Long amount;

}
