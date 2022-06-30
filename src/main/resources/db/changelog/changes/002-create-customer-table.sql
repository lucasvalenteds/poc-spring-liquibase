create table CUSTOMER
(
    CUSTOMER_ID uuid,
    NAME        varchar not null,
    COUNTRY_ID  uuid,

    primary key (CUSTOMER_ID),
    foreign key (COUNTRY_ID) references COUNTRY (COUNTRY_ID)
);