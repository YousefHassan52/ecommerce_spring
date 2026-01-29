create table carts(
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    date_created date default(curdate()) not null
);

create table cart_items(
    id BIGINT AUTO_INCREMENT primary key,
    cart_id binary(16) not null,
    product_id BIGINT  not null,
    quantity int default 1 not null,
    constraint carts_cart_item_fk foreign key (cart_id) references carts(id) on delete cascade,
    constraint product_cart_item_fk foreign key (product_id) references products(id) on delete cascade ,
    constraint cart_item_unique_cart_product unique (cart_id,product_id)
);