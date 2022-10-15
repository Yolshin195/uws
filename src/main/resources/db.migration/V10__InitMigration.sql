create table uws_user (
    id uuid not null,
    delete_ts TIMESTAMP,
    update_ts TIMESTAMP,
    create_ts TIMESTAMP,
    version int,
    --
    username varchar2(250) not null,
    password varchar2(250) not null,
    active BOOLEAN,

    constraint uws_user_pk primary key (id)
);

insert into BOOK (id, delete_ts, update_ts, create_ts, version, username, password, active)
values (random_uuid(), null, null, CURRENT_TIMESTAMP(), 1, "test", "test", true);

commit;