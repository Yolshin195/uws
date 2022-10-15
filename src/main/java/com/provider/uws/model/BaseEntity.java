package com.provider.uws.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @Column(name = "id")
    UUID id = UUID.randomUUID();

    @Column(name = "delete_ts")
    LocalDateTime deleteTs;

    @Column(name = "update_ts")
    LocalDateTime updateTs;

    @Column(name = "create_ts")
    LocalDateTime createTs;

    @Column(name = "version")
    Integer version;
}
