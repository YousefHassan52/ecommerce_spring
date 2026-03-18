alter table users
    add column role varchar(25) default 'CUSTOMER' not null;