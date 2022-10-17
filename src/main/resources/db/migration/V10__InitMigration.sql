create table uws_user (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    username varchar2(255) not null,
    password varchar2(255) not null,
    active BOOLEAN,
    --
    constraint uws_user_pk primary key (id)
);

create table uws_customer (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    name varchar2(255),
    phone varchar2(13),
    --

    --
    constraint uws_customer_pk primary key (id)
);

create table uws_provider (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    service_id bigint,
    name varchar2(255),
    --

    --
    constraint uws_provider_pk primary key (id)
);

create table uws_wallet (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    pin varchar2(6),
    balance bigint,
    virtual_wallet_number varchar2(16),
    --
    provider_id uuid,
    customer_id uuid,
    --
    constraint uws_wallet_pk primary key (id)
);

create table uws_transaction (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    transaction_id bigint not null,
    balance_before_transaction bigint,
    balance_after_transaction bigint,
    amount bigint,
    transaction_type int,
    transaction_time TIMESTAMP,
    time_stamp TIMESTAMP,
    --
    wallet_id uuid,
    --
    constraint uws_transaction_pk primary key (id)
);

ALTER TABLE uws_transaction ADD CONSTRAINT uc_uws_transaction_transaction UNIQUE (transaction_id);
ALTER TABLE uws_transaction ADD CONSTRAINT FK_UWS_TRANSACTION_ON_WALLET FOREIGN KEY (wallet_id) REFERENCES uws_wallet (id);

insert into uws_user (id, delete_ts, update_ts, create_ts, version, username, password, active)
values ('d9080d12-91aa-4fda-8757-1d911a512453', null, null, CURRENT_TIMESTAMP(), 1, 'user', 'pwd', true);

insert into uws_provider (id, delete_ts, update_ts, create_ts, version, service_id, name)
values ('85a9d2c4-7bfe-4170-a518-060eac846744', null, null, CURRENT_TIMESTAMP(), 1, 1, 'test');

insert into uws_customer (id, delete_ts, update_ts, create_ts, version, phone, name)
values ('98147ae6-695a-4a37-9d3a-36086244f1c5', null, null, CURRENT_TIMESTAMP(), 1, '+998917813126', 'test');

insert into uws_wallet (id, delete_ts, update_ts, create_ts, version, pin, balance, virtual_wallet_number, provider_id, customer_id)
values ('258223cf-e81c-4624-ab3e-443e6ebccfa3', null, null, CURRENT_TIMESTAMP(), 1, '123456', 0, '9991234567891234', '85a9d2c4-7bfe-4170-a518-060eac846744', '98147ae6-695a-4a37-9d3a-36086244f1c5');

commit;