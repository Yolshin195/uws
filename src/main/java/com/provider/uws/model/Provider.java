package com.provider.uws.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uws_provider")
public class Provider extends BaseEntity {

    @Column(name = "service_id")
    Long serviceId;

    @Column(name = "name")
    String name;
}
