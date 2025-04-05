create table tbl_branch
(
    id              int auto_increment
        primary key,
    is_active       bit          null,
    created_at      datetime(6)  null,
    updated_at      datetime(6)  null,
    address         varchar(255) null,
    name            varchar(255) null,
    operating_hours varchar(255) null,
    phone           varchar(255) null
);

create table tbl_cart
(
    created_at datetime(6)  null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)  null,
    user_id    bigint       null,
    session_id varchar(255) null
);

alter table tbl_cart
    add constraint UKeymp5kbfgooc31l260hr63vox
        unique (user_id);

create table tbl_cart_item
(
    price_at_addition decimal(10, 2) null,
    quantity          int            null,
    cart_id           bigint         null,
    created_at        datetime(6)    null,
    id                bigint auto_increment
        primary key,
    menu_item_id      bigint         null,
    updated_at        datetime(6)    null
);

alter table tbl_cart_item
    add constraint FKhaw0aw4g8s9icxekpl4oi715a
        foreign key (cart_id) references tbl_cart (id);

create table tbl_cart_item_option
(
    menu_item_option_id int         null,
    cart_item_id        bigint      null,
    created_at          datetime(6) null,
    id                  bigint auto_increment
        primary key,
    updated_at          datetime(6) null
);

alter table tbl_cart_item_option
    add constraint FKe4c9d5hw50tf9yiodk28dytt4
        foreign key (cart_item_id) references tbl_cart_item (id);

create table tbl_loyalty_point_transaction
(
    points_change    int          null,
    transaction_type tinyint      null,
    created_at       datetime(6)  null,
    id               bigint auto_increment
        primary key,
    order_id         bigint       null,
    updated_at       datetime(6)  null,
    user_id          bigint       null,
    description      varchar(255) null
);

alter table tbl_loyalty_point_transaction
    add constraint UK3ptwulckn5xfftg0cw5wmir8g
        unique (order_id);

alter table tbl_loyalty_point_transaction
    add check (`transaction_type` between 0 and 4);

create table tbl_menu_category
(
    id          int auto_increment
        primary key,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    description varchar(255) null,
    image_url   varchar(255) null,
    name        varchar(255) null
);

create table tbl_menu_item
(
    base_price       decimal(10, 2) null,
    is_available     bit            null,
    menu_category_id int            null,
    created_at       datetime(6)    null,
    id               bigint auto_increment
        primary key,
    updated_at       datetime(6)    null,
    description      varchar(255)   null,
    image_url        varchar(255)   null,
    name             varchar(255)   null
);

alter table tbl_cart_item
    add constraint FK1jeys141vung8xso07bybmgt8
        foreign key (menu_item_id) references tbl_menu_item (id);

alter table tbl_menu_item
    add constraint FKnc0a94b15efn4cwtjvscmji0h
        foreign key (menu_category_id) references tbl_menu_category (id);

create table tbl_menu_item_option
(
    additional_price decimal(10, 2) null,
    id               int auto_increment
        primary key,
    option_id        int            null,
    created_at       datetime(6)    null,
    menu_item_id     bigint         null,
    updated_at       datetime(6)    null,
    value            varchar(255)   null
);

alter table tbl_cart_item_option
    add constraint FK5jkvm12p8q6gs1nbl68w048ka
        foreign key (menu_item_option_id) references tbl_menu_item_option (id);

alter table tbl_menu_item_option
    add constraint FKpdwav2ydfeiotwveyvwvdvc4x
        foreign key (menu_item_id) references tbl_menu_item (id);

create table tbl_menu_item_review
(
    rating       int                                      null,
    created_at   datetime(6)                              null,
    id           bigint auto_increment
        primary key,
    menu_item_id bigint                                   null,
    updated_at   datetime(6)                              null,
    user_id      bigint                                   null,
    comment      varchar(255)                             null,
    status       enum ('APPROVED', 'PENDING', 'REJECTED') null
);

alter table tbl_menu_item_review
    add constraint FKf7o0uy5c10oh8dtg4cbl2ebap
        foreign key (menu_item_id) references tbl_menu_item (id);

create table tbl_option
(
    id          int auto_increment
        primary key,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    description varchar(255) null,
    name        varchar(255) null
);

alter table tbl_menu_item_option
    add constraint FKcpumophqqlija90qfopqnllr5
        foreign key (option_id) references tbl_option (id);

create table tbl_order
(
    branch_id        int                                                           null,
    total_price      decimal(10, 2)                                                null,
    created_at       datetime(6)                                                   null,
    id               bigint auto_increment
        primary key,
    updated_at       datetime(6)                                                   null,
    user_id          bigint                                                        null,
    note             varchar(255)                                                  null,
    order_code       varchar(255)                                                  null,
    shipping_address varchar(255)                                                  null,
    order_status     enum ('CANCELLED', 'COMPLETED', 'IN_PROGRESS', 'PENDING')     null,
    payment_method   enum ('BANK_TRANSFER', 'COD', 'CREDIT_CARD', 'MOMO', 'VNPAY') null
);

alter table tbl_loyalty_point_transaction
    add constraint FKpvi1u0tgxkxcufodomxi9e45s
        foreign key (order_id) references tbl_order (id);

alter table tbl_order
    add constraint UKt91aupbg9yjoyjtx66qo1n0mq
        unique (order_code);

alter table tbl_order
    add constraint FK6tawch2hb6phj81libq4di3d7
        foreign key (branch_id) references tbl_branch (id);

create table tbl_order_item
(
    price_per_unit decimal(10, 2) null,
    quantity       int            null,
    total_price    decimal(10, 2) null,
    created_at     datetime(6)    null,
    id             bigint auto_increment
        primary key,
    menu_item_id   bigint         null,
    order_id       bigint         null,
    updated_at     datetime(6)    null,
    menu_item_name varchar(255)   null
);

alter table tbl_order_item
    add constraint FK8gr4qdx0amivl0prt6tf4k9vv
        foreign key (menu_item_id) references tbl_menu_item (id);

alter table tbl_order_item
    add constraint FKmkqpajkg6p2wq4owcv1v08pc5
        foreign key (order_id) references tbl_order (id);

create table tbl_order_item_option
(
    additional_price decimal(10, 2) null,
    created_at       datetime(6)    null,
    id               bigint auto_increment
        primary key,
    order_item_id    bigint         null,
    updated_at       datetime(6)    null,
    option_name      varchar(255)   null,
    option_value     varchar(255)   null
);

alter table tbl_order_item_option
    add constraint FKox3jjb96i81yldxe3yh57sf0b
        foreign key (order_item_id) references tbl_order_item (id);

create table tbl_payment
(
    amount           decimal(10, 2)                                                 null,
    created_at       datetime(6)                                                    null,
    id               bigint auto_increment
        primary key,
    order_id         bigint                                                         null,
    paid_at          datetime(6)                                                    null,
    updated_at       datetime(6)                                                    null,
    gateway_response varchar(255)                                                   null,
    transaction_code varchar(255)                                                   null,
    payment_method   enum ('BANK_TRANSFER', 'COD', 'CREDIT_CARD', 'MOMO', 'VNPAY')  null,
    status           enum ('CANCELLED', 'FAILED', 'PENDING', 'REFUNDED', 'SUCCESS') null
);

alter table tbl_payment
    add constraint FKac54xp3r2r3m9datds9351ric
        foreign key (order_id) references tbl_order (id);

create table tbl_role
(
    created_at  datetime(6)                                    null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)                                    null,
    description varchar(255)                                   null,
    name        enum ('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_USER') null
);

create table tbl_shipment
(
    created_at      datetime(6)                                                                                            null,
    id              bigint auto_increment
        primary key,
    order_id        bigint                                                                                                 null,
    updated_at      datetime(6)                                                                                            null,
    user_id         bigint                                                                                                 null,
    delivery_status enum ('ASSIGNED_SHIPPER', 'DELIVERED', 'FAILED_ATTEMPT', 'OUT_FOR_DELIVERY', 'PICKED_UP', 'PREPARING') null
);

alter table tbl_shipment
    add constraint FKb1wxiaexwu36k7740xfs776x1
        foreign key (order_id) references tbl_order (id);

create table tbl_shipment_tracking_event
(
    location_latitude  double                                                                                                 null,
    location_longitude double                                                                                                 null,
    created_at         datetime(6)                                                                                            null,
    event_time         datetime(6)                                                                                            null,
    id                 bigint auto_increment
        primary key,
    shipment_id        bigint                                                                                                 null,
    updated_at         datetime(6)                                                                                            null,
    note               varchar(255)                                                                                           null,
    delivery_status    enum ('ASSIGNED_SHIPPER', 'DELIVERED', 'FAILED_ATTEMPT', 'OUT_FOR_DELIVERY', 'PICKED_UP', 'PREPARING') null
);

alter table tbl_shipment_tracking_event
    add constraint FKo94f45rni5ivrdkfcrebx6aaf
        foreign key (shipment_id) references tbl_shipment (id);

create table tbl_user
(
    date_of_birth          date                             null,
    loyalty_points_balance int                              null,
    created_at             datetime(6)                      null,
    id                     bigint auto_increment
        primary key,
    updated_at             datetime(6)                      null,
    address                varchar(255)                     null,
    avatar                 varchar(255)                     null,
    email                  varchar(255)                     null,
    full_name              varchar(255)                     null,
    password               varchar(255)                     null,
    phone                  varchar(255)                     null,
    gender                 enum ('FEMALE', 'MALE', 'OTHER') null,
    is_active              enum ('ACTIVE', 'INACTIVE')      null
);

alter table tbl_cart
    add constraint FKhv6grtjnmtoylt2yyt4wmqtf3
        foreign key (user_id) references tbl_user (id);

alter table tbl_loyalty_point_transaction
    add constraint FKmidvb51pjeo3bd9m5hn8wiy2q
        foreign key (user_id) references tbl_user (id);

