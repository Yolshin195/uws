package com.provider.uws.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    protected Long serviceId;

    protected String phone;

    protected String number;

    protected String pin;

    public boolean isPhone() {
        return phone != null;
    }

    public boolean isNumber() {
        return number != null;
    }
}
