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
    balance_before_transaction bigint,
    balance_after_transaction bigint,
    amount bigint,
    transaction_type int,
    --
    wallet_id uuid,
    --
    constraint uws_transaction_pk primary key (id)
);

insert into uws_user (id, delete_ts, update_ts, create_ts, version, username, password, active)
values (random_uuid(), null, null, CURRENT_TIMESTAMP(), 1, 'user', 'pwd', true);

commit;