alter table tbl_menu_item_review
    add constraint FK49xv7if8qcql1kd6igf4soncx
        foreign key (user_id) references tbl_user (id);

alter table tbl_order
    add constraint FKhyolniflkctr0p6bp4t8me9vj
        foreign key (user_id) references tbl_user (id);

alter table tbl_shipment
    add constraint FKeq5lvi93h8mlrn29xbw3w0lvg
        foreign key (user_id) references tbl_user (id);

alter table tbl_user
    add constraint UKd6tho5pxk6qd8xem6vwou8sdp
        unique (phone);

alter table tbl_user
    add constraint UKnpn1wf1yu1g5rjohbek375pp1
        unique (email);

create table tbl_user_has_role
(
    created_at datetime(6) null,
    id         bigint auto_increment
        primary key,
    role_id    bigint      null,
    updated_at datetime(6) null,
    user_id    bigint      null
);

alter table tbl_user_has_role
    add constraint FKlndw06guu8xedftiuosbtbr0a
        foreign key (user_id) references tbl_user (id);

alter table tbl_user_has_role
    add constraint FKnbcb2rhv9gavk1vtounetntw0
        foreign key (role_id) references tbl_role (id);

create table tbl_wishlist_item
(
    created_at   datetime(6) null,
    id           bigint auto_increment
        primary key,
    menu_item_id bigint      null,
    updated_at   datetime(6) null,
    user_id      bigint      null
);

alter table tbl_wishlist_item
    add constraint FKg5dtuut70bnlm8tcge8wsxs03
        foreign key (menu_item_id) references tbl_menu_item (id);

alter table tbl_wishlist_item
    add constraint FKsi21859ual7hbkhv4rlx96fow
        foreign key (user_id) references tbl_user (id);

INSERT INTO tbl_role (id, name, created_at, updated_at, description)
VALUES (2, 'ROLE_STAFF', NULL, NULL, NULL),
       (3, 'ROLE_USER', NULL, NULL, NULL),
       (1, 'ROLE_ADMIN', NULL, NULL, NULL);

