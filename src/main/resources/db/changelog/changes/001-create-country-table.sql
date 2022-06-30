create table COUNTRY
(
    COUNTRY_ID uuid,
    NAME       varchar    not null,
    CODE       varchar(2) not null,

    primary key (COUNTRY_ID),
    unique (NAME, CODE)
);