INSERT INTO tbl_user (date_of_birth, loyalty_points_balance, created_at, id, updated_at, address, avatar, email, full_name, password, phone, gender, is_active)
VALUES ('2004-09-25', 0, '2025-04-04 12:19:45.269846', 2, '2025-04-04 12:19:45.269846', 'VN', null, 'user@gmail.com', 'user', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', '0979859449', 'MALE', 'ACTIVE'),
       ('2004-09-25', 0, '2025-04-04 12:19:45.269846', 1, '2025-04-04 12:19:45.269846', 'VN', null, 'admin@gmail.com', 'admin', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', '0979859559', 'MALE', 'ACTIVE');

INSERT INTO tbl_user_has_role (created_at, id, role_id, updated_at, user_id)
VALUES ('2025-04-04 12:19:45.276706', 1, 3, '2025-04-04 12:19:45.276706', 2),
       ('2025-04-04 12:19:45.276706', 2, 1, '2025-04-04 12:19:45.276706', 1);

-- option
INSERT INTO tbl_option (name, description, created_at, updated_at)
VALUES
('Kích cỡ', 'Tùy chọn kích cỡ cho món ăn hoặc đồ uống (ví dụ: Nhỏ, Vừa, Lớn)', NOW(), NOW()),
('Loại đế bánh', 'Tùy chọn loại đế cho Pizza (ví dụ: Dày, Mỏng Giòn)', NOW(), NOW()),
('Thêm Topping Pizza', 'Tùy chọn thêm các loại topping cho Pizza (ví dụ: Phô mai, Xúc xích, Nấm)', NOW(), NOW()),
('Mức độ cay', 'Tùy chọn độ cay cho món ăn (ví dụ: Không cay, Cay vừa, Rất cay)', NOW(), NOW()),
('Thêm phô mai', 'Tùy chọn thêm phô mai vào món ăn (ví dụ: Burger, Mì Ý)', NOW(), NOW()),
('Thêm thịt/pattie', 'Tùy chọn thêm thịt hoặc miếng pattie cho Burger', NOW(), NOW()),
('Sốt chấm kèm', 'Tùy chọn các loại sốt chấm đi kèm (ví dụ: Tương cà, Tương ớt, Sốt Mayonnaise)', NOW(), NOW()),
('Mức độ đá', 'Tùy chọn lượng đá trong đồ uống (ví dụ: Không đá, Ít đá, Nhiều đá)', NOW(), NOW()),
('Mức độ đường', 'Tùy chọn lượng đường trong đồ uống (ví dụ: 100% đường, 70% đường, 50% đường, Không đường)', NOW(), NOW()),
('Topping đồ uống', 'Tùy chọn thêm topping cho đồ uống (ví dụ: Trân châu, Thạch trái cây)', NOW(), NOW());

-- menu_category
INSERT INTO tbl_menu_category (id, name, created_at, updated_at, description, image_url)
VALUES
    (1, 'Pizza', NOW(), NOW(), 'Pizza đế giòn, nhiều loại nhân.', 'https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80'),
    (2, 'Hamburger', NOW(), NOW(), 'Burger bò, gà kẹp rau tươi và sốt.', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60'),
    (3, 'Mì ý', NOW(), NOW(), 'Mì Ý sốt bò bằm hoặc sốt kem.', 'https://pastafrescasaigon.com/wp-content/uploads/2023/08/2017-04-21_0000185.png'),
    (4, 'Gà rán', NOW(), NOW(), 'Gà rán giòn rụm, có vị cay hoặc truyền thống.', 'https://images.unsplash.com/photo-1562967914-01efa7e87832?ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'),
    (5, 'Món ăn kèm', NOW(), NOW(), 'Món ăn kèm: Khoai tây chiên, hành tây chiên...', 'https://www.allrecipes.com/thmb/Hkg8fY6d22mIn-OZm3AznW00-T8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/16760-Old-Fashioned-Onion-Rings-ddmfs-4x3-1597-f5e601d18049437887b748e70acd0703.jpg'),
    (6, 'Nước uống', NOW(), NOW(), 'Nước ngọt, nước suối, trà.', 'https://images.unsplash.com/photo-1551024709-8f23befc6f87?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');

INSERT INTO tbl_menu_item (id, name, created_at, updated_at, description, image_url, base_price, is_available, menu_category_id)
VALUES
-- Danh mục 1: PIZZA (menu_category_id = 1)
(1, 'Pizza Phô Mai Classic', NOW(), NOW(), 'Sốt cà chua, phô mai Mozzarella tan chảy.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Pho+Mai', 8.99, 1, 1),
(2, 'Pizza Pepperoni Supreme', NOW(), NOW(), 'Xúc xích Pepperoni cay nồng phủ kín mặt bánh.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Pepperoni', 9.99, 1, 1),
(3, 'Pizza Thịt Nguội & Nấm', NOW(), NOW(), 'Kết hợp thịt nguội, nấm tươi và phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Ham+Mushroom', 10.49, 1, 1),
(4, 'Pizza Hải Sản Nhiệt Đới', NOW(), NOW(), 'Tôm, mực, thanh cua, thơm, sốt Thousand Island.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Seafood', 12.99, 1, 1),
(5, 'Pizza Gà BBQ', NOW(), NOW(), 'Thịt gà, sốt BBQ, hành tây, ớt chuông.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+BBQ+Chicken', 11.49, 1, 1),
(6, 'Pizza Rau Củ Tổng Hợp', NOW(), NOW(), 'Nấm, ớt chuông, hành tây, ô liu, cà chua.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Veggie', 9.49, 1, 1),
(7, 'Pizza Xúc Xích Ý & Hành Tây', NOW(), NOW(), 'Xúc xích Ý cay nhẹ, hành tây và phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Sausage+Onion', 10.99, 1, 1),
(8, 'Pizza 4 Loại Phô Mai', NOW(), NOW(), 'Mozzarella, Cheddar, Parmesan, Blue Cheese.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+4+Cheese', 11.99, 1, 1),
(9, 'Pizza Hawaii', NOW(), NOW(), 'Thịt nguội, thơm, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Hawaiian', 10.49, 1, 1),
(10, 'Pizza Bò Bằm & Ngô', NOW(), NOW(), 'Thịt bò bằm, ngô ngọt, hành tây, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Beef+Corn', 11.49, 1, 1),
(11, 'Pizza Gà Nấm Sốt Kem', NOW(), NOW(), 'Thịt gà, nấm, sốt kem trắng Alfredo.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Chicken+Alfredo', 11.99, 1, 1),
(12, 'Pizza Cay Kiểu Mexico', NOW(), NOW(), 'Thịt bò bằm cay, đậu đỏ, hành tây, ớt jalapeno.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Mexican', 11.99, 1, 1),
(13, 'Pizza Cá Ngừ & Ô Liu', NOW(), NOW(), 'Cá ngừ ngâm dầu, ô liu đen, hành tây.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Tuna+Olive', 10.99, 1, 1),
(14, 'Pizza Thịt Xông Khói', NOW(), NOW(), 'Thịt xông khói giòn rụm phủ phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Bacon', 10.99, 1, 1),
(15, 'Pizza Pesto Gà', NOW(), NOW(), 'Sốt Pesto, thịt gà nướng, cà chua bi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Pesto+Chicken', 11.99, 1, 1),
(16, 'Pizza Phô Mai Dê & Rau Bina', NOW(), NOW(), 'Phô mai dê, rau bina (spinach), tỏi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Goat+Cheese', 12.49, 1, 1),
(17, 'Pizza Margherita Đặc Biệt', NOW(), NOW(), 'Cà chua tươi, phô mai Mozzarella tươi, lá húng quế.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Margherita', 10.49, 1, 1),
(18, 'Pizza Sốt Trứng Muối Hải Sản', NOW(), NOW(), 'Sốt trứng muối đặc biệt, tôm, mực.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Salted+Egg', 13.49, 1, 1),
(19, 'Pizza Thập Cẩm (The Works)', NOW(), NOW(), 'Pepperoni, thịt nguội, bò bằm, nấm, ớt, hành tây, ô liu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+The+Works', 13.99, 1, 1),
(20, 'Pizza Đế Mỏng Giòn (Chọn Nhân)', NOW(), NOW(), 'Đế bánh siêu mỏng giòn, chọn 2 loại nhân.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Thin+Crust', 10.99, 1, 1),

-- Danh mục 2: HAMBURGER (menu_category_id = 2)
(21, 'Classic Beef Burger', NOW(), NOW(), 'Bò Mỹ, rau diếp, cà chua, hành tây, sốt burger.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Classic+Burger', 4.99, 1, 2),
(22, 'Cheeseburger', NOW(), NOW(), 'Bò Mỹ, phô mai American, rau diếp, cà chua, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheeseburger', 5.49, 1, 2),
(23, 'Double Cheeseburger', NOW(), NOW(), '2 lớp bò Mỹ, 2 lớp phô mai American, rau, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Double+Cheeseburger', 7.49, 1, 2),
(24, 'Bacon Cheeseburger', NOW(), NOW(), 'Bò Mỹ, phô mai American, thịt xông khói giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bacon+Cheeseburger', 6.49, 1, 2),
(25, 'Mushroom Swiss Burger', NOW(), NOW(), 'Bò Mỹ, nấm xào, phô mai Thụy Sĩ, sốt kem nấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mushroom+Burger', 6.99, 1, 2),
(26, 'Crispy Chicken Burger', NOW(), NOW(), 'Gà phi lê chiên giòn, rau diếp, sốt mayonnaise.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Crispy+Chicken+Burger', 5.99, 1, 2),
(27, 'Spicy Chicken Burger', NOW(), NOW(), 'Gà phi lê chiên giòn cay, rau diếp, sốt cay.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spicy+Chicken+Burger', 6.29, 1, 2),
(28, 'Grilled Chicken Burger', NOW(), NOW(), 'Gà phi lê nướng, rau diếp, cà chua, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Grilled+Chicken+Burger', 6.49, 1, 2),
(29, 'Fish Burger', NOW(), NOW(), 'Cá phi lê tẩm bột chiên, sốt tartar, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fish+Burger', 5.49, 1, 2),
(30, 'Veggie Burger', NOW(), NOW(), 'Nhân rau củ chiên, rau diếp, cà chua, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Veggie+Burger', 5.99, 1, 2),
(31, 'BBQ Bacon Burger', NOW(), NOW(), 'Bò Mỹ, thịt xông khói, sốt BBQ, hành tây chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQ+Bacon+Burger', 6.99, 1, 2),
(32, 'Jalapeno Burger', NOW(), NOW(), 'Bò Mỹ, phô mai Pepper Jack, ớt Jalapeno ngâm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Jalapeno+Burger', 6.49, 1, 2),
(33, 'Blue Cheese Burger', NOW(), NOW(), 'Bò Mỹ, phô mai xanh (Blue Cheese), hành tây caramel.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Blue+Cheese+Burger', 7.29, 1, 2),
(34, 'Teriyaki Burger', NOW(), NOW(), 'Bò Mỹ hoặc gà, sốt Teriyaki, lát thơm nướng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Teriyaki+Burger', 6.79, 1, 2),
(35, 'Breakfast Burger', NOW(), NOW(), 'Bò Mỹ, trứng ốp la, thịt xông khói, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Breakfast+Burger', 7.49, 1, 2),
(36, 'Slider Trio (Mini Burgers)', NOW(), NOW(), 'Bộ 3 burger nhỏ: bò, gà, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Slider+Trio', 8.99, 1, 2),
(37, 'Tower Burger', NOW(), NOW(), '2 lớp bò, 1 lớp gà chiên giòn, phô mai, bacon, hành tây chiên.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Tower+Burger', 9.99, 1, 2),
(38, 'Hawaiian Chicken Burger', NOW(), NOW(), 'Gà nướng, lát thơm nướng, sốt teriyaki.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Hawaiian+Chicken+Burger', 6.99, 1, 2),
(39, 'Avocado Burger', NOW(), NOW(), 'Bò Mỹ, lát bơ tươi, hành tây đỏ, rau mầm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Avocado+Burger', 7.49, 1, 2),
(40, 'Burger Gà Sốt Nấm', NOW(), NOW(), 'Gà nướng, sốt kem nấm, phô mai Thụy Sĩ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Mushroom+Burger', 6.99, 1, 2),

-- Danh mục 3: SPAGHETTI (menu_category_id = 3)
(41, 'Spaghetti Bolognese', NOW(), NOW(), 'Mì Ý sốt bò bằm cà chua cổ điển.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Bolognese', 6.99, 1, 3),
(42, 'Spaghetti Carbonara', NOW(), NOW(), 'Mì Ý sốt kem trứng và thịt xông khói.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Carbonara', 7.49, 1, 3),
(43, 'Spaghetti Sốt Kem Gà Nấm', NOW(), NOW(), 'Mì Ý sốt kem, thịt gà, nấm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Chicken+Alfredo', 7.99, 1, 3),
(44, 'Spaghetti Hải Sản Sốt Cà Chua', NOW(), NOW(), 'Mì Ý sốt cà chua, tôm, mực, nghêu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Seafood+Tomato', 8.99, 1, 3),
(45, 'Spaghetti Sốt Kem Hải Sản', NOW(), NOW(), 'Mì Ý sốt kem, tôm, mực, nghêu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Seafood+Cream', 9.49, 1, 3),
(46, 'Spaghetti Aglio e Olio', NOW(), NOW(), 'Mì Ý xào tỏi, dầu ô liu, ớt khô (cay nhẹ).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Aglio+Olio', 5.99, 1, 3),
(47, 'Spaghetti Aglio e Olio Tôm', NOW(), NOW(), 'Mì Ý xào tỏi ớt với tôm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Aglio+Olio+Shrimp', 8.49, 1, 3),
(48, 'Spaghetti Sốt Pesto', NOW(), NOW(), 'Mì Ý sốt lá húng quế Pesto.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Pesto', 6.99, 1, 3),
(49, 'Spaghetti Sốt Pesto Gà', NOW(), NOW(), 'Mì Ý sốt Pesto với thịt gà nướng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Pesto+Chicken', 8.49, 1, 3),
(50, 'Spaghetti Meatballs', NOW(), NOW(), 'Mì Ý sốt cà chua với thịt viên bò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Meatballs', 7.99, 1, 3),
(51, 'Spaghetti Puttanesca', NOW(), NOW(), 'Mì Ý sốt cà chua, ô liu, nụ bạch hoa, cá cơm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Puttanesca', 7.99, 1, 3),
(52, 'Spaghetti Arrabbiata', NOW(), NOW(), 'Mì Ý sốt cà chua cay nồng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Arrabbiata', 6.49, 1, 3),
(53, 'Spaghetti Vẹm Xanh Sốt Rượu Vang', NOW(), NOW(), 'Mì Ý xào vẹm xanh, tỏi, rượu vang trắng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Mussels', 9.99, 1, 3),
(54, 'Spaghetti Cá Hồi Sốt Kem', NOW(), NOW(), 'Mì Ý sốt kem với phi lê cá hồi áp chảo.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Salmon+Cream', 10.99, 1, 3),
(55, 'Spaghetti Bò Lúc Lắc', NOW(), NOW(), 'Mì Ý xào với thịt bò thái hạt lựu kiểu Việt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Bo+Luc+Lac', 8.99, 1, 3),
(56, 'Spaghetti Đút Lò Phô Mai', NOW(), NOW(), 'Mì Ý Bolognese phủ phô mai đút lò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Baked', 8.49, 1, 3),
(57, 'Spaghetti Sốt Cà Ri Gà', NOW(), NOW(), 'Mì Ý sốt cà ri kiểu Nhật với thịt gà.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Curry+Chicken', 7.99, 1, 3),
(58, 'Spaghetti Rau Củ Sốt Kem', NOW(), NOW(), 'Mì Ý sốt kem với bông cải xanh, cà rốt, nấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Veggie+Cream', 6.99, 1, 3),
(59, 'Spaghetti Trứng Muối Tôm', NOW(), NOW(), 'Mì Ý sốt kem trứng muối với tôm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Salted+Egg+Shrimp', 9.99, 1, 3),
(60, 'Spaghetti Mực Đen', NOW(), NOW(), 'Mì Ý sốt mực đen đặc trưng của Ý.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Squid+Ink', 10.49, 1, 3),

-- Danh mục 4: FRIED CHICKEN (menu_category_id = 4)
(61, 'Gà Rán Giòn (1 Miếng)', NOW(), NOW(), '1 miếng gà giòn tan (chọn vị Original/Spicy).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+1pc', 2.99, 1, 4),
(62, 'Gà Rán Giòn (2 Miếng)', NOW(), NOW(), '2 miếng gà giòn tan (chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+2pcs', 5.79, 1, 4),
(63, 'Gà Rán Giòn (3 Miếng)', NOW(), NOW(), '3 miếng gà giòn tan (chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+3pcs', 8.49, 1, 4),
(64, 'Combo Gà Rán 2 Miếng', NOW(), NOW(), '2 miếng gà, 1 Khoai tây (vừa), 1 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Chicken+2pcs', 8.99, 1, 4),
(65, 'Combo Gà Rán 3 Miếng', NOW(), NOW(), '3 miếng gà, 1 Khoai tây (vừa), 1 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Chicken+3pcs', 11.49, 1, 4),
(66, 'Xô Gà Rán (6 Miếng)', NOW(), NOW(), '6 miếng gà giòn (chọn vị hoặc mix).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+6pcs', 15.99, 1, 4),
(67, 'Xô Gà Rán (9 Miếng)', NOW(), NOW(), '9 miếng gà giòn (chọn vị hoặc mix).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+9pcs', 22.99, 1, 4),
(68, 'Xô Gà Gia Đình', NOW(), NOW(), '8 miếng gà, 2 Khoai tây (lớn), 1 Salad bắp cải, 4 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+Family', 29.99, 1, 4),
(69, 'Cánh Gà Rán (3 cái)', NOW(), NOW(), '3 cánh gà chiên giòn (chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Wings+3pcs', 4.49, 1, 4),
(70, 'Cánh Gà Rán (6 cái)', NOW(), NOW(), '6 cánh gà chiên giòn (chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Wings+6pcs', 8.49, 1, 4),
(71, 'Đùi Gà Rán (1 cái)', NOW(), NOW(), '1 đùi gà chiên giòn (chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Drumstick', 3.19, 1, 4),
(72, 'Gà Rán Không Xương (Popcorn)', NOW(), NOW(), 'Gà viên chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Popcorn', 4.99, 1, 4),
(73, 'Gà Rán Sốt Cay Ngọt Hàn Quốc (6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt cay ngọt kiểu Hàn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Korean+Wings', 9.49, 1, 4),
(74, 'Gà Rán Sốt BBQ (6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt BBQ đậm đà.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQ+Wings', 9.49, 1, 4),
(75, 'Gà Rán Sốt Phô Mai (6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt phô mai béo ngậy.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheese+Wings', 9.99, 1, 4),
(76, 'Combo Gà Popcorn', NOW(), NOW(), '1 Gà Popcorn (vừa), 1 Khoai tây (vừa), 1 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Popcorn', 8.49, 1, 4),
(77, 'Gà Rán Mật Ong (1 Miếng)', NOW(), NOW(), '1 miếng gà giòn phủ sốt mật ong.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Honey+Chicken', 3.29, 1, 4),
(78, 'Combo Cánh Gà', NOW(), NOW(), '5 cánh gà (chọn vị), 1 Khoai tây (vừa), 1 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Wings', 10.99, 1, 4),
(79, 'Ức Gà Rán Filet (1 miếng)', NOW(), NOW(), '1 miếng ức gà không xương chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Filet', 3.49, 1, 4),
(80, 'Xô Gà Tiệc Tùng', NOW(), NOW(), '12 miếng gà, 4 Khoai tây (vừa), 2 Gà Popcorn, 6 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Party+Bucket', 45.99, 1, 4),

-- Danh mục 5: SIDES (menu_category_id = 5)
(81, 'Khoai Tây Chiên', NOW(), NOW(), 'Khoai tây sợi chiên vàng giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fries', 1.99, 1, 5),
(82, 'Khoai Tây Chiên Phô Mai', NOW(), NOW(), 'Khoai tây chiên phủ sốt phô mai nóng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheese+Fries', 2.99, 1, 5),
(83, 'Khoai Tây Chiên BBQ', NOW(), NOW(), 'Khoai tây chiên rắc bột gia vị BBQ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQ+Fries', 2.49, 1, 5),
(84, 'Hành Tây Chiên Giòn (Onion Rings)', NOW(), NOW(), 'Khoanh hành tây tẩm bột chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Onion+Rings', 2.49, 1, 5),
(85, 'Phô Mai Que (4 que)', NOW(), NOW(), 'Phô mai Mozzarella chiên xù ăn kèm sốt cà chua.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mozzarella+Sticks', 3.49, 1, 5),
(86, 'Salad Bắp Cải Trộn', NOW(), NOW(), 'Bắp cải, cà rốt thái sợi trộn sốt mayonnaise.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coleslaw', 1.99, 1, 5),
(87, 'Khoai Tây Nghiền & Nước Sốt', NOW(), NOW(), 'Khoai tây nghiền mịn ăn kèm nước sốt thịt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mashed+Potato', 2.49, 1, 5),
(88, 'Bánh Mì Bơ Tỏi', NOW(), NOW(), 'Bánh mì nướng bơ tỏi thơm lừng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Garlic+Bread', 2.29, 1, 5),
(89, 'Bánh Mì Bơ Tỏi Phô Mai', NOW(), NOW(), 'Bánh mì bơ tỏi phủ phô mai Mozzarella đút lò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheesy+Garlic+Bread', 3.29, 1, 5),
(90, 'Ngô Ngọt Hấp Bơ', NOW(), NOW(), 'Hạt ngô ngọt hấp với bơ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Corn', 1.99, 1, 5),
(91, 'Nấm Chiên Giòn', NOW(), NOW(), 'Nấm tươi tẩm bột chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Mushrooms', 3.49, 1, 5),
(92, 'Khoai Tây Múi Cau', NOW(), NOW(), 'Khoai tây bổ múi cau chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Potato+Wedges', 2.49, 1, 5),
(93, 'Jalapeno Poppers (4 cái)', NOW(), NOW(), 'Ớt Jalapeno nhồi phô mai chiên xù.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Jalapeno+Poppers', 3.99, 1, 5),
(94, 'Bánh Xếp Chiên (Fried Dumplings - 4 cái)', NOW(), NOW(), 'Bánh xếp nhân thịt chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Dumplings', 3.49, 1, 5),
(95, 'Chả Giò Mini (4 cuốn)', NOW(), NOW(), 'Chả giò nhỏ chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mini+Spring+Rolls', 3.29, 1, 5),
(96, 'Xúc Xích Đức Nướng', NOW(), NOW(), 'Xúc xích Đức nướng ăn kèm mù tạt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Grilled+Sausage', 2.99, 1, 5),
(97, 'Súp Kem Nấm', NOW(), NOW(), 'Súp kem nấm nóng hổi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mushroom+Soup', 2.99, 1, 5),
(98, 'Súp Cà Chua', NOW(), NOW(), 'Súp cà chua kiểu Ý.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Tomato+Soup', 2.79, 1, 5),
(99, 'Salad Trộn Dầu Giấm', NOW(), NOW(), 'Xà lách, cà chua, dưa leo, sốt dầu giấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Garden+Salad', 2.99, 1, 5),
(100, 'Combo Ăn Kèm', NOW(), NOW(), '1 Khoai tây (vừa), 1 Salad bắp cải, 1 Nước ngọt (vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Sides', 4.99, 1, 5),

-- Danh mục 6: DRINKS (menu_category_id = 6)
(101, 'Coca-Cola', NOW(), NOW(), 'Nước ngọt có gas Coca-Cola.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coca-Cola', 1.49, 1, 6),
(102, 'Coca-Cola Light / Zero', NOW(), NOW(), 'Nước ngọt Coca-Cola không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coke+Zero', 1.49, 1, 6),
(103, 'Pepsi', NOW(), NOW(), 'Nước ngọt có gas Pepsi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pepsi', 1.49, 1, 6),
(104, 'Pepsi Light / Zero', NOW(), NOW(), 'Nước ngọt Pepsi không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pepsi+Zero', 1.49, 1, 6),
(105, 'Sprite', NOW(), NOW(), 'Nước ngọt vị chanh Sprite.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Sprite', 1.49, 1, 6),
(106, '7 Up', NOW(), NOW(), 'Nước ngọt vị chanh 7 Up.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=7+Up', 1.49, 1, 6),
(107, 'Fanta Cam', NOW(), NOW(), 'Nước ngọt vị cam Fanta.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fanta+Orange', 1.49, 1, 6),
(108, 'Fanta Xá Xị', NOW(), NOW(), 'Nước ngọt vị xá xị Fanta.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fanta+Sarsi', 1.49, 1, 6),
(109, 'Mirinda Kem Soda', NOW(), NOW(), 'Nước ngọt vị kem soda Mirinda.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mirinda+Soda+Cream', 1.49, 1, 6),
(110, 'Nước Suối Aquafina', NOW(), NOW(), 'Nước tinh khiết đóng chai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Aquafina', 0.99, 1, 6),
(111, 'Nước Suối Dasani', NOW(), NOW(), 'Nước tinh khiết đóng chai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Dasani', 0.99, 1, 6),
(112, 'Trà Chanh Lipton', NOW(), NOW(), 'Trà chanh đóng chai Lipton.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lipton+Lemon', 1.79, 1, 6),
(113, 'Trà Đào Lipton', NOW(), NOW(), 'Trà đào đóng chai Lipton.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lipton+Peach', 1.79, 1, 6),
(114, 'Trà Đá Pha Sẵn', NOW(), NOW(), 'Trà đen pha sẵn không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Iced+Tea', 1.29, 1, 6),
(115, 'Nước Cam Ép', NOW(), NOW(), 'Nước cam tươi ép.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Orange+Juice', 2.49, 1, 6),
(116, 'Nước Chanh Tươi', NOW(), NOW(), 'Nước chanh tươi pha.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lemonade', 1.99, 1, 6),
(117, 'Cà Phê Đen Đá', NOW(), NOW(), 'Cà phê phin đen đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Black+Coffee', 1.99, 1, 6),
(118, 'Cà Phê Sữa Đá', NOW(), NOW(), 'Cà phê phin sữa đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Milk+Coffee', 2.29, 1, 6),
(119, 'Milo Đá', NOW(), NOW(), 'Thức uống lúa mạch Milo pha đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Milo', 1.99, 1, 6),
(120, 'Nước Ngọt Ly Lớn (Refill)', NOW(), NOW(), 'Ly lớn tự chọn nước ngọt và có thể refill.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Refill+Drink', 2.49, 1, 6);

-- menu_item_option
INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Pizza ID 1
(1, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (1, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(1, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (1, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (1, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(1, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (1, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (1, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(1, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(1, 7, 'Tương cà', 0.30, NOW(), NOW()), (1, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (1, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 2
(2, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (2, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(2, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (2, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (2, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(2, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (2, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (2, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(2, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(2, 7, 'Tương cà', 0.30, NOW(), NOW()), (2, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (2, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 3
(3, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (3, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(3, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (3, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (3, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(3, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (3, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (3, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(3, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(3, 7, 'Tương cà', 0.30, NOW(), NOW()), (3, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (3, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 4
(4, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (4, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(4, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (4, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (4, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(4, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (4, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (4, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(4, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(4, 7, 'Tương cà', 0.30, NOW(), NOW()), (4, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (4, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 5
(5, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (5, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(5, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (5, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (5, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(5, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (5, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (5, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(5, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(5, 7, 'Tương cà', 0.30, NOW(), NOW()), (5, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (5, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 6
(6, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (6, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(6, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (6, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (6, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(6, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (6, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (6, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(6, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(6, 7, 'Tương cà', 0.30, NOW(), NOW()), (6, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (6, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 7
(7, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (7, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(7, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (7, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (7, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(7, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (7, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (7, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(7, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(7, 7, 'Tương cà', 0.30, NOW(), NOW()), (7, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (7, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 8
(8, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (8, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(8, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (8, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (8, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(8, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (8, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (8, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(8, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(8, 7, 'Tương cà', 0.30, NOW(), NOW()), (8, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (8, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 9
(9, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (9, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(9, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (9, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (9, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(9, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (9, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (9, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(9, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(9, 7, 'Tương cà', 0.30, NOW(), NOW()), (9, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (9, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 10
(10, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (10, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(10, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (10, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (10, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(10, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (10, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (10, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(10, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(10, 7, 'Tương cà', 0.30, NOW(), NOW()), (10, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (10, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 11
(11, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (11, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(11, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (11, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (11, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(11, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (11, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (11, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(11, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(11, 7, 'Tương cà', 0.30, NOW(), NOW()), (11, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (11, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 12
(12, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (12, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(12, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (12, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (12, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(12, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (12, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (12, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(12, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(12, 7, 'Tương cà', 0.30, NOW(), NOW()), (12, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (12, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 13
(13, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (13, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(13, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (13, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (13, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(13, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (13, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (13, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(13, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(13, 7, 'Tương cà', 0.30, NOW(), NOW()), (13, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (13, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 14
(14, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (14, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(14, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (14, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (14, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(14, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (14, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (14, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(14, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(14, 7, 'Tương cà', 0.30, NOW(), NOW()), (14, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (14, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 15
(15, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (15, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(15, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (15, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (15, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(15, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (15, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (15, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(15, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(15, 7, 'Tương cà', 0.30, NOW(), NOW()), (15, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (15, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 16
(16, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (16, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(16, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (16, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (16, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(16, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (16, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (16, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(16, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(16, 7, 'Tương cà', 0.30, NOW(), NOW()), (16, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (16, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 17
(17, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (17, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(17, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (17, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (17, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(17, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (17, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (17, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(17, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(17, 7, 'Tương cà', 0.30, NOW(), NOW()), (17, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (17, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 18
(18, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (18, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(18, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (18, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (18, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(18, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (18, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (18, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(18, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(18, 7, 'Tương cà', 0.30, NOW(), NOW()), (18, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (18, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 19
(19, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (19, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(19, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (19, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (19, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(19, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (19, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (19, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(19, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(19, 7, 'Tương cà', 0.30, NOW(), NOW()), (19, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (19, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Pizza ID 20
(20, 1, 'Vừa (M)', 0.00, NOW(), NOW()), (20, 1, 'Lớn (L)', 2.00, NOW(), NOW()),
(20, 2, 'Đế dày xốp', 0.00, NOW(), NOW()), (20, 2, 'Đế mỏng giòn', 0.00, NOW(), NOW()), (20, 2, 'Đế viền phô mai', 1.50, NOW(), NOW()),
(20, 3, 'Thêm Nấm', 0.75, NOW(), NOW()), (20, 3, 'Thêm Pepperoni', 1.25, NOW(), NOW()), (20, 3, 'Thêm Ô liu', 0.60, NOW(), NOW()),
(20, 5, 'Nhân đôi phô mai', 1.50, NOW(), NOW()),
(20, 7, 'Tương cà', 0.30, NOW(), NOW()), (20, 7, 'Sốt Ranch', 0.50, NOW(), NOW()), (20, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW());

INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Burger ID 21: Classic Beef Burger
(21, 4, 'Không cay', 0.00, NOW(), NOW()), (21, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (21, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(21, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (21, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (21, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(21, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (21, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (21, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(21, 7, 'Tương cà', 0.30, NOW(), NOW()), (21, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (21, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 22: Cheeseburger
(22, 4, 'Không cay', 0.00, NOW(), NOW()), (22, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (22, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(22, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (22, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (22, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(22, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (22, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (22, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(22, 7, 'Tương cà', 0.30, NOW(), NOW()), (22, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (22, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 23: Double Cheeseburger
(23, 4, 'Không cay', 0.00, NOW(), NOW()), (23, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (23, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(23, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (23, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (23, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(23, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (23, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (23, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(23, 7, 'Tương cà', 0.30, NOW(), NOW()), (23, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (23, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 24: Bacon Cheeseburger
(24, 4, 'Không cay', 0.00, NOW(), NOW()), (24, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (24, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(24, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (24, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (24, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(24, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (24, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (24, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(24, 7, 'Tương cà', 0.30, NOW(), NOW()), (24, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (24, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 25: Mushroom Swiss Burger
(25, 4, 'Không cay', 0.00, NOW(), NOW()), (25, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (25, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(25, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (25, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (25, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(25, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (25, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (25, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(25, 7, 'Tương cà', 0.30, NOW(), NOW()), (25, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (25, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 26: Crispy Chicken Burger
(26, 4, 'Không cay', 0.00, NOW(), NOW()), (26, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (26, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(26, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (26, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (26, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(26, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (26, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (26, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(26, 7, 'Tương cà', 0.30, NOW(), NOW()), (26, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (26, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 27: Spicy Chicken Burger
(27, 4, 'Không cay', 0.00, NOW(), NOW()), (27, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (27, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(27, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (27, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (27, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(27, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (27, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (27, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(27, 7, 'Tương cà', 0.30, NOW(), NOW()), (27, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (27, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 28: Grilled Chicken Burger
(28, 4, 'Không cay', 0.00, NOW(), NOW()), (28, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (28, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(28, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (28, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (28, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(28, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (28, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (28, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(28, 7, 'Tương cà', 0.30, NOW(), NOW()), (28, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (28, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 29: Fish Burger
(29, 4, 'Không cay', 0.00, NOW(), NOW()), (29, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (29, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(29, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (29, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (29, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(29, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (29, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (29, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(29, 7, 'Tương cà', 0.30, NOW(), NOW()), (29, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (29, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 30: Veggie Burger
(30, 4, 'Không cay', 0.00, NOW(), NOW()), (30, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (30, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(30, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (30, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (30, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
-- (30, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), -- Không hợp lý cho Veggie Burger
-- (30, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), -- Không hợp lý cho Veggie Burger
(30, 6, 'Thêm Thịt Xông Khói Chay (nếu có)', 1.50, NOW(), NOW()), -- Ví dụ tùy chọn khác biệt
(30, 6, 'Thêm Pattie Rau Củ', 1.50, NOW(), NOW()), -- Thêm pattie rau củ
(30, 6, 'Thêm Đậu phụ chiên', 1.00, NOW(), NOW()), -- Tùy chọn thay thế cho thịt
(30, 7, 'Tương cà', 0.30, NOW(), NOW()), (30, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (30, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 31: BBQ Bacon Burger
(31, 4, 'Không cay', 0.00, NOW(), NOW()), (31, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (31, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(31, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (31, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (31, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(31, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (31, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (31, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(31, 7, 'Tương cà', 0.30, NOW(), NOW()), (31, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (31, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 32: Jalapeno Burger
(32, 4, 'Không cay', 0.00, NOW(), NOW()), (32, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (32, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(32, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (32, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (32, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(32, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (32, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (32, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(32, 7, 'Tương cà', 0.30, NOW(), NOW()), (32, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (32, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 33: Blue Cheese Burger
(33, 4, 'Không cay', 0.00, NOW(), NOW()), (33, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (33, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(33, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (33, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (33, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(33, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (33, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (33, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(33, 7, 'Tương cà', 0.30, NOW(), NOW()), (33, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (33, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 34: Teriyaki Burger
(34, 4, 'Không cay', 0.00, NOW(), NOW()), (34, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (34, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(34, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (34, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (34, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(34, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (34, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (34, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(34, 7, 'Tương cà', 0.30, NOW(), NOW()), (34, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (34, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 35: Breakfast Burger
(35, 4, 'Không cay', 0.00, NOW(), NOW()), (35, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (35, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(35, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (35, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (35, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(35, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (35, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (35, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(35, 7, 'Tương cà', 0.30, NOW(), NOW()), (35, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (35, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 36: Slider Trio (Mini Burgers) - Tùy chọn có thể khác? Áp dụng chung cho cả bộ?
(36, 4, 'Không cay', 0.00, NOW(), NOW()), (36, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (36, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(36, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (36, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (36, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(36, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (36, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (36, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(36, 7, 'Tương cà', 0.30, NOW(), NOW()), (36, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (36, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 37: Tower Burger
(37, 4, 'Không cay', 0.00, NOW(), NOW()), (37, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (37, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(37, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (37, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (37, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(37, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (37, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (37, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(37, 7, 'Tương cà', 0.30, NOW(), NOW()), (37, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (37, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 38: Hawaiian Chicken Burger
(38, 4, 'Không cay', 0.00, NOW(), NOW()), (38, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (38, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(38, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (38, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (38, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(38, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (38, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (38, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(38, 7, 'Tương cà', 0.30, NOW(), NOW()), (38, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (38, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 39: Avocado Burger
(39, 4, 'Không cay', 0.00, NOW(), NOW()), (39, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (39, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(39, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (39, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (39, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(39, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (39, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (39, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(39, 7, 'Tương cà', 0.30, NOW(), NOW()), (39, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (39, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Burger ID 40: Burger Gà Sốt Nấm
(40, 4, 'Không cay', 0.00, NOW(), NOW()), (40, 4, 'Cay vừa (sốt cay)', 0.30, NOW(), NOW()), (40, 4, 'Thêm ớt Jalapeno', 0.50, NOW(), NOW()),
(40, 5, 'Thêm 1 lát phô mai American', 0.50, NOW(), NOW()), (40, 5, 'Thêm 1 lát phô mai Swiss', 0.60, NOW(), NOW()), (40, 5, 'Thêm 1 lát phô mai Pepper Jack', 0.60, NOW(), NOW()),
(40, 6, 'Thêm 1 miếng Bò', 2.00, NOW(), NOW()), (40, 6, 'Thêm 1 miếng Gà', 1.80, NOW(), NOW()), (40, 6, 'Thêm Thịt Xông Khói (2 lát)', 1.25, NOW(), NOW()),
(40, 7, 'Tương cà', 0.30, NOW(), NOW()), (40, 7, 'Sốt BBQ', 0.50, NOW(), NOW()), (40, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW());

INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Spaghetti ID 41: Spaghetti Bolognese
(41, 4, 'Không cay', 0.00, NOW(), NOW()), (41, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (41, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(41, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (41, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (41, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(41, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (41, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (41, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 42: Spaghetti Carbonara
(42, 4, 'Không cay', 0.00, NOW(), NOW()), (42, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (42, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(42, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (42, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (42, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(42, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (42, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (42, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 43: Spaghetti Sốt Kem Gà Nấm
(43, 4, 'Không cay', 0.00, NOW(), NOW()), (43, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (43, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(43, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (43, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (43, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(43, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (43, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (43, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 44: Spaghetti Hải Sản Sốt Cà Chua
(44, 4, 'Không cay', 0.00, NOW(), NOW()), (44, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (44, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(44, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (44, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (44, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(44, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (44, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (44, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 45: Spaghetti Sốt Kem Hải Sản
(45, 4, 'Không cay', 0.00, NOW(), NOW()), (45, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (45, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(45, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (45, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (45, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(45, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (45, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (45, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 46: Spaghetti Aglio e Olio
(46, 4, 'Không cay', 0.00, NOW(), NOW()), (46, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (46, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(46, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (46, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (46, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(46, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (46, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (46, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 47: Spaghetti Aglio e Olio Tôm
(47, 4, 'Không cay', 0.00, NOW(), NOW()), (47, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (47, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(47, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (47, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (47, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(47, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (47, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (47, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 48: Spaghetti Sốt Pesto
(48, 4, 'Không cay', 0.00, NOW(), NOW()), (48, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (48, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(48, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (48, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (48, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(48, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (48, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (48, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 49: Spaghetti Sốt Pesto Gà
(49, 4, 'Không cay', 0.00, NOW(), NOW()), (49, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (49, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(49, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (49, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (49, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(49, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (49, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (49, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 50: Spaghetti Meatballs
(50, 4, 'Không cay', 0.00, NOW(), NOW()), (50, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (50, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(50, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (50, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (50, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(50, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (50, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (50, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 51: Spaghetti Puttanesca
(51, 4, 'Không cay', 0.00, NOW(), NOW()), (51, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (51, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(51, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (51, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (51, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(51, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (51, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (51, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 52: Spaghetti Arrabbiata
(52, 4, 'Không cay', 0.00, NOW(), NOW()), (52, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (52, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(52, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (52, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (52, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(52, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (52, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (52, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 53: Spaghetti Vẹm Xanh Sốt Rượu Vang
(53, 4, 'Không cay', 0.00, NOW(), NOW()), (53, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (53, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(53, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (53, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (53, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(53, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (53, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (53, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 54: Spaghetti Cá Hồi Sốt Kem
(54, 4, 'Không cay', 0.00, NOW(), NOW()), (54, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (54, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(54, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (54, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (54, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(54, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (54, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (54, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 55: Spaghetti Bò Lúc Lắc
(55, 4, 'Không cay', 0.00, NOW(), NOW()), (55, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (55, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(55, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (55, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (55, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(55, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (55, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (55, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 56: Spaghetti Đút Lò Phô Mai
(56, 4, 'Không cay', 0.00, NOW(), NOW()), (56, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (56, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(56, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (56, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (56, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(56, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (56, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (56, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 57: Spaghetti Sốt Cà Ri Gà
(57, 4, 'Không cay', 0.00, NOW(), NOW()), (57, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (57, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(57, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (57, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (57, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(57, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (57, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (57, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 58: Spaghetti Rau Củ Sốt Kem
(58, 4, 'Không cay', 0.00, NOW(), NOW()), (58, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (58, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(58, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (58, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (58, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(58, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (58, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (58, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 59: Spaghetti Trứng Muối Tôm
(59, 4, 'Không cay', 0.00, NOW(), NOW()), (59, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (59, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(59, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (59, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (59, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(59, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (59, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (59, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW()),

-- Spaghetti ID 60: Spaghetti Mực Đen
(60, 4, 'Không cay', 0.00, NOW(), NOW()), (60, 4, 'Cay vừa (thêm ớt bột)', 0.25, NOW(), NOW()), (60, 4, 'Cay nhiều (thêm ớt tươi)', 0.50, NOW(), NOW()),
(60, 5, 'Tiêu chuẩn (có Parmesan)', 0.00, NOW(), NOW()), (60, 5, 'Thêm Phô mai Parmesan', 0.75, NOW(), NOW()), (60, 5, 'Gấp đôi Phô mai Parmesan', 1.25, NOW(), NOW()),
(60, 6, 'Thêm Thịt viên (Meatballs)', 2.00, NOW(), NOW()), (60, 6, 'Thêm Gà nướng', 1.80, NOW(), NOW()), (60, 6, 'Thêm Tôm (3 con)', 2.50, NOW(), NOW());

INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Gà rán ID 61: Gà Rán Giòn (1 Miếng)
(61, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (61, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (61, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(61, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (61, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (61, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(61, 7, 'Tương ớt', 0.30, NOW(), NOW()), (61, 7, 'Tương cà', 0.30, NOW(), NOW()), (61, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 62: Gà Rán Giòn (2 Miếng)
(62, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (62, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (62, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(62, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (62, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (62, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(62, 7, 'Tương ớt', 0.30, NOW(), NOW()), (62, 7, 'Tương cà', 0.30, NOW(), NOW()), (62, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 63: Gà Rán Giòn (3 Miếng)
(63, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (63, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (63, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(63, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (63, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (63, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(63, 7, 'Tương ớt', 0.30, NOW(), NOW()), (63, 7, 'Tương cà', 0.30, NOW(), NOW()), (63, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 64: Combo Gà Rán 2 Miếng
(64, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (64, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (64, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(64, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (64, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (64, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(64, 7, 'Tương ớt', 0.30, NOW(), NOW()), (64, 7, 'Tương cà', 0.30, NOW(), NOW()), (64, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 65: Combo Gà Rán 3 Miếng
(65, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (65, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (65, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(65, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (65, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (65, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(65, 7, 'Tương ớt', 0.30, NOW(), NOW()), (65, 7, 'Tương cà', 0.30, NOW(), NOW()), (65, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 66: Xô Gà Rán (6 Miếng)
(66, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (66, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (66, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(66, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (66, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (66, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(66, 7, 'Tương ớt', 0.30, NOW(), NOW()), (66, 7, 'Tương cà', 0.30, NOW(), NOW()), (66, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 67: Xô Gà Rán (9 Miếng)
(67, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (67, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (67, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(67, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (67, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (67, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(67, 7, 'Tương ớt', 0.30, NOW(), NOW()), (67, 7, 'Tương cà', 0.30, NOW(), NOW()), (67, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 68: Xô Gà Gia Đình
(68, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (68, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (68, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(68, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (68, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (68, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(68, 7, 'Tương ớt', 0.30, NOW(), NOW()), (68, 7, 'Tương cà', 0.30, NOW(), NOW()), (68, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 69: Cánh Gà Rán (3 cái)
(69, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (69, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (69, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(69, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (69, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (69, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(69, 7, 'Tương ớt', 0.30, NOW(), NOW()), (69, 7, 'Tương cà', 0.30, NOW(), NOW()), (69, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 70: Cánh Gà Rán (6 cái)
(70, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (70, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (70, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(70, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (70, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (70, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(70, 7, 'Tương ớt', 0.30, NOW(), NOW()), (70, 7, 'Tương cà', 0.30, NOW(), NOW()), (70, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 71: Đùi Gà Rán (1 cái)
(71, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (71, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (71, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(71, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (71, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (71, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(71, 7, 'Tương ớt', 0.30, NOW(), NOW()), (71, 7, 'Tương cà', 0.30, NOW(), NOW()), (71, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 72: Gà Rán Không Xương (Popcorn)
(72, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (72, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (72, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(72, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (72, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (72, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(72, 7, 'Tương ớt', 0.30, NOW(), NOW()), (72, 7, 'Tương cà', 0.30, NOW(), NOW()), (72, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 73: Gà Rán Sốt Cay Ngọt Hàn Quốc (6 cánh) - Đã có sốt sẵn
(73, 4, 'Vị Cay Ngọt HQ (Mặc định)', 0.00, NOW(), NOW()), (73, 4, 'Cay hơn (Thêm ớt bột HQ)', 0.25, NOW(), NOW()), (73, 4, 'Giảm cay', -0.00, NOW(), NOW()), -- Có thể không giảm được
(73, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (73, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (73, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(73, 7, 'Tương ớt', 0.30, NOW(), NOW()), (73, 7, 'Tương cà', 0.30, NOW(), NOW()), (73, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 74: Gà Rán Sốt BBQ (6 cánh) - Đã có sốt sẵn
(74, 4, 'Vị BBQ (Mặc định)', 0.00, NOW(), NOW()), (74, 4, 'Vị BBQ Cay', 0.25, NOW(), NOW()), (74, 4, 'Thêm Sốt BBQ', 0.40, NOW(), NOW()),
(74, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (74, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (74, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(74, 7, 'Tương ớt', 0.30, NOW(), NOW()), (74, 7, 'Tương cà', 0.30, NOW(), NOW()), (74, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 75: Gà Rán Sốt Phô Mai (6 cánh) - Đã có sốt sẵn
(75, 4, 'Vị Truyền Thống (Gà)', 0.00, NOW(), NOW()), (75, 4, 'Vị Cay (Gà)', 0.00, NOW(), NOW()), (75, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(75, 5, 'Sốt Phô Mai (Mặc định)', 0.00, NOW(), NOW()), (75, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (75, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(75, 7, 'Tương ớt', 0.30, NOW(), NOW()), (75, 7, 'Tương cà', 0.30, NOW(), NOW()), (75, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 76: Combo Gà Popcorn
(76, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (76, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (76, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(76, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (76, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (76, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(76, 7, 'Tương ớt', 0.30, NOW(), NOW()), (76, 7, 'Tương cà', 0.30, NOW(), NOW()), (76, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 77: Gà Rán Mật Ong (1 Miếng) - Đã có sốt sẵn
(77, 4, 'Vị Mật Ong (Mặc định)', 0.00, NOW(), NOW()), (77, 4, 'Thêm sốt cay (pha mật ong cay)', 0.30, NOW(), NOW()), (77, 4, 'Thêm Mật Ong', 0.40, NOW(), NOW()),
(77, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (77, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (77, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(77, 7, 'Tương ớt', 0.30, NOW(), NOW()), (77, 7, 'Tương cà', 0.30, NOW(), NOW()), (77, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 78: Combo Cánh Gà
(78, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (78, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (78, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(78, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (78, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (78, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(78, 7, 'Tương ớt', 0.30, NOW(), NOW()), (78, 7, 'Tương cà', 0.30, NOW(), NOW()), (78, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 79: Ức Gà Rán Filet (1 miếng)
(79, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (79, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (79, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(79, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (79, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (79, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(79, 7, 'Tương ớt', 0.30, NOW(), NOW()), (79, 7, 'Tương cà', 0.30, NOW(), NOW()), (79, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()),

-- Gà rán ID 80: Xô Gà Tiệc Tùng
(80, 4, 'Vị Truyền Thống (Original)', 0.00, NOW(), NOW()), (80, 4, 'Vị Cay (Spicy)', 0.00, NOW(), NOW()), (80, 4, 'Yêu cầu thêm sốt cay bên ngoài', 0.30, NOW(), NOW()),
(80, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (80, 5, 'Thêm Sốt Phô Mai Cheddar', 0.75, NOW(), NOW()), (80, 5, 'Rắc Bột Phô Mai', 0.50, NOW(), NOW()),
(80, 7, 'Tương ớt', 0.30, NOW(), NOW()), (80, 7, 'Tương cà', 0.30, NOW(), NOW()), (80, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW());

INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- ID 81: Khoai Tây Chiên
(81, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (81, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (81, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(81, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (81, 5, 'Thêm Sốt Phô Mai', 1.00, NOW(), NOW()), (81, 5, 'Rắc bột phô mai', 0.60, NOW(), NOW()),
(81, 7, 'Tương cà', 0.30, NOW(), NOW()), (81, 7, 'Tương ớt', 0.30, NOW(), NOW()), (81, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (81, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 82: Khoai Tây Chiên Phô Mai (Đã có phô mai, chỉ thêm sốt và size)
(82, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (82, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (82, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(82, 7, 'Tương cà', 0.30, NOW(), NOW()), (82, 7, 'Tương ớt', 0.30, NOW(), NOW()), (82, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (82, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 83: Khoai Tây Chiên BBQ
(83, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (83, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (83, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(83, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (83, 5, 'Thêm Sốt Phô Mai', 1.00, NOW(), NOW()), (83, 5, 'Rắc bột phô mai', 0.60, NOW(), NOW()),
(83, 7, 'Tương cà', 0.30, NOW(), NOW()), (83, 7, 'Tương ớt', 0.30, NOW(), NOW()), (83, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (83, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 84: Hành Tây Chiên Giòn (Onion Rings)
(84, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (84, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (84, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(84, 7, 'Tương cà', 0.30, NOW(), NOW()), (84, 7, 'Tương ớt', 0.30, NOW(), NOW()), (84, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (84, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 85: Phô Mai Que (Số lượng cố định, chỉ thêm sốt)
(85, 7, 'Tương cà', 0.30, NOW(), NOW()), (85, 7, 'Tương ớt', 0.30, NOW(), NOW()), (85, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (85, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 86: Salad Bắp Cải Trộn
(86, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (86, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (86, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),

-- ID 87: Khoai Tây Nghiền & Nước Sốt
(87, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (87, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (87, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(87, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (87, 5, 'Thêm Sốt Phô Mai', 1.00, NOW(), NOW()), (87, 5, 'Rắc bột phô mai', 0.60, NOW(), NOW()),

-- ID 88: Bánh Mì Bơ Tỏi (Size thường cố định)
(88, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (88, 5, 'Thêm Sốt Phô Mai', 1.00, NOW(), NOW()), (88, 5, 'Rắc bột phô mai', 0.60, NOW(), NOW()),

-- ID 89: Bánh Mì Bơ Tỏi Phô Mai (Đã có phô mai)
-- Không thêm tùy chọn nào ở đây cho đơn giản

-- ID 90: Ngô Ngọt Hấp Bơ (Kích cỡ có thể không áp dụng)
-- Không thêm tùy chọn nào ở đây cho đơn giản

-- ID 91: Nấm Chiên Giòn
(91, 7, 'Tương cà', 0.30, NOW(), NOW()), (91, 7, 'Tương ớt', 0.30, NOW(), NOW()), (91, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (91, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 92: Khoai Tây Múi Cau
(92, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (92, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (92, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW()),
(92, 5, 'Không thêm phô mai', 0.00, NOW(), NOW()), (92, 5, 'Thêm Sốt Phô Mai', 1.00, NOW(), NOW()), (92, 5, 'Rắc bột phô mai', 0.60, NOW(), NOW()),
(92, 7, 'Tương cà', 0.30, NOW(), NOW()), (92, 7, 'Tương ớt', 0.30, NOW(), NOW()), (92, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (92, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 93: Jalapeno Poppers (Số lượng cố định, chỉ thêm sốt)
(93, 7, 'Tương cà', 0.30, NOW(), NOW()), (93, 7, 'Tương ớt', 0.30, NOW(), NOW()), (93, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (93, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 94: Bánh Xếp Chiên (Số lượng cố định, chỉ thêm sốt)
(94, 7, 'Tương cà', 0.30, NOW(), NOW()), (94, 7, 'Tương ớt', 0.30, NOW(), NOW()), (94, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (94, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 95: Chả Giò Mini (Số lượng cố định, chỉ thêm sốt)
(95, 7, 'Tương cà', 0.30, NOW(), NOW()), (95, 7, 'Tương ớt', 0.30, NOW(), NOW()), (95, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (95, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 96: Xúc Xích Đức Nướng
(96, 7, 'Tương cà', 0.30, NOW(), NOW()), (96, 7, 'Tương ớt', 0.30, NOW(), NOW()), (96, 7, 'Sốt Mayonnaise Tỏi', 0.50, NOW(), NOW()), (96, 7, 'Sốt BBQ', 0.50, NOW(), NOW()),

-- ID 97: Súp Kem Nấm
-- Không thêm tùy chọn nào ở đây cho đơn giản

-- ID 98: Súp Cà Chua
-- Không thêm tùy chọn nào ở đây cho đơn giản

-- ID 99: Salad Trộn Dầu Giấm
(99, 1, 'Vừa (Regular)', 0.00, NOW(), NOW()), (99, 1, 'Lớn (Large)', 1.00, NOW(), NOW()), (99, 1, 'Siêu lớn (XL)', 1.75, NOW(), NOW());

-- ID 100: Combo Ăn Kèm
-- Không thêm tùy chọn cho bản thân combo, tùy chọn áp dụng cho món bên trong (nếu cần)

INSERT INTO tbl_menu_item_option (menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Áp dụng Kích cỡ (ID 1) và Mức độ đá (ID 8) cho TẤT CẢ đồ uống (ID 101-120)
-- Coca-Cola (ID 101)
(101, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (101, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(101, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (101, 8, 'Ít Đá', 0.00, NOW(), NOW()), (101, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Coca-Cola Light / Zero (ID 102)
(102, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (102, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(102, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (102, 8, 'Ít Đá', 0.00, NOW(), NOW()), (102, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Pepsi (ID 103)
(103, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (103, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(103, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (103, 8, 'Ít Đá', 0.00, NOW(), NOW()), (103, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Pepsi Light / Zero (ID 104)
(104, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (104, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(104, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (104, 8, 'Ít Đá', 0.00, NOW(), NOW()), (104, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Sprite (ID 105)
(105, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (105, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(105, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (105, 8, 'Ít Đá', 0.00, NOW(), NOW()), (105, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- 7 Up (ID 106)
(106, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (106, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(106, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (106, 8, 'Ít Đá', 0.00, NOW(), NOW()), (106, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Fanta Cam (ID 107)
(107, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (107, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(107, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (107, 8, 'Ít Đá', 0.00, NOW(), NOW()), (107, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Fanta Xá Xị (ID 108)
(108, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (108, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(108, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (108, 8, 'Ít Đá', 0.00, NOW(), NOW()), (108, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Mirinda Kem Soda (ID 109)
(109, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (109, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(109, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (109, 8, 'Ít Đá', 0.00, NOW(), NOW()), (109, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Nước Suối Aquafina (ID 110) - Kích cỡ/đá có thể không hợp lý lắm, nhưng để nhất quán
(110, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (110, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(110, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (110, 8, 'Ít Đá', 0.00, NOW(), NOW()), (110, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Nước Suối Dasani (ID 111) - Kích cỡ/đá có thể không hợp lý lắm, nhưng để nhất quán
(111, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (111, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(111, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (111, 8, 'Ít Đá', 0.00, NOW(), NOW()), (111, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Trà Chanh Lipton (ID 112)
(112, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (112, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(112, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (112, 8, 'Ít Đá', 0.00, NOW(), NOW()), (112, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Trà Đào Lipton (ID 113)
(113, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (113, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(113, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (113, 8, 'Ít Đá', 0.00, NOW(), NOW()), (113, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Trà Đá Pha Sẵn (ID 114)
(114, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (114, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(114, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (114, 8, 'Ít Đá', 0.00, NOW(), NOW()), (114, 8, 'Không Đá', 0.00, NOW(), NOW()),
(114, 9, '100% Đường (Ngọt Vừa)', 0.00, NOW(), NOW()), (114, 9, '70% Đường (Ít Ngọt)', 0.00, NOW(), NOW()), (114, 9, '50% Đường (Ngọt Ít)', 0.00, NOW(), NOW()), (114, 9, 'Không Đường', 0.00, NOW(), NOW()),
(114, 10, 'Không Topping', 0.00, NOW(), NOW()), (114, 10, 'Thêm Trân châu', 0.70, NOW(), NOW()), (114, 10, 'Thêm Thạch Cà Phê', 0.70, NOW(), NOW()),
-- Nước Cam Ép (ID 115)
(115, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (115, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(115, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (115, 8, 'Ít Đá', 0.00, NOW(), NOW()), (115, 8, 'Không Đá', 0.00, NOW(), NOW()),
-- Nước Chanh Tươi (ID 116)
(116, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (116, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(116, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (116, 8, 'Ít Đá', 0.00, NOW(), NOW()), (116, 8, 'Không Đá', 0.00, NOW(), NOW()),
(116, 9, '100% Đường (Ngọt Vừa)', 0.00, NOW(), NOW()), (116, 9, '70% Đường (Ít Ngọt)', 0.00, NOW(), NOW()), (116, 9, '50% Đường (Ngọt Ít)', 0.00, NOW(), NOW()), (116, 9, 'Không Đường', 0.00, NOW(), NOW()),
-- Cà Phê Đen Đá (ID 117)
(117, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (117, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(117, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (117, 8, 'Ít Đá', 0.00, NOW(), NOW()), (117, 8, 'Không Đá', 0.00, NOW(), NOW()),
(117, 9, '100% Đường (Ngọt Vừa)', 0.00, NOW(), NOW()), (117, 9, '70% Đường (Ít Ngọt)', 0.00, NOW(), NOW()), (117, 9, '50% Đường (Ngọt Ít)', 0.00, NOW(), NOW()), (117, 9, 'Không Đường', 0.00, NOW(), NOW()),
-- Cà Phê Sữa Đá (ID 118)
(118, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (118, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(118, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (118, 8, 'Ít Đá', 0.00, NOW(), NOW()), (118, 8, 'Không Đá', 0.00, NOW(), NOW()),
(118, 9, '100% Đường (Ngọt Vừa)', 0.00, NOW(), NOW()), (118, 9, '70% Đường (Ít Ngọt)', 0.00, NOW(), NOW()), (118, 9, '50% Đường (Ngọt Ít)', 0.00, NOW(), NOW()), (118, 9, 'Không Đường', 0.00, NOW(), NOW()),
(118, 10, 'Không Topping', 0.00, NOW(), NOW()), (118, 10, 'Thêm Trân châu', 0.70, NOW(), NOW()), (118, 10, 'Thêm Thạch Cà Phê', 0.70, NOW(), NOW()),
-- Milo Đá (ID 119)
(119, 1, 'Ly Vừa (M)', 0.00, NOW(), NOW()), (119, 1, 'Ly Lớn (L)', 0.50, NOW(), NOW()),
(119, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (119, 8, 'Ít Đá', 0.00, NOW(), NOW()), (119, 8, 'Không Đá', 0.00, NOW(), NOW()),
(119, 9, '100% Đường (Ngọt Vừa)', 0.00, NOW(), NOW()), (119, 9, '70% Đường (Ít Ngọt)', 0.00, NOW(), NOW()), (119, 9, '50% Đường (Ngọt Ít)', 0.00, NOW(), NOW()), (119, 9, 'Không Đường', 0.00, NOW(), NOW()),
(119, 10, 'Không Topping', 0.00, NOW(), NOW()), (119, 10, 'Thêm Trân châu', 0.70, NOW(), NOW()), (119, 10, 'Thêm Thạch Cà Phê', 0.70, NOW(), NOW()),
-- Nước Ngọt Ly Lớn (Refill) (ID 120) - Kích cỡ đã là Lớn, không cần tùy chọn size
(120, 8, 'Đá Bình Thường', 0.00, NOW(), NOW()), (120, 8, 'Ít Đá', 0.00, NOW(), NOW()), (120, 8, 'Không Đá', 0.00, NOW(), NOW());


INSERT INTO tbl_branch (is_active, created_at, updated_at, address, name, operating_hours, phone)
VALUES
    -- TP. Hồ Chí Minh
    (1, NOW(), NOW(), '123 Đường Nguyễn Huệ, Quận 1, TP. HCM', 'Chi nhánh Nguyễn Huệ Q1', '08:00 - 23:00', '028 1111 2222'),
    (1, NOW(), NOW(), '456 Đường Lê Lợi, Quận 3, TP. HCM', 'Chi nhánh Lê Lợi Q3', '09:00 - 22:00', '028 2222 3333'),
    (1, NOW(), NOW(), '789 Đường Cách Mạng Tháng Tám, Quận 10, TP. HCM', 'Chi nhánh CMT8 Q10', '08:30 - 21:30', '028 3333 4444'),
    (1, NOW(), NOW(), '101 Đường Nguyễn Trãi, Quận 5, TP. HCM', 'Chi nhánh Nguyễn Trãi Q5', '10:00 - 22:00', '028 4444 5555'),
    (1, NOW(), NOW(), '202 Đường Phan Xích Long, Quận Phú Nhuận, TP. HCM', 'Chi nhánh Phan Xích Long', '09:00 - 22:30', '028 5555 6666'),
    (1, NOW(), NOW(), '303 Đường Nguyễn Thị Minh Khai, Quận 1, TP. HCM', 'Chi nhánh NTMK Q1', '07:00 - 21:00', '028 6666 7777'),
    (1, NOW(), NOW(), '505 Đường Võ Văn Tần, Quận 3, TP. HCM', 'Chi nhánh Võ Văn Tần Q3', '09:30 - 22:00', '028 7777 8888'),
    (0, NOW(), NOW(), '606 Đường Sư Vạn Hạnh, Quận 10, TP. HCM', 'Chi nhánh Sư Vạn Hạnh (Tạm đóng)', '09:00 - 21:00', '028 8888 9999'),
    (1, NOW(), NOW(), '707 Khu đô thị Phú Mỹ Hưng, Quận 7, TP. HCM', 'Chi nhánh Phú Mỹ Hưng', '10:00 - 23:00', '028 9999 0000'),
    (1, NOW(), NOW(), '808 Đường Quang Trung, Quận Gò Vấp, TP. HCM', 'Chi nhánh Quang Trung Gò Vấp', '08:00 - 22:00', '028 1010 1010'),

    -- Hà Nội
    (1, NOW(), NOW(), '11 Phố Hàng Bài, Quận Hoàn Kiếm, Hà Nội', 'Chi nhánh Hàng Bài', '08:00 - 22:30', '024 1111 2222'),
    (1, NOW(), NOW(), '22 Phố Lý Thường Kiệt, Quận Hoàn Kiếm, Hà Nội', 'Chi nhánh Lý Thường Kiệt', '09:00 - 22:00', '024 2222 3333'),
    (1, NOW(), NOW(), '33 Đường Trần Duy Hưng, Quận Cầu Giấy, Hà Nội', 'Chi nhánh Trần Duy Hưng', '10:00 - 23:00', '024 3333 4444'),
    (1, NOW(), NOW(), '44 Phố Xã Đàn, Quận Đống Đa, Hà Nội', 'Chi nhánh Xã Đàn', '08:30 - 21:30', '024 4444 5555'),
    (1, NOW(), NOW(), '55 Đường Nguyễn Chí Thanh, Quận Ba Đình, Hà Nội', 'Chi nhánh Nguyễn Chí Thanh', '09:00 - 22:00', '024 5555 6666'),

    -- Đà Nẵng
    (1, NOW(), NOW(), '66 Đường Bạch Đằng, Quận Hải Châu, Đà Nẵng', 'Chi nhánh Bạch Đằng ĐN', '08:00 - 22:00', '0236 1111 2222'),
    (1, NOW(), NOW(), '77 Đường Nguyễn Văn Linh, Quận Thanh Khê, Đà Nẵng', 'Chi nhánh Nguyễn Văn Linh ĐN', '09:00 - 22:30', '0236 2222 3333'),

    -- Các tỉnh thành khác
    (1, NOW(), NOW(), '88 Đường Trần Phú, TP. Nha Trang, Khánh Hòa', 'Chi nhánh Nha Trang', '09:00 - 22:00', '0258 1111 2222'),
    (1, NOW(), NOW(), '99 Đường Hùng Vương, TP. Huế, Thừa Thiên Huế', 'Chi nhánh Huế', '08:30 - 21:30', '0234 1111 2222'),
    (1, NOW(), NOW(), '111 Đại lộ Hòa Bình, Quận Ninh Kiều, Cần Thơ', 'Chi nhánh Cần Thơ', '08:00 - 22:00', '0292 1111 2222');
