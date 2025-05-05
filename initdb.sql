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
        unique(user_id);

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
        foreign key(cart_id) references tbl_cart(id);

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
        foreign key(cart_item_id) references tbl_cart_item(id);

create table tbl_loyalty_point_transaction
(
    points_change    int                                                            null,
    created_at       datetime(6)                                                    null,
    id               bigint auto_increment
        primary key,
    order_id         bigint                                                         null,
    updated_at       datetime(6)                                                    null,
    user_id          bigint                                                         null,
    description      varchar(255)                                                   null,
    transaction_type enum('ADJUSTMENT', 'EARNED', 'EXPIRATION', 'REFUND', 'SPENT') null
);

alter table tbl_loyalty_point_transaction
    add constraint UK3ptwulckn5xfftg0cw5wmir8g
        unique(order_id);

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
        foreign key(menu_item_id) references tbl_menu_item(id);

alter table tbl_menu_item
    add constraint FKnc0a94b15efn4cwtjvscmji0h
        foreign key(menu_category_id) references tbl_menu_category(id);

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
        foreign key(menu_item_option_id) references tbl_menu_item_option(id);

alter table tbl_menu_item_option
    add constraint FKpdwav2ydfeiotwveyvwvdvc4x
        foreign key(menu_item_id) references tbl_menu_item(id);

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
    status       enum('APPROVED', 'PENDING', 'REJECTED') null
);

alter table tbl_menu_item_review
    add constraint FKf7o0uy5c10oh8dtg4cbl2ebap
        foreign key(menu_item_id) references tbl_menu_item(id);

create table tbl_notification
(
    is_read    bit          null,
    created_at datetime(6)  null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)  null,
    user_id    bigint       null,
    content    varchar(255) null,
    title      varchar(255) null
);

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
        foreign key(option_id) references tbl_option(id);

create table tbl_order
(
    branch_id        int                                                           not null,
    total_price      decimal(10, 2)                                                not null,
    created_at       datetime(6)                                                   null,
    id               bigint auto_increment
        primary key,
    updated_at       datetime(6)                                                   null,
    user_id          bigint                                                        not null,
    note             varchar(255)                                                  null,
    order_code       varchar(255)                                                  null,
    shipping_address varchar(255)                                                  null,
    order_status     enum('CANCELLED', 'COMPLETED', 'PROCESSING', 'SHIPPING')     null,
    payment_method   enum('BANK_TRANSFER', 'COD', 'CREDIT_CARD', 'MOMO', 'VNPAY') null
);

alter table tbl_loyalty_point_transaction
    add constraint FKpvi1u0tgxkxcufodomxi9e45s
        foreign key(order_id) references tbl_order(id);

alter table tbl_order
    add constraint UKt91aupbg9yjoyjtx66qo1n0mq
        unique(order_code);

alter table tbl_order
    add constraint FK6tawch2hb6phj81libq4di3d7
        foreign key(branch_id) references tbl_branch(id);

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
    updated_at     datetime(6)    null
);

alter table tbl_order_item
    add constraint FK8gr4qdx0amivl0prt6tf4k9vv
        foreign key(menu_item_id) references tbl_menu_item(id);

alter table tbl_order_item
    add constraint FKmkqpajkg6p2wq4owcv1v08pc5
        foreign key(order_id) references tbl_order(id);

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
        foreign key(order_item_id) references tbl_order_item(id);

create table tbl_payment
(
    amount           decimal(10, 2)                                                 not null,
    created_at       datetime(6)                                                    null,
    id               bigint auto_increment
        primary key,
    order_id         bigint                                                         not null,
    paid_at          datetime(6)                                                    null,
    updated_at       datetime(6)                                                    null,
    gateway_response varchar(255)                                                   null,
    transaction_code varchar(255)                                                   null,
    payment_method   enum('BANK_TRANSFER', 'COD', 'CREDIT_CARD', 'MOMO', 'VNPAY')  not null,
    status           enum('CANCELLED', 'FAILED', 'PENDING', 'REFUNDED', 'SUCCESS') not null
);

alter table tbl_payment
    add constraint FKac54xp3r2r3m9datds9351ric
        foreign key(order_id) references tbl_order(id);

create table tbl_role
(
    created_at  datetime(6)                                    null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)                                    null,
    description varchar(255)                                   null,
    name        enum('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_USER') null
);

create table tbl_shipment
(
    created_at      datetime(6)                                                                                            null,
    id              bigint auto_increment
        primary key,
    order_id        bigint                                                                                                 not null,
    updated_at      datetime(6)                                                                                            null,
    user_id         bigint                                                                                                 null,
    delivery_status enum('ASSIGNED_SHIPPER', 'DELIVERED', 'FAILED_ATTEMPT', 'OUT_FOR_DELIVERY', 'PICKED_UP', 'PREPARING') not null
);

alter table tbl_shipment
    add constraint UKbeb9glb1yy9xa5gdc5s76pcd2
        unique(order_id);

alter table tbl_shipment
    add constraint FKb1wxiaexwu36k7740xfs776x1
        foreign key(order_id) references tbl_order(id);

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
    delivery_status    enum('ASSIGNED_SHIPPER', 'DELIVERED', 'FAILED_ATTEMPT', 'OUT_FOR_DELIVERY', 'PICKED_UP', 'PREPARING') null
);

alter table tbl_shipment_tracking_event
    add constraint FKo94f45rni5ivrdkfcrebx6aaf
        foreign key(shipment_id) references tbl_shipment(id);

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
    gender                 enum('FEMALE', 'MALE', 'OTHER') null,
    is_active              enum('ACTIVE', 'INACTIVE')      null
);

alter table tbl_cart
    add constraint FKhv6grtjnmtoylt2yyt4wmqtf3
        foreign key(user_id) references tbl_user(id);

alter table tbl_loyalty_point_transaction
    add constraint FKmidvb51pjeo3bd9m5hn8wiy2q
        foreign key(user_id) references tbl_user(id);

alter table tbl_menu_item_review
    add constraint FK49xv7if8qcql1kd6igf4soncx
        foreign key(user_id) references tbl_user(id);

alter table tbl_notification
    add constraint FK17xlvi4d2o1r18carkq5kmd3c
        foreign key(user_id) references tbl_user(id);

alter table tbl_order
    add constraint FKhyolniflkctr0p6bp4t8me9vj
        foreign key(user_id) references tbl_user(id);

alter table tbl_shipment
    add constraint FKeq5lvi93h8mlrn29xbw3w0lvg
        foreign key(user_id) references tbl_user(id);

alter table tbl_user
    add constraint UKd6tho5pxk6qd8xem6vwou8sdp
        unique(phone);

alter table tbl_user
    add constraint UKnpn1wf1yu1g5rjohbek375pp1
        unique(email);

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
        foreign key(user_id) references tbl_user(id);

alter table tbl_user_has_role
    add constraint FKnbcb2rhv9gavk1vtounetntw0
        foreign key(role_id) references tbl_role(id);

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
        foreign key(menu_item_id) references tbl_menu_item(id);

alter table tbl_wishlist_item
    add constraint FKsi21859ual7hbkhv4rlx96fow
        foreign key(user_id) references tbl_user(id);


INSERT INTO tbl_role(id, name, created_at, updated_at, description)
VALUES(2, 'ROLE_STAFF', NULL, NULL, NULL),
  (3, 'ROLE_USER', NULL, NULL, NULL),
  (1, 'ROLE_ADMIN', NULL, NULL, NULL);

INSERT INTO tbl_user(date_of_birth, loyalty_points_balance, created_at, id, updated_at, address, avatar, email, full_name, password, phone, gender, is_active)
VALUES('2004-09-25', 0, '2025-04-04 12:19:45.269846', 2, '2025-04-04 12:19:45.269846', 'VN', 'https://thanhnien.mediacdn.vn/Uploaded/hoangnam/2022_01_06/anh-01-8444.jpg', 'user@gmail.com', 'user', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', '0979859449', 'MALE', 'ACTIVE'),
  ('2004-09-25', 0, '2025-04-04 12:19:45.269846', 1, '2025-04-04 12:19:45.269846', 'VN', null, 'admin@gmail.com', 'admin', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', '0979859559', 'MALE', 'ACTIVE');

INSERT INTO tbl_user_has_role(created_at, id, role_id, updated_at, user_id)
VALUES('2025-04-04 12:19:45.276706', 1, 3, '2025-04-04 12:19:45.276706', 2),
  ('2025-04-04 12:19:45.276706', 2, 1, '2025-04-04 12:19:45.276706', 1);

-- option
INSERT INTO tbl_option(name, description, created_at, updated_at)
VALUES
('Kích cỡ', 'Tùy chọn kích cỡ cho món ăn hoặc đồ uống(ví dụ: Nhỏ, Vừa, Lớn)', NOW(), NOW()),
('Loại đế bánh', 'Tùy chọn loại đế cho Pizza(ví dụ: Dày, Mỏng Giòn)', NOW(), NOW()),
('Thêm Topping Pizza', 'Tùy chọn thêm các loại topping cho Pizza(ví dụ: Phô mai, Xúc xích, Nấm)', NOW(), NOW()),
('Mức độ cay', 'Tùy chọn độ cay cho món ăn(ví dụ: Không cay, Cay vừa, Rất cay)', NOW(), NOW()),
('Thêm phô mai', 'Tùy chọn thêm phô mai vào món ăn(ví dụ: Burger, Mì Ý)', NOW(), NOW()),
('Thêm thịt/pattie', 'Tùy chọn thêm thịt hoặc miếng pattie cho Burger', NOW(), NOW()),
('Sốt chấm kèm', 'Tùy chọn các loại sốt chấm đi kèm(ví dụ: Tương cà, Tương ớt, Sốt Mayonnaise)', NOW(), NOW()),
('Mức độ đá', 'Tùy chọn lượng đá trong đồ uống(ví dụ: Không đá, Ít đá, Nhiều đá)', NOW(), NOW()),
('Mức độ đường', 'Tùy chọn lượng đường trong đồ uống(ví dụ: 100% đường, 70% đường, 50% đường, Không đường)', NOW(), NOW()),
('Topping đồ uống', 'Tùy chọn thêm topping cho đồ uống(ví dụ: Trân châu, Thạch trái cây)', NOW(), NOW());

-- menu_category
INSERT INTO tbl_menu_category(id, name, created_at, updated_at, description, image_url)
VALUES
(1, 'Pizza', NOW(), NOW(), 'Pizza đế giòn, nhiều loại nhân.', 'https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80'),
(2, 'Hamburger', NOW(), NOW(), 'Burger bò, gà kẹp rau tươi và sốt.', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60'),
(3, 'Mì ý', NOW(), NOW(), 'Mì Ý sốt bò bằm hoặc sốt kem.', 'https://pastafrescasaigon.com/wp-content/uploads/2023/08/2017-04-21_0000185.png'),
(4, 'Gà rán', NOW(), NOW(), 'Gà rán giòn rụm, có vị cay hoặc truyền thống.', 'https://images.unsplash.com/photo-1562967914-01efa7e87832?ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'),
(5, 'Món ăn kèm', NOW(), NOW(), 'Món ăn kèm: Khoai tây chiên, hành tây chiên...', 'https://www.allrecipes.com/thmb/Hkg8fY6d22mIn-OZm3AznW00-T8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/16760-Old-Fashioned-Onion-Rings-ddmfs-4x3-1597-f5e601d18049437887b748e70acd0703.jpg'),
(6, 'Nước uống', NOW(), NOW(), 'Nước ngọt, nước suối, trà.', 'https://images.unsplash.com/photo-1551024709-8f23befc6f87?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');

INSERT INTO tbl_menu_item(id, name, created_at, updated_at, description, image_url, base_price, is_available, menu_category_id)
VALUES
-- Danh mục 1: PIZZA(menu_category_id = 1)
(1, 'Pizza Phô Mai Classic', NOW(), NOW(), 'Sốt cà chua, phô mai Mozzarella tan chảy.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+pho+mai', 139000, 1, 1),
(2, 'Pizza Pepperoni Supreme', NOW(), NOW(), 'Xúc xích Pepperoni cay nồng phủ kín mặt bánh.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Pepperoni', 159000, 1, 1),
(3, 'Pizza Thịt Nguội & Nấm', NOW(), NOW(), 'Kết hợp thịt nguội, nấm tươi và phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Ham+Mushroom', 169000, 1, 1),
(4, 'Pizza Hải Sản Nhiệt Đới', NOW(), NOW(), 'Tôm, mực, thanh cua, thơm, sốt Thousand Island.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Seafood', 199000, 1, 1),
(5, 'Pizza Gà BBQ', NOW(), NOW(), 'Thịt gà, sốt BBQ, hành tây, ớt chuông.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+BBQ+Chicken', 179000, 1, 1),
(6, 'Pizza Rau Củ Tổng Hợp', NOW(), NOW(), 'Nấm, ớt chuông, hành tây, ô liu, cà chua.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Veggie', 149000, 1, 1),
(7, 'Pizza Xúc Xích Ý & Hành Tây', NOW(), NOW(), 'Xúc xích Ý cay nhẹ, hành tây và phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Sausage+Onion', 169000, 1, 1),
(8, 'Pizza 4 Loại Phô Mai', NOW(), NOW(), 'Mozzarella, Cheddar, Parmesan, Blue Cheese.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+4+Cheese', 189000, 1, 1),
(9, 'Pizza Hawaii', NOW(), NOW(), 'Thịt nguội, thơm, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Hawaiian', 169000, 1, 1),
(10, 'Pizza Bò Bằm & Ngô', NOW(), NOW(), 'Thịt bò bằm, ngô ngọt, hành tây, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Beef+Corn', 179000, 1, 1),
(11, 'Pizza Gà Nấm Sốt Kem', NOW(), NOW(), 'Thịt gà, nấm, sốt kem trắng Alfredo.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Chicken+Alfredo', 189000, 1, 1),
(12, 'Pizza Cay Kiểu Mexico', NOW(), NOW(), 'Thịt bò bằm cay, đậu đỏ, hành tây, ớt jalapeno.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Mexican', 189000, 1, 1),
(13, 'Pizza Cá Ngừ & Ô Liu', NOW(), NOW(), 'Cá ngừ ngâm dầu, ô liu đen, hành tây.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Tuna+Olive', 169000, 1, 1),
(14, 'Pizza Thịt Xông Khói', NOW(), NOW(), 'Thịt xông khói giòn rụm phủ phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Bacon', 169000, 1, 1),
(15, 'Pizza Pesto Gà', NOW(), NOW(), 'Sốt Pesto, thịt gà nướng, cà chua bi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Pesto+Chicken', 189000, 1, 1),
(16, 'Pizza Phô Mai Dê & Rau Bina', NOW(), NOW(), 'Phô mai dê, rau bina(spinach), tỏi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Goat+Cheese', 199000, 1, 1),
(17, 'Pizza Margherita Đặc Biệt', NOW(), NOW(), 'Cà chua tươi, phô mai Mozzarella tươi, lá húng quế.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Margherita', 169000, 1, 1),
(18, 'Pizza Sốt Trứng Muối Hải Sản', NOW(), NOW(), 'Sốt trứng muối đặc biệt, tôm, mực.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Salted+Egg', 209000, 1, 1),
(19, 'Pizza Thập Cẩm(The Works)', NOW(), NOW(), 'Pepperoni, thịt nguội, bò bằm, nấm, ớt, hành tây, ô liu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+The+Works', 219000, 1, 1),
(20, 'Pizza Đế Mỏng Giòn(Chọn Nhân)', NOW(), NOW(), 'Đế bánh siêu mỏng giòn, chọn 2 loại nhân.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pizza+Thin+Crust', 169000, 1, 1),

-- Danh mục 2: HAMBURGER(menu_category_id = 2)
(21, 'Classic Beef Burger', NOW(), NOW(), 'Bò Mỹ, rau diếp, cà chua, hành tây, sốt burger.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Classic+Burger', 69000, 1, 2),
(22, 'Cheeseburger', NOW(), NOW(), 'Bò Mỹ, phô mai American, rau diếp, cà chua, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheeseburger', 79000, 1, 2),
(23, 'Double Cheeseburger', NOW(), NOW(), '2 lớp bò Mỹ, 2 lớp phô mai American, rau, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Double+Cheeseburger', 109000, 1, 2),
(24, 'Bacon Cheeseburger', NOW(), NOW(), 'Bò Mỹ, phô mai American, thịt xông khói giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bacon+Cheeseburger', 89000, 1, 2),
(25, 'Mushroom Swiss Burger', NOW(), NOW(), 'Bò Mỹ, nấm xào, phô mai Thụy Sĩ, sốt kem nấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mushroom+Burger', 99000, 1, 2),
(26, 'Crispy Chicken Burger', NOW(), NOW(), 'Gà phi lê chiên giòn, rau diếp, sốt mayonnaise.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Crispy+Chicken+Burger', 79000, 1, 2),
(27, 'Spicy Chicken Burger', NOW(), NOW(), 'Gà phi lê chiên giòn cay, rau diếp, sốt cay.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spicy+Chicken+Burger', 85000, 1, 2),
(28, 'Grilled Chicken Burger', NOW(), NOW(), 'Gà phi lê nướng, rau diếp, cà chua, sốt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Grilled+Chicken+Burger', 89000, 1, 2),
(29, 'Fish Burger', NOW(), NOW(), 'Cá phi lê tẩm bột chiên, sốt tartar, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fish+Burger', 79000, 1, 2),
(30, 'Veggie Burger', NOW(), NOW(), 'Nhân rau củ chiên, rau diếp, cà chua-Known', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Veggie+Burger', 79000, 1, 2),
(31, 'BBQ Bacon Burger', NOW(), NOW(), 'Bò Mỹ, thịt xông khói, sốt BBQ, hành tây chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQ+Bacon+Burger', 99000, 1, 2),
(32, 'Jalapeno Burger', NOW(), NOW(), 'Bò Mỹ, phô mai Pepper Jack, ớt Jalapeno ngâm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Jalapeno+Burger', 89000, 1, 2),
(33, 'Blue Cheese Burger', NOW(), NOW(), 'Bò Mỹ, phô mai xanh(Blue Cheese), hành tây caramel.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Blue+Cheese+Burger', 99000, 1, 2),
(34, 'Teriyaki Burger', NOW(), NOW(), 'Bò Mỹ hoặc gà, sốt Teriyaki, lát thơm nướng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Teriyaki+Burger', 89000, 1, 2),
(35, 'Breakfast Burger', NOW(), NOW(), 'Bò Mỹ, trứng ốp la, thịt xông khói, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Breakfast+Burger', 109000, 1, 2),
(36, 'Slider Trio(Mini Burgers)', NOW(), NOW(), 'Bộ 3 burger nhỏ: bò, gà, phô mai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Slider+Trio', 129000, 1, 2),
(37, 'Tower Burger', NOW(), NOW(), '2 lớp bò, 1 lớp gà chiên giòn, phô mai, bacon, hành tây chiên.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Tower+Burger', 149000, 1, 2),
(38, 'Hawaiian Chicken Burger', NOW(), NOW(), 'Gà nướng, lát thơm nướng, sốt teriyaki.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Hawaiian+Chicken+Burger', 99000, 1, 2),
(39, 'Avocado Burger', NOW(), NOW(), 'Bò Mỹ, lát bơ tươi, hành tây đỏ, rau mầm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Avocado+Burger', 109000, 1, 2),
(40, 'Burger Gà Sốt Nấm', NOW(), NOW(), 'Gà nướng, sốt kem nấm, phô mai Thụy Sĩ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Mushroom+Burger', 99000, 1, 2),

-- Danh mục 3: SPAGHETTI(menu_category_id = 3)
(41, 'Spaghetti Bolognese', NOW(), NOW(), 'Mì Ý sốt bò bằm cà chua cổ điển.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Bolognese', 99000, 1, 3),
(42, 'Spaghetti Carbonara', NOW(), NOW(), 'Mì Ý sốt kem trứng và thịt xông khói.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Carbonara', 109000, 1, 3),
(43, 'Spaghetti Sốt Kem Gà Nấm', NOW(), NOW(), 'Mì Ý sốt kem, thịt gà, nấm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Chicken+Alfredo', 119000, 1, 3),
(44, 'Spaghetti Hải Sản Sốt Cà Chua', NOW(), NOW(), 'Mì Ý sốt cà chua, tôm, mực, nghêu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Seafood+Tomato', 139000, 1, 3),
(45, 'Spaghetti Sốt Kem Hải Sản', NOW(), NOW(), 'Mì Ý sốt kem, tôm, mực, nghêu.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Seafood+Cream', 149000, 1, 3),
(46, 'Spaghetti Aglio e Olio', NOW(), NOW(), 'Mì Ý xào tỏi, dầu ô liu, ớt khô(cay nhẹ).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Aglio+Olio', 89000, 1, 3),
(47, 'Spaghetti Aglio e Olio Tôm', NOW(), NOW(), 'Mì Ý xào tỏi ớt với tôm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Aglio+Olio+Shrimp', 129000, 1, 3),
(48, 'Spaghetti Sốt Pesto', NOW(), NOW(), 'Mì Ý sốt lá húng quế Pesto.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Pesto', 99000, 1, 3),
(49, 'Spaghetti Sốt Pesto Gà', NOW(), NOW(), 'Mì Ý sốt Pesto với thịt gà nướng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Pesto+Chicken', 129000, 1, 3),
(50, 'Spaghetti Meatballs', NOW(), NOW(), 'Mì Ý sốt cà chua với thịt viên bò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Meatballs', 119000, 1, 3),
(51, 'Spaghetti Puttanesca', NOW(), NOW(), 'Mì Ý sốt cà chua, ô liu, nụ bạch hoa, cá cơm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Puttanesca', 119000, 1, 3),
(52, 'Spaghetti Arrabbiata', NOW(), NOW(), 'Mì Ý sốt cà chua cay nồng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Arrabbiata', 99000, 1, 3),
(53, 'Spaghetti Vẹm Xanh Sốt Rượu Vang', NOW(), NOW(), 'Mì Ý xào vẹm xanh, tỏi, rượu vang trắng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Mussels', 149000, 1, 3),
(54, 'Spaghetti Cá Hồi Sốt Kem', NOW(), NOW(), 'Mì Ý sốt kem với phi lê cá hồi áp chảo.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Salmon+Cream', 169000, 1, 3),
(55, 'Spaghetti Bò Lúc Lắc', NOW(), NOW(), 'Mì Ý xào với thịt bò thái hạt lựu kiểu Việt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Bo+Luc+Lac', 139000, 1, 3),
(56, 'Spaghetti Đút Lò Phô Mai', NOW(), NOW(), 'Mì Ý Bolognese phủ phô mai đút lò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Baked', 129000, 1, 3),
(57, 'Spaghetti Sốt Cà Ri Gà', NOW(), NOW(), 'Mì Ý sốt cà ri kiểu Nhật với thịt gà.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Curry+Chicken', 119000, 1, 3),
(58, 'Spaghetti Rau Củ Sốt Kem', NOW(), NOW(), 'Mì Ý sốt kem với bông cải xanh, cà rốt, nấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Veggie+Cream', 99000, 1, 3),
(59, 'Spaghetti Trứng Muối Tôm', NOW(), NOW(), 'Mì Ý sốt kem trứng muối với tôm tươi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Salted+Egg+Shrimp', 149000, 1, 3),
(60, 'Spaghetti Mực Đen', NOW(), NOW(), 'Mì Ý sốt mực đen đặc trưng của Ý.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Spaghetti+Squid+Ink', 159000, 1, 3),

-- Danh mục 4: FRIED CHICKEN(menu_category_id = 4)
(61, 'Gà Rán Giòn(1 Miếng)', NOW(), NOW(), '1 miếng gà giòn tan(chọn vị Original/Spicy).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+1pc', 35000, 1, 4),
(62, 'Gà Rán Giòn(2 Miếng)', NOW(), NOW(), '2 miếng gà giòn tan(chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+2pcs', 69000, 1, 4),
(63, 'Gà Rán Giòn(3 Miếng)', NOW(), NOW(), '3 miếng gà giòn tan(chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Chicken+3pcs', 99000, 1, 4),
(64, 'Combo Gà Rán 2 Miếng', NOW(), NOW(), '2 miếng gà, 1 Khoai tây(vừa), 1 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Chicken+2pcs', 109000, 1, 4),
(65, 'Combo Gà Rán 3 Miếng', NOW(), NOW(), '3 miếng gà, 1 Khoai tây(vừa), 1 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Chicken+3pcs', 139000, 1, 4),
(66, 'Xô Gà Rán(6 Miếng)', NOW(), NOW(), '6 miếng gà giòn(chọn vị hoặc mix).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+6pcs', 199000, 1, 4),
(67, 'Xô Gà Rán(9 Miếng)', NOW(), NOW(), '9 miếng gà giòn(chọn vị hoặc mix).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+9pcs', 289000, 1, 4),
(68, 'Xô Gà Gia Đình', NOW(), NOW(), '8 miếng gà, 2 Khoai tây(lớn), 1 Salad bắp cải, 4 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Bucket+Chicken+Family', 399000, 1, 4),
(69, 'Cánh Gà Rán(3 cái)', NOW(), NOW(), '3 cánh gà chiên giòn(chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Wings+3pcs', 59000, 1, 4),
(70, 'Cánh Gà Rán(6 cái)', NOW(), NOW(), '6 cánh gà chiên giòn(chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Wings+6pcs', 109000, 1, 4),
(71, 'Đùi Gà Rán(1 cái)', NOW(), NOW(), '1 đùi gà chiên giòn(chọn vị).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Drumstick', 39000, 1, 4),
(72, 'Gà Rán Không Xương(Popcorn)', NOW(), NOW(), 'Gà viên chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Popcorn', 69000, 1, 4),
(73, 'Gà Rán Sốt Cay Ngọt Hàn Quốc(6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt cay ngọt kiểu Hàn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Korean+Wings', 129000, 1, 4),
(74, 'Gà Rán Sốt BBQ(6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt BBQ đậm đà.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQq+Wings', 129000, 1, 4),
(75, 'Gà Rán Sốt Phô Mai(6 cánh)', NOW(), NOW(), '6 cánh gà giòn phủ sốt phô mai béo ngậy.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheese+Wings', 139000, 1, 4),
(76, 'Combo Gà Popcorn', NOW(), NOW(), '1 Gà Popcorn(vừa), 1 Khoai tây(vừa), 1 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Popcorn', 109000, 1, 4),
(77, 'Gà Rán Mật Ong(1 Miếng)', NOW(), NOW(), '1 miếng gà giòn phủ sốt mật ong.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Honey+Chicken', 39000, 1, 4),
(78, 'Combo Cánh Gà', NOW(), NOW(), '5 cánh gà(chọn vị), 1 Khoai tây(vừa), 1 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Wings', 139000, 1, 4),
(79, 'Ức Gà Rán Filet(1 miếng)', NOW(), NOW(), '1 miếng ức gà không xương chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Chicken+Filet', 45000, 1, 4),
(80, 'Xô Gà Tiệc Tùng', NOW(), NOW(), '12 miếng gà, 4 Khoai tây(vừa), 2 Gà Popcorn, 6 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Party+Bucket', 599000, 1, 4),

-- Danh mục 5: SIDES(menu_category_id = 5)
(81, 'Khoai Tây Chiên', NOW(), NOW(), 'Khoai tây sợi chiên vàng giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fries', 29000, 1, 5),
(82, 'Khoai Tây Chiên Phô Mai', NOW(), NOW(), 'Khoai tây chiên phủ sốt phô mai nóng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheese+Fries', 39000, 1, 5),
(83, 'Khoai Tây Chiên BBQ', NOW(), NOW(), 'Khoai tây chiên rắc bột gia vị BBQ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=BBQ+Fries', 34000, 1, 5),
(84, 'Hành Tây Chiên Giòn(Onion Rings)', NOW(), NOW(), 'Khoanh hành tây tẩm bột chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Onion+Rings', 34000, 1, 5),
(85, 'Phô Mai Que(4 que)', NOW(), NOW(), 'Phô mai Mozzarella chiên xù ăn kèm sốt cà chua.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mozzarella+Sticks', 49000, 1, 5),
(86, 'Salad Bắp Cải Trộn', NOW(), NOW(), 'Bắp cải, cà rốt thái sợi trộn sốt mayonnaise.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coleslaw', 29000, 1, 5),
(87, 'Khoai Tây Nghiền & Nước Sốt', NOW(), NOW(), 'Khoai tây nghiền mịn ăn kèm nước sốt thịt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mashed+Potato', 34000, 1, 5),
(88, 'Bánh Mì Bơ Tỏi', NOW(), NOW(), 'Bánh mì nướng bơ tỏi thơm lừng.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Garlic+Bread', 29000, 1, 5),
(89, 'Bánh Mì Bơ Tỏi Phô Mai', NOW(), NOW(), 'Bánh mì bơ tỏi phủ phô mai Mozzarella đút lò.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Cheesy+Garlic+Bread', 39000, 1, 5),
(90, 'Ngô Ngọt Hấp Bơ', NOW(), NOW(), 'Hạt ngô ngọt hấp với bơ.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Corn', 29000, 1, 5),
(91, 'Nấm Chiên Giòn', NOW(), NOW(), 'Nấm tươi tẩm bột chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Mushrooms', 49000, 1, 5),
(92, 'Khoai Tây Múi Cau', NOW(), NOW(), 'Khoai tây bổ múi cau chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Potato+Wedges', 34000, 1, 5),
(93, 'Jalapeno Poppers(4 cái)', NOW(), NOW(), 'Ớt Jalapeno nhồi phô mai chiên xù.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Jalapeno+Poppers', 59000, 1, 5),
(94, 'Bánh Xếp Chiên(Fried Dumplings - 4 cái)', NOW(), NOW(), 'Bánh xếp nhân thịt chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fried+Dumplings', 49000, 1, 5),
(95, 'Chả Giò Mini(4 cuốn)', NOW(), NOW(), 'Chả giò nhỏ chiên giòn.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mini+Spring+Rolls', 39000, 1, 5),
(96, 'Xúc Xích Đức Nướng', NOW(), NOW(), 'Xúc xích Đức nướng ăn kèm mù tạt.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Grilled+Sausage', 39000, 1, 5),
(97, 'Súp Kem Nấm', NOW(), NOW(), 'Súp kem nấm nóng hổi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mushroom+Soup', 39000, 1, 5),
(98, 'Súp Cà Chua', NOW(), NOW(), 'Súp cà chua kiểu Ý.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Tomato+Soup', 39000, 1, 5),
(99, 'Salad Trộn Dầu Giấm', NOW(), NOW(), 'Xà lách, cà chua, dưa leo, sốt dầu giấm.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Garden+Salad', 39000, 1, 5),
(100, 'Combo Ăn Kèm', NOW(), NOW(), '1 Khoai tây(vừa), 1 Salad bắp cải, 1 Nước ngọt(vừa).', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Combo+Sides', 69000, 1, 5),

-- Danh mục 6: DRINKS(menu_category_id = 6)
(101, 'Coca-Cola', NOW(), NOW(), 'Nước ngọt có gas Coca-Cola.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coca-Cola', 15000, 1, 6),
(102, 'Coca-Cola Light / Zero', NOW(), NOW(), 'Nước ngọt Coca-Cola không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Coke+Zero', 15000, 1, 6),
(103, 'Pepsi', NOW(), NOW(), 'Nước ngọt có gas Pepsi.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pepsi', 15000, 1, 6),
(104, 'Pepsi Light / Zero', NOW(), NOW(), 'Nước ngọt Pepsi không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Pepsi+Zero', 15000, 1, 6),
(105, 'Sprite', NOW(), NOW(), 'Nước ngọt vị chanh Sprite.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Sprite', 15000, 1, 6),
(106, '7 Up', NOW(), NOW(), 'Nước ngọt vị chanh 7 Up.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=7+Up', 15000, 1, 6),
(107, 'Fanta Cam', NOW(), NOW(), 'Nước ngọt vị cam Fanta.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fanta+Orange', 15000, 1, 6),
(108, 'Fanta Xá Xị', NOW(), NOW(), 'Nước ngọt vị xá xị Fanta.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Fanta+Sarsi', 15000, 1, 6),
(109, 'Mirinda Kem Soda', NOW(), NOW(), 'Nước ngọt vị kem soda Mirinda.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Mirinda+Soda+Cream', 15000, 1, 6),
(110, 'Nước Suối Aquafina', NOW(), NOW(), 'Nước tinh khiết đóng chai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Aquafina', 10000, 1, 6),
(111, 'Nước Suối Dasani', NOW(), NOW(), 'Nước tinh khiết đóng chai.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Dasani', 10000, 1, 6),
(112, 'Trà Chanh Lipton', NOW(), NOW(), 'Trà chanh đóng chai Lipton.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lipton+Lemon', 20000, 1, 6),
(113, 'Trà Đào Lipton', NOW(), NOW(), 'Trà đào đóng chai Lipton.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lipton+Peach', 20000, 1, 6),
(114, 'Trà Đá Pha Sẵn', NOW(), NOW(), 'Trà đen pha sẵn không đường.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Iced+Tea', 15000, 1, 6),
(115, 'Nước Cam Ép', NOW(), NOW(), 'Nước cam tươi ép.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Orange+Juice', 30000, 1, 6),
(116, 'Nước Chanh Tươi', NOW(), NOW(), 'Nước chanh tươi pha.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Lemonade', 25000, 1, 6),
(117, 'Cà Phê Đen Đá', NOW(), NOW(), 'Cà phê phin đen đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Black+Coffee', 25000, 1, 6),
(118, 'Cà Phê Sữa Đá', NOW(), NOW(), 'Cà phê phin sữa đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Milk+Coffee', 30000, 1, 6),
(119, 'Milo Đá', NOW(), NOW(), 'Thức uống lúa mạch Milo pha đá.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Milo', 25000, 1, 6),
(120, 'Nước Ngọt Ly Lớn(Refill)', NOW(), NOW(), 'Ly lớn tự chọn nước ngọt và có thể refill.', 'https://dummyimage.com/300x200/ff6666/ffffff.png&text=Refill+Drink', 30000, 1, 6);

INSERT INTO tbl_menu_item_option(id, menu_item_id, option_id, value, additional_price, created_at, updated_at)
VALUES
-- Pizza ID 1
(1, 1, 1, 'Vừa(M)', 0, NOW(), NOW()),
(2, 1, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(3, 1, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(4, 1, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(5, 1, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(6, 1, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(7, 1, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(8, 1, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(9, 1, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(10, 1, 7, 'Tương cà', 7000, NOW(), NOW()),
(11, 1, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(12, 1, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 2
(13, 2, 1, 'Vừa(M)', 0, NOW(), NOW()),
(14, 2, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(15, 2, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(16, 2, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(17, 2, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(18, 2, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(19, 2, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(20, 2, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(21, 2, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(22, 2, 7, 'Tương cà', 7000, NOW(), NOW()),
(23, 2, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(24, 2, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 3
(25, 3, 1, 'Vừa(M)', 0, NOW(), NOW()),
(26, 3, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(27, 3, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(28, 3, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(29, 3, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(30, 3, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(31, 3, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(32, 3, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(33, 3, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(34, 3, 7, 'Tương cà', 7000, NOW(), NOW()),
(35, 3, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(36, 3, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 4
(37, 4, 1, 'Vừa(M)', 0, NOW(), NOW()),
(38, 4, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(39, 4, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(40, 4, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(41, 4, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(42, 4, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(43, 4, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(44, 4, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(45, 4, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(46, 4, 7, 'Tương cà', 7000, NOW(), NOW()),
(47, 4, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(48, 4, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 5
(49, 5, 1, 'Vừa(M)', 0, NOW(), NOW()),
(50, 5, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(51, 5, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(52, 5, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(53, 5, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(54, 5, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(55, 5, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(56, 5, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(57, 5, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(58, 5, 7, 'Tương cà', 7000, NOW(), NOW()),
(59, 5, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(60, 5, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 6
(61, 6, 1, 'Vừa(M)', 0, NOW(), NOW()),
(62, 6, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(63, 6, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(64, 6, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(65, 6, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(66, 6, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(67, 6, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(68, 6, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(69, 6, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(70, 6, 7, 'Tương cà', 7000, NOW(), NOW()),
(71, 6, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(72, 6, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 7
(73, 7, 1, 'Vừa(M)', 0, NOW(), NOW()),
(74, 7, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(75, 7, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(76, 7, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(77, 7, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(78, 7, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(79, 7, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(80, 7, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(81, 7, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(82, 7, 7, 'Tương cà', 7000, NOW(), NOW()),
(83, 7, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(84, 7, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 8
(85, 8, 1, 'Vừa(M)', 0, NOW(), NOW()),
(86, 8, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(87, 8, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(88, 8, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(89, 8, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(90, 8, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(91, 8, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(92, 8, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(93, 8, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(94, 8, 7, 'Tương cà', 7000, NOW(), NOW()),
(95, 8, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(96, 8, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 9
(97, 9, 1, 'Vừa(M)', 0, NOW(), NOW()),
(98, 9, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(99, 9, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(100, 9, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(101, 9, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(102, 9, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(103, 9, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(104, 9, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(105, 9, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(106, 9, 7, 'Tương cà', 7000, NOW(), NOW()),
(107, 9, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(108, 9, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 10
(109, 10, 1, 'Vừa(M)', 0, NOW(), NOW()),
(110, 10, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(111, 10, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(112, 10, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(113, 10, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(114, 10, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(115, 10, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(116, 10, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(117, 10, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(118, 10, 7, 'Tương cà', 7000, NOW(), NOW()),
(119, 10, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(120, 10, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 11
(121, 11, 1, 'Vừa(M)', 0, NOW(), NOW()),
(122, 11, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(123, 11, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(124, 11, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(125, 11, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(126, 11, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(127, 11, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(128, 11, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(129, 11, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(130, 11, 7, 'Tương cà', 7000, NOW(), NOW()),
(131, 11, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(132, 11, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 12
(133, 12, 1, 'Vừa(M)', 0, NOW(), NOW()),
(134, 12, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(135, 12, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(136, 12, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(137, 12, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(138, 12, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(139, 12, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(140, 12, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(141, 12, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(142, 12, 7, 'Tương cà', 7000, NOW(), NOW()),
(143, 12, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(144, 12, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 13
(145, 13, 1, 'Vừa(M)', 0, NOW(), NOW()),
(146, 13, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(147, 13, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(148, 13, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(149, 13, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(150, 13, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(151, 13, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(152, 13, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(153, 13, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(154, 13, 7, 'Tương cà', 7000, NOW(), NOW()),
(155, 13, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(156, 13, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 14
(157, 14, 1, 'Vừa(M)', 0, NOW(), NOW()),
(158, 14, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(159, 14, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(160, 14, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(161, 14, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(162, 14, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(163, 14, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(164, 14, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(165, 14, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(166, 14, 7, 'Tương cà', 7000, NOW(), NOW()),
(167, 14, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(168, 14, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 15
(169, 15, 1, 'Vừa(M)', 0, NOW(), NOW()),
(170, 15, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(171, 15, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(172, 15, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(173, 15, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(174, 15, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(175, 15, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(176, 15, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(177, 15, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(178, 15, 7, 'Tương cà', 7000, NOW(), NOW()),
(179, 15, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(180, 15, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 16
(181, 16, 1, 'Vừa(M)', 0, NOW(), NOW()),
(182, 16, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(183, 16, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(184, 16, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(185, 16, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(186, 16, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(187, 16, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(188, 16, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(189, 16, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(190, 16, 7, 'Tương cà', 7000, NOW(), NOW()),
(191, 16, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(192, 16, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 17
(193, 17, 1, 'Vừa(M)', 0, NOW(), NOW()),
(194, 17, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(195, 17, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(196, 17, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(197, 17, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(198, 17, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(199, 17, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(200, 17, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(201, 17, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(202, 17, 7, 'Tương cà', 7000, NOW(), NOW()),
(203, 17, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(204, 17, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 18
(205, 18, 1, 'Vừa(M)', 0, NOW(), NOW()),
(206, 18, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(207, 18, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(208, 18, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(209, 18, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(210, 18, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(211, 18, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(212, 18, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(213, 18, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(214, 18, 7, 'Tương cà', 7000, NOW(), NOW()),
(215, 18, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(216, 18, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 19
(217, 19, 1, 'Vừa(M)', 0, NOW(), NOW()),
(218, 19, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(219, 19, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(220, 19, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(221, 19, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(222, 19, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(223, 19, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(224, 19, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(225, 19, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(226, 19, 7, 'Tương cà', 7000, NOW(), NOW()),
(227, 19, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(228, 19, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Pizza ID 20
(229, 20, 1, 'Vừa(M)', 0, NOW(), NOW()),
(230, 20, 1, 'Lớn(L)', 50000, NOW(), NOW()),
(231, 20, 2, 'Đế dày xốp', 0, NOW(), NOW()),
(232, 20, 2, 'Đế mỏng giòn', 0, NOW(), NOW()),
(233, 20, 2, 'Đế viền phô mai', 35000, NOW(), NOW()),
(234, 20, 3, 'Thêm Nấm', 18000, NOW(), NOW()),
(235, 20, 3, 'Thêm Pepperoni', 30000, NOW(), NOW()),
(236, 20, 3, 'Thêm Ô liu', 15000, NOW(), NOW()),
(237, 20, 5, 'Nhân đôi phô mai', 35000, NOW(), NOW()),
(238, 20, 7, 'Tương cà', 7000, NOW(), NOW()),
(239, 20, 7, 'Sốt Ranch', 12000, NOW(), NOW()),
(240, 20, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW());
INSERT INTO tbl_menu_item_option(id, menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Burger ID 21: Classic Beef Burger
(241, 21, 4, 'Không cay', 0, NOW(), NOW()),
(242, 21, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(243, 21, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(244, 21, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(245, 21, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(246, 21, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(247, 21, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(248, 21, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(249, 21, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(250, 21, 7, 'Tương cà', 7000, NOW(), NOW()),
(251, 21, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(252, 21, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 22: Cheeseburger
(253, 22, 4, 'Không cay', 0, NOW(), NOW()),
(254, 22, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(255, 22, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(256, 22, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(257, 22, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(258, 22, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(259, 22, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(260, 22, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(261, 22, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(262, 22, 7, 'Tương cà', 7000, NOW(), NOW()),
(263, 22, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(264, 22, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 23: Double Cheeseburger
(265, 23, 4, 'Không cay', 0, NOW(), NOW()),
(266, 23, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(267, 23, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(268, 23, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(269, 23, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(270, 23, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(271, 23, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(272, 23, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(273, 23, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(274, 23, 7, 'Tương cà', 7000, NOW(), NOW()),
(275, 23, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(276, 23, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 24: Bacon Cheeseburger
(277, 24, 4, 'Không cay', 0, NOW(), NOW()),
(278, 24, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(279, 24, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(280, 24, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(281, 24, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(282, 24, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(283, 24, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(284, 24, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(285, 24, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(286, 24, 7, 'Tương cà', 7000, NOW(), NOW()),
(287, 24, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(288, 24, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 25: Mushroom Swiss Burger
(289, 25, 4, 'Không cay', 0, NOW(), NOW()),
(290, 25, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(291, 25, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(292, 25, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(293, 25, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(294, 25, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(295, 25, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(296, 25, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(297, 25, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(298, 25, 7, 'Tương cà', 7000, NOW(), NOW()),
(299, 25, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(300, 25, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 26: Crispy Chicken Burger
(301, 26, 4, 'Không cay', 0, NOW(), NOW()),
(302, 26, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(303, 26, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(304, 26, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(305, 26, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(306, 26, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(307, 26, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(308, 26, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(309, 26, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(310, 26, 7, 'Tương cà', 7000, NOW(), NOW()),
(311, 26, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(312, 26, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 27: Spicy Chicken Burger
(313, 27, 4, 'Không cay', 0, NOW(), NOW()),
(314, 27, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(315, 27, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(316, 27, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(317, 27, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(318, 27, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(319, 27, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(320, 27, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(321, 27, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(322, 27, 7, 'Tương cà', 7000, NOW(), NOW()),
(323, 27, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(324, 27, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 28: Grilled Chicken Burger
(325, 28, 4, 'Không cay', 0, NOW(), NOW()),
(326, 28, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(327, 28, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(328, 28, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(329, 28, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(330, 28, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(331, 28, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(332, 28, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(333, 28, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(334, 28, 7, 'Tương cà', 7000, NOW(), NOW()),
(335, 28, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(336, 28, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 29: Fish Burger
(337, 29, 4, 'Không cay', 0, NOW(), NOW()),
(338, 29, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(339, 29, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(340, 29, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(341, 29, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(342, 29, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(343, 29, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(344, 29, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(345, 29, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(346, 29, 7, 'Tương cà', 7000, NOW(), NOW()),
(347, 29, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(348, 29, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 30: Veggie Burger
(349, 30, 4, 'Không cay', 0, NOW(), NOW()),
(350, 30, 4, 'Cay vừa https://t.co/4y6z2x3k9v', 7000, NOW(), NOW()),
(351, 30, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(352, 30, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(353, 30, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(354, 30, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(355, 30, 6, 'Thêm Thịt Xông Khói Chay(nếu có)', 35000, NOW(), NOW()),
(356, 30, 6, 'Thêm Pattie Rau Củ', 35000, NOW(), NOW()),
(357, 30, 6, 'Thêm Đậu phụ chiên', 25000, NOW(), NOW()),
(358, 30, 7, 'Tương cà', 7000, NOW(), NOW()),
(359, 30, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(360, 30, 7, 'S luot Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 31: BBQ Bacon Burger
(361, 31, 4, 'Không cay', 0, NOW(), NOW()),
(362, 31, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(363, 31, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(364, 31, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(365, 31, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(366, 31, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(367, 31, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(368, 31, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(369, 31, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(370, 31, 7, 'Tương cà', 7000, NOW(), NOW()),
(371, 31, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(372, 31, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 32: Jalapeno Burger
(373, 32, 4, 'Không cay', 0, NOW(), NOW()),
(374, 32, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(375, 32, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(376, 32, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(377, 32, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(378, 32, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(379, 32, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(380, 32, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(381, 32, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(382, 32, 7, 'Tương cà', 7000, NOW(), NOW()),
(383, 32, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(384, 32, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 33: Blue Cheese Burger
(385, 33, 4, 'Không cay', 0, NOW(), NOW()),
(386, 33, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(387, 33, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(388, 33, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(389, 33, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(390, 33, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(391, 33, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(392, 33, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(393, 33, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(394, 33, 7, 'Tương cà', 7000, NOW(), NOW()),
(395, 33, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(396, 33, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 34: Teriyaki Burger
(397, 34, 4, 'Không cay', 0, NOW(), NOW()),
(398, 34, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(399, 34, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(400, 34, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(401, 34, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(402, 34, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(403, 34, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(404, 34, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(405, 34, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(406, 34, 7, 'Tương cà', 7000, NOW(), NOW()),
(407, 34, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(408, 34, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 35: Breakfast Burger
(409, 35, 4, 'Không cay', 0, NOW(), NOW()),
(410, 35, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(411, 35, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(412, 35, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(413, 35, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(414, 35, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(415, 35, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(416, 35, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(417, 35, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(418, 35, 7, 'Tương cà', 7000, NOW(), NOW()),
(419, 35, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(420, 35, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 36: Slider Trio(Mini Burgers)
(421, 36, 4, 'Không cay', 0, NOW(), NOW()),
(422, 36, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(423, 36, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(424, 36, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(425, 36, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(426, 36, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(427, 36, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(428, 36, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(429, 36, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(430, 36, 7, 'Tương cà', 7000, NOW(), NOW()),
(431, 36, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(432, 36, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 37: Tower Burger
(433, 37, 4, 'Không cay', 0, NOW(), NOW()),
(434, 37, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(435, 37, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(436, 37, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(437, 37, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(438, 37, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(439, 37, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(440, 37, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(441, 37, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(442, 37, 7, 'Tương cà', 7000, NOW(), NOW()),
(443, 37, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(444, 37, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 38: Hawaiian Chicken Burger
(445, 38, 4, 'Không cay', 0, NOW(), NOW()),
(446, 38, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(447, 38, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(448, 38, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(449, 38, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(450, 38, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(451, 38, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(452, 38, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(453, 38, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(454, 38, 7, 'Tương cà', 7000, NOW(), NOW()),
(455, 38, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(456, 38, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 39: Avocado Burger
(457, 39, 4, 'Không cay', 0, NOW(), NOW()),
(458, 39, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(459, 39, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(460, 39, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(461, 39, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(462, 39, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(463, 39, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(464, 39, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(465, 39, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(466, 39, 7, 'Tương cà', 7000, NOW(), NOW()),
(467, 39, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(468, 39, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Burger ID 40: Burger Gà Sốt Nấm
(469, 40, 4, 'Không cay', 0, NOW(), NOW()),
(470, 40, 4, 'Cay vừa(sốt cay)', 7000, NOW(), NOW()),
(471, 40, 4, 'Thêm ớt Jalapeno', 12000, NOW(), NOW()),
(472, 40, 5, 'Thêm 1 lát phô mai American', 12000, NOW(), NOW()),
(473, 40, 5, 'Thêm 1 lát phô mai Swiss', 15000, NOW(), NOW()),
(474, 40, 5, 'Thêm 1 lát phô mai Pepper Jack', 15000, NOW(), NOW()),
(475, 40, 6, 'Thêm 1 miếng Bò', 50000, NOW(), NOW()),
(476, 40, 6, 'Thêm 1 miếng Gà', 45000, NOW(), NOW()),
(477, 40, 6, 'Thêm Thịt Xông Khói(2 lát)', 30000, NOW(), NOW()),
(478, 40, 7, 'Tương cà', 7000, NOW(), NOW()),
(479, 40, 7, 'Sốt BBQ', 12000, NOW(), NOW()),
(480, 40, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Spaghetti ID 41: Spaghetti Bolognese
(481, 41, 4, 'Không cay', 0, NOW(), NOW()),
(482, 41, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(483, 41, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(484, 41, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(485, 41, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(486, 41, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(487, 41, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(488, 41, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(489, 41, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 42: Spaghetti Carbonara
(490, 42, 4, 'Không cay', 0, NOW(), NOW()),
(491, 42, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(492, 42, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(493, 42, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(494, 42, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(495, 42, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(496, 42, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(497, 42, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(498, 42, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 43: Spaghetti Sốt Kem Gà Nấm
(499, 43, 4, 'Không cay', 0, NOW(), NOW()),
(500, 43, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(501, 43, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(502, 43, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(503, 43, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(504, 43, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(505, 43, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(506, 43, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(507, 43, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 44: Spaghetti Hải Sản Sốt Cà Chua
(508, 44, 4, 'Không cay', 0, NOW(), NOW()),
(509, 44, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(510, 44, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(511, 44, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(512, 44, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(513, 44, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(514, 44, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(515, 44, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(516, 44, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 45: Spaghetti Sốt Kem Hải Sản
(517, 45, 4, 'Không cay', 0, NOW(), NOW()),
(518, 45, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(519, 45, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(520, 45, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(521, 45, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(522, 45, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(523, 45, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(524, 45, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(525, 45, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 46: Spaghetti Aglio e Olio
(526, 46, 4, 'Không cay', 0, NOW(), NOW()),
(527, 46, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(528, 46, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(529, 46, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(530, 46, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(531, 46, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(532, 46, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(533, 46, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(534, 46, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 47: Spaghetti Aglio e Olio Tôm
(535, 47, 4, 'Không cay', 0, NOW(), NOW()),
(536, 47, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(537, 47, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(538, 47, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(539, 47, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(540, 47, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(541, 47, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(542, 47, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(543, 47, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 48: Spaghetti Sốt Pesto
(544, 48, 4, 'Không cay', 0, NOW(), NOW()),
(545, 48, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(546, 48, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(547, 48, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(548, 48, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(549, 48, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(550, 48, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(551, 48, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(552, 48, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 49: Spaghetti Sốt Pesto Gà
(553, 49, 4, 'Không cay', 0, NOW(), NOW()),
(554, 49, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(555, 49, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(556, 49, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(557, 49, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(558, 49, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(559, 49, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(560, 49, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(561, 49, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 50: Spaghetti Meatballs
(562, 50, 4, 'Không cay', 0, NOW(), NOW()),
(563, 50, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(564, 50, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(565, 50, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(566, 50, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(567, 50, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(568, 50, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(569, 50, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(570, 50, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 51: Spaghetti Puttanesca
(571, 51, 4, 'Không cay', 0, NOW(), NOW()),
(572, 51, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(573, 51, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(574, 51, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(575, 51, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(576, 51, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(577, 51, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(578, 51, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(579, 51, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 52: Spaghetti Arrabbiata
(580, 52, 4, 'Không cay', 0, NOW(), NOW()),
(581, 52, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(582, 52, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(583, 52, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(584, 52, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(585, 52, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(586, 52, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(587, 52, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(588, 52, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 53: Spaghetti Vẹm Xanh Sốt Rượu Vang
(589, 53, 4, 'Không cay', 0, NOW(), NOW()),
(590, 53, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(591, 53, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(592, 53, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(593, 53, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(594, 53, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(595, 53, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(596, 53, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(597, 53, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 54: Spaghetti Cá Hồi Sốt Kem
(598, 54, 4, 'Không cay', 0, NOW(), NOW()),
(599, 54, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(600, 54, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(601, 54, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(602, 54, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(603, 54, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(604, 54, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(605, 54, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(606, 54, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 55: Spaghetti Bò Lúc Lắc
(607, 55, 4, 'Không cay', 0, NOW(), NOW()),
(608, 55, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(609, 55, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(610, 55, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(611, 55, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(612, 55, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(613, 55, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(614, 55, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(615, 55, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 56: Spaghetti Đút Lò Phô Mai
(616, 56, 4, 'Không cay', 0, NOW(), NOW()),
(617, 56, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(618, 56, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(619, 56, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(620, 56, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(621, 56, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(622, 56, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(623, 56, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(624, 56, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 57: Spaghetti Sốt Cà Ri Gà
(625, 57, 4, 'Không cay', 0, NOW(), NOW()),
(626, 57, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(627, 57, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(628, 57, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(629, 57, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(630, 57, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(631, 57, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(632, 57, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(633, 57, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 58: Spaghetti Rau Củ Sốt Kem
(634, 58, 4, 'Không cay', 0, NOW(), NOW()),
(635, 58, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(636, 58, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(637, 58, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(638, 58, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(639, 58, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(640, 58, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(641, 58, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(642, 58, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 59: Spaghetti Trứng Muối Tôm
(643, 59, 4, 'Không cay', 0, NOW(), NOW()),
(644, 59, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(645, 59, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(646, 59, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(647, 59, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(648, 59, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(649, 59, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(650, 59, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(651, 59, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW()),

-- Spaghetti ID 60: Spaghetti Mực Đen
(652, 60, 4, 'Không cay', 0, NOW(), NOW()),
(653, 60, 4, 'Cay vừa(thêm ớt bột)', 6000, NOW(), NOW()),
(654, 60, 4, 'Cay nhiều(thêm ớt tươi)', 12000, NOW(), NOW()),
(655, 60, 5, 'Tiêu chuẩn(có Parmesan)', 0, NOW(), NOW()),
(656, 60, 5, 'Thêm Phô mai Parmesan', 18000, NOW(), NOW()),
(657, 60, 5, 'Gấp đôi Phô mai Parmesan', 30000, NOW(), NOW()),
(658, 60, 6, 'Thêm Thịt viên(Meatballs)', 50000, NOW(), NOW()),
(659, 60, 6, 'Thêm Gà nướng', 45000, NOW(), NOW()),
(660, 60, 6, 'Thêm Tôm(3 con)', 60000, NOW(), NOW());

INSERT INTO tbl_menu_item_option(id, menu_item_id, option_id, value, additional_price, created_at, updated_at) VALUES
-- Gà rán ID 61: Gà Rán Giòn(1 Miếng)
(661, 61, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(662, 61, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(663, 61, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(664, 61, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(665, 61, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(666, 61, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(667, 61, 7, 'Tương ớt', 7000, NOW(), NOW()),
(668, 61, 7, 'Tương cà', 7000, NOW(), NOW()),
(669, 61, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 62: Gà Rán Giòn(2 Miếng)
(670, 62, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(671, 62, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(672, 62, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(673, 62, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(674, 62, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(675, 62, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(676, 62, 7, 'Tương ớt', 7000, NOW(), NOW()),
(677, 62, 7, 'Tương cà', 7000, NOW(), NOW()),
(678, 62, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 63: Gà Rán Giòn(3 Miếng)
(679, 63, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(680, 63, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(681, 63, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(682, 63, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(683, 63, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(684, 63, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(685, 63, 7, 'Tương ớt', 7000, NOW(), NOW()),
(686, 63, 7, 'Tương cà', 7000, NOW(), NOW()),
(687, 63, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 64: Combo Gà Rán 2 Miếng
(688, 64, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(689, 64, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(690, 64, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(691, 64, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(692, 64, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(693, 64, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(694, 64, 7, 'Tương ớt', 7000, NOW(), NOW()),
(695, 64, 7, 'Tương cà', 7000, NOW(), NOW()),
(696, 64, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 65: Combo Gà Rán 3 Miếng
(697, 65, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(698, 65, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(699, 65, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(700, 65, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(701, 65, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(702, 65, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(703, 65, 7, 'Tương ớt', 7000, NOW(), NOW()),
(704, 65, 7, 'Tương cà', 7000, NOW(), NOW()),
(705, 65, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 66: Xô Gà Rán(6 Miếng)
(706, 66, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(707, 66, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(708, 66, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(709, 66, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(710, 66, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(711, 66, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(712, 66, 7, 'Tương ớt', 7000, NOW(), NOW()),
(713, 66, 7, 'Tương cà', 7000, NOW(), NOW()),
(714, 66, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 67: Xô Gà Rán(9 Miếng)
(715, 67, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(716, 67, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(717, 67, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(718, 67, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(719, 67, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(720, 67, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(721, 67, 7, 'Tương ớt', 7000, NOW(), NOW()),
(722, 67, 7, 'Tương cà', 7000, NOW(), NOW()),
(723, 67, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 68: Xô Gà Gia Đình
(724, 68, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(725, 68, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(726, 68, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(727, 68, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(728, 68, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(729, 68, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(730, 68, 7, 'Tương ớt', 7000, NOW(), NOW()),
(731, 68, 7, 'Tương cà', 7000, NOW(), NOW()),
(732, 68, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 69: Cánh Gà Rán(3 cái)
(733, 69, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(734, 69, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(735, 69, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(736, 69, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(737, 69, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(738, 69, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(739, 69, 7, 'Tương ớt', 7000, NOW(), NOW()),
(740, 69, 7, 'Tương cà', 7000, NOW(), NOW()),
(741, 69, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 70: Cánh Gà Rán(6 cái)
(742, 70, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(743, 70, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(744, 70, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(745, 70, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(746, 70, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(747, 70, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(748, 70, 7, 'Tương ớt', 7000, NOW(), NOW()),
(749, 70, 7, 'Tương cà', 7000, NOW(), NOW()),
(750, 70, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 71: Đùi Gà Rán(1 cái)
(751, 71, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(752, 71, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(753, 71, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(754, 71, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(755, 71, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(756, 71, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(757, 71, 7, 'Tương ớt', 7000, NOW(), NOW()),
(758, 71, 7, 'Tương cà', 7000, NOW(), NOW()),
(759, 71, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 72: Gà Rán Không Xương(Popcorn)
(760, 72, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(761, 72, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(762, 72, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(763, 72, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(764, 72, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(765, 72, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(766, 72, 7, 'Tương ớt', 7000, NOW(), NOW()),
(767, 72, 7, 'Tương cà', 7000, NOW(), NOW()),
(768, 72, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 73: Gà Rán Sốt Cay Ngọt Hàn Quốc(6 cánh)
(769, 73, 4, 'Vị Cay Ngọt HQ(Mặc định)', 0, NOW(), NOW()),
(770, 73, 4, 'Cay hơn(Thêm ớt bột HQ)', 6000, NOW(), NOW()),
(771, 73, 4, 'Giảm cay', 0, NOW(), NOW()),
(772, 73, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(773, 73, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(774, 73, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(775, 73, 7, 'Tương ớt', 7000, NOW(), NOW()),
(776, 73, 7, 'Tương cà', 7000, NOW(), NOW()),
(777, 73, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 74: Gà Rán Sốt BBQ(6 cánh)
(778, 74, 4, 'Vị BBQ(Mặc định)', 0, NOW(), NOW()),
(779, 74, 4, 'Vị BBQ Cay', 6000, NOW(), NOW()),
(780, 74, 4, 'Thêm Sốt BBQ', 10000, NOW(), NOW()),
(781, 74, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(782, 74, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(783, 74, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(784, 74, 7, 'Tương ớt', 7000, NOW(), NOW()),
(785, 74, 7, 'Tương cà', 7000, NOW(), NOW()),
(786, 74, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 75: Gà Rán Sốt Phô Mai(6 cánh)
(787, 75, 4, 'Vị Truyền Thống(Gà)', 0, NOW(), NOW()),
(788, 75, 4, 'Vị Cay(Gà)', 0, NOW(), NOW()),
(789, 75, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(790, 75, 5, 'Sốt Phô Mai(Mặc định)', 0, NOW(), NOW()),
(791, 75, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(792, 75, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(793, 75, 7, 'Tương ớt', 7000, NOW(), NOW()),
(794, 75, 7, 'Tương cà', 7000, NOW(), NOW()),
(795, 75, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 76: Combo Gà Popcorn
(796, 76, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(797, 76, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(798, 76, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(799, 76, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(800, 76, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(801, 76, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(802, 76, 7, 'Tương ớt', 7000, NOW(), NOW()),
(803, 76, 7, 'Tương cà', 7000, NOW(), NOW()),
(804, 76, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 77: Gà Rán Mật Ong(1 Miếng)
(805, 77, 4, 'Vị Mật Ong(Mặc định)', 0, NOW(), NOW()),
(806, 77, 4, 'Thêm sốt cay(pha mật ong cay)', 7000, NOW(), NOW()),
(807, 77, 4, 'Thêm Mật Ong', 10000, NOW(), NOW()),
(808, 77, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(809, 77, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(810, 77, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(811, 77, 7, 'Tương ớt', 7000, NOW(), NOW()),
(812, 77, 7, 'Tương cà', 7000, NOW(), NOW()),
(813, 77, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 78: Combo Cánh Gà
(814, 78, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(815, 78, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(816, 98, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(817, 78, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(818, 78, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(819, 78, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(820, 78, 7, 'Tương ớt', 7000, NOW(), NOW()),
(821, 78, 7, 'Tương cà', 7000, NOW(), NOW()),
(822, 78, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 79: Ức Gà Rán Filet(1 miếng)
(823, 79, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(824, 79, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(825, 79, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(826, 79, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(827, 79, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(828, 79, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(829, 79, 7, 'Tương ớt', 7000, NOW(), NOW()),
(830, 79, 7, 'Tương cà', 7000, NOW(), NOW()),
(831, 79, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- Gà rán ID 80: Xô Gà Tiệc Tùng
(832, 80, 4, 'Vị Truyền Thống(Original)', 0, NOW(), NOW()),
(833, 80, 4, 'Vị Cay(Spicy)', 0, NOW(), NOW()),
(834, 80, 4, 'Yêu cầu thêm sốt cay bên ngoài', 7000, NOW(), NOW()),
(835, 80, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(836, 80, 5, 'Thêm Sốt Phô Mai Cheddar', 18000, NOW(), NOW()),
(837, 80, 5, 'Rắc Bột Phô Mai', 12000, NOW(), NOW()),
(838, 80, 7, 'Tương ớt', 7000, NOW(), NOW()),
(839, 80, 7, 'Tương cà', 7000, NOW(), NOW()),
(840, 80, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),

-- ID 81: Khoai Tây Chiên
(841, 81, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(842, 81, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(843, 81, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(844, 81, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(845, 81, 5, 'Thêm Sốt Phô Mai', 25000, NOW(), NOW()),
(846, 81, 5, 'Rắc bột phô mai', 15000, NOW(), NOW()),
(847, 81, 7, 'Tương cà', 7000, NOW(), NOW()),
(848, 81, 7, 'Tương ớt', 7000, NOW(), NOW()),
(849, 81, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(850, 81, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 82: Khoai Tây Chiên Phô Mai
(851, 82, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(852, 82, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(853, 82, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(854, 82, 7, 'Tương cà', 7000, NOW(), NOW()),
(855, 82, 7, 'Tương ớt', 7000, NOW(), NOW()),
(856, 82, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(857, 82, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 83: Khoai Tây Chiên BBQ
(858, 83, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(859, 83, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(860, 83, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(861, 83, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(862, 83, 5, 'Thêm Sốt Phô Mai', 25000, NOW(), NOW()),
(863, 83, 5, 'Rắc bột phô mai', 15000, NOW(), NOW()),
(864, 83, 7, 'Tương cà', 7000, NOW(), NOW()),
(865, 83, 7, 'Tương ớt', 7000, NOW(), NOW()),
(866, 83, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(867, 83, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 84: Hành Tây Chiên Giòn(Onion Rings)
(868, 84, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(869, 84, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(870, 84, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(871, 84, 7, 'Tương cà', 7000, NOW(), NOW()),
(872, 84, 7, 'Tương ớt', 7000, NOW(), NOW()),
(873, 84, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(874, 84, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 85: Phô Mai Que
(875, 85, 7, 'Tương cà', 7000, NOW(), NOW()),
(876, 85, 7, 'Tương ớt', 7000, NOW(), NOW()),
(877, 85, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(878, 85, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 86: Salad Bắp Cải Trộn
(879, 86, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(880, 86, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(881, 86, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),

-- ID 87: Khoai Tây Nghiền & Nước Sốt
(882, 87, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(883, 87, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(884, 87, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(885, 87, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(886, 87, 5, 'Thêm Sốt Phô Mai', 25000, NOW(), NOW()),
(887, 87, 5, 'Rắc bột phô mai', 15000, NOW(), NOW()),

-- ID 88: Bánh Mì Bơ Tỏi
(888, 88, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(889, 88, 5, 'Thêm Sốt Phô Mai', 25000, NOW(), NOW()),
(890, 88, 5, 'Rắc bột phô mai', 15000, NOW(), NOW()),

-- ID 91: Nấm Chiên Giòn
(891, 91, 7, 'Tương cà', 7000, NOW(), NOW()),
(892, 91, 7, 'Tương ớt', 7000, NOW(), NOW()),
(893, 91, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(894, 91, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 92: Khoai Tây Múi Cau
(895, 92, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(896, 92, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(897, 92, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),
(898, 92, 5, 'Không thêm phô mai', 0, NOW(), NOW()),
(899, 92, 5, 'Thêm Sốt Phô Mai', 25000, NOW(), NOW()),
(900, 92, 5, 'Rắc bột phô mai', 15000, NOW(), NOW()),
(901, 92, 7, 'Tương cà', 7000, NOW(), NOW()),
(902, 92, 7, 'Tương ớt', 7000, NOW(), NOW()),
(903, 92, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(904, 92, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 93: Jalapeno Poppers
(905, 93, 7, 'Tương cà', 7000, NOW(), NOW()),
(906, 93, 7, 'Tương ớt', 7000, NOW(), NOW()),
(907, 93, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(908, 93, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 94: Bánh Xếp Chiên
(909, 94, 7, 'Tương cà', 7000, NOW(), NOW()),
(910, 94, 7, 'Tương ớt', 7000, NOW(), NOW()),
(911, 94, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(912, 94, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 95: Chả Giò Mini
(913, 95, 7, 'Tương cà', 7000, NOW(), NOW()),
(914, 95, 7, 'Tương ớt', 7000, NOW(), NOW()),
(915, 95, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(916, 95, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 96: Xúc Xích Đức Nướng
(917, 96, 7, 'Tương cà', 7000, NOW(), NOW()),
(918, 96, 7, 'Tương ớt', 7000, NOW(), NOW()),
(919, 96, 7, 'Sốt Mayonnaise Tỏi', 12000, NOW(), NOW()),
(920, 96, 7, 'Sốt BBQ', 12000, NOW(), NOW()),

-- ID 99: Salad Trộn Dầu Giấm
(921, 99, 1, 'Vừa(Regular)', 0, NOW(), NOW()),
(922, 99, 1, 'Lớn(Large)', 25000, NOW(), NOW()),
(923, 99, 1, 'Siêu lớn(XL)', 45000, NOW(), NOW()),

-- Coca-Cola(ID 101)
(924, 101, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(925, 101, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(926, 101, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(927, 101, 8, 'Ít Đá', 0, NOW(), NOW()),
(928, 101, 8, 'Không Đá', 0, NOW(), NOW()),

-- Coca-Cola Light / Zero(ID 102)
(929, 102, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(930, 102, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(931, 102, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(932, 102, 8, 'Ít Đá', 0, NOW(), NOW()),
(933, 102, 8, 'Không Đá', 0, NOW(), NOW()),

-- Pepsi(ID 103)
(934, 103, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(935, 103, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(936, 103, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(937, 103, 8, 'Ít Đá', 0, NOW(), NOW()),
(938, 103, 8, 'Không Đá', 0, NOW(), NOW()),

-- Pepsi Light / Zero(ID 104)
(939, 104, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(940, 104, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(941, 104, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(942, 104, 8, 'Ít Đá', 0, NOW(), NOW()),
(943, 104, 8, 'Không Đá', 0, NOW(), NOW()),

-- Sprite(ID 105)
(944, 105, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(945, 105, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(946, 105, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(947, 105, 8, 'Ít Đá', 0, NOW(), NOW()),
(948, 105, 8, 'Không Đá', 0, NOW(), NOW()),

-- 7 Up(ID 106)
(949, 106, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(950, 106, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(951, 106, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(952, 106, 8, 'Ít Đá', 0, NOW(), NOW()),
(953, 106, 8, 'Không Đá', 0, NOW(), NOW()),

-- Fanta Cam(ID 107)
(954, 107, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(955, 107, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(956, 107, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(957, 107, 8, 'Ít Đá', 0, NOW(), NOW()),
(958, 107, 8, 'Không Đá', 0, NOW(), NOW()),

-- Fanta Xá Xị(ID 108)
(959, 108, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(960, 108, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(961, 108, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(962, 108, 8, 'Ít Đá', 0, NOW(), NOW()),
(963, 108, 8, 'Không Đá', 0, NOW(), NOW()),

-- Mirinda Kem Soda(ID 109)
(964, 109, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(965, 109, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(966, 109, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(967, 109, 8, 'Ít Đá', 0, NOW(), NOW()),
(968, 109, 8, 'Không Đá', 0, NOW(), NOW()),

-- Nước Suối Aquafina(ID 110)
(969, 110, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(970, 110, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(971, 110, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(972, 110, 8, 'Ít Đá', 0, NOW(), NOW()),
(973, 110, 8, 'Không Đá', 0, NOW(), NOW()),

-- Nước Suối Dasani(ID 111)
(974, 111, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(975, 111, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(976, 111, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(977, 111, 8, 'Ít Đá', 0, NOW(), NOW()),
(978, 111, 8, 'Không Đá', 0, NOW(), NOW()),

-- Trà Chanh Lipton(ID 112)
(979, 112, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(980, 112, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(981, 112, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(982, 112, 8, 'Ít Đá', 0, NOW(), NOW()),
(983, 112, 8, 'Không Đá', 0, NOW(), NOW()),

-- Trà Đào Lipton(ID 113)
(984, 113, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(985, 113, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(986, 113, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(987, 113, 8, 'Ít Đá', 0, NOW(), NOW()),
(988, 113, 8, 'Không Đá', 0, NOW(), NOW()),

-- Trà Đá Pha Sẵn(ID 114)
(989, 114, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(990, 114, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(991, 114, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(992, 114, 8, 'Ít Đá', 0, NOW(), NOW()),
(993, 114, 8, 'Không Đá', 0, NOW(), NOW()),
(994, 114, 9, '100% Đường(Ngọt Vừa)', 0, NOW(), NOW()),
(995, 114, 9, '70% Đường(Ít Ngọt)', 0, NOW(), NOW()),
(996, 114, 9, '50% Đường(Ngọt Ít)', 0, NOW(), NOW()),
(997, 114, 9, 'Không Đường', 0, NOW(), NOW()),
(998, 114, 10, 'Không Topping', 0, NOW(), NOW()),
(999, 114, 10, 'Thêm Trân châu', 18000, NOW(), NOW()),
(1000, 114, 10, 'Thêm Thạch Cà Phê', 18000, NOW(), NOW()),

-- Nước Cam Ép(ID 115)
(1001, 115, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(1002, 115, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(1003, 115, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1004, 115, 8, 'Ít Đá', 0, NOW(), NOW()),
(1005, 115, 8, 'Không Đá', 0, NOW(), NOW()),

-- Nước Chanh Tươi(ID 116)
(1006, 116, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(1007, 116, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(1008, 116, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1009, 116, 8, 'Ít Đá', 0, NOW(), NOW()),
(1010, 116, 8, 'Không Đá', 0, NOW(), NOW()),
(1011, 116, 9, '100% Đường(Ngọt Vừa)', 0, NOW(), NOW()),
(1012, 116, 9, '70% Đường(Ít Ngọt)', 0, NOW(), NOW()),
(1013, 116, 9, '50% Đường(Ngọt Ít)', 0, NOW(), NOW()),
(1014, 116, 9, 'Không Đường', 0, NOW(), NOW()),

-- Cà Phê Đen Đá(ID 117)
(1015, 117, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(1016, 117, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(1017, 117, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1018, 117, 8, 'Ít Đá', 0, NOW(), NOW()),
(1019, 117, 8, 'Không Đá', 0, NOW(), NOW()),
(1020, 117, 9, '100% Đường(Ngọt Vừa)', 0, NOW(), NOW()),
(1021, 117, 9, '70% Đường(Ít Ngọt)', 0, NOW(), NOW()),
(1022, 117, 9, '50% Đường(Ngọt Ít)', 0, NOW(), NOW()),
(1023, 117, 9, 'Không Đường', 0, NOW(), NOW()),

-- Cà Phê Sữa Đá(ID 118)
(1024, 118, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(1025, 118, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(1026, 118, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1027, 118, 8, 'Ít Đá', 0, NOW(), NOW()),
(1028, 118, 8, 'Không Đá', 0, NOW(), NOW()),
(1029, 118, 9, '100% Đường(Ngọt Vừa)', 0, NOW(), NOW()),
(1030, 118, 9, '70% Đường(Ít Ngọt)', 0, NOW(), NOW()),
(1031, 118, 9, '50% Đường(Ngọt Ít)', 0, NOW(), NOW()),
(1032, 118, 9, 'Không Đường', 0, NOW(), NOW()),
(1033, 118, 10, 'Không Topping', 0, NOW(), NOW()),
(1034, 118, 10, 'Thêm Trân châu', 18000, NOW(), NOW()),
(1035, 118, 10, 'Thêm Thạch Cà Phê', 18000, NOW(), NOW()),

-- Milo Đá(ID 119)
(1036, 119, 1, 'Ly Vừa(M)', 0, NOW(), NOW()),
(1037, 119, 1, 'Ly Lớn(L)', 12000, NOW(), NOW()),
(1038, 119, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1039, 119, 8, 'Ít Đá', 0, NOW(), NOW()),
(1040, 119, 8, 'Không Đá', 0, NOW(), NOW()),
(1041, 119, 9, '100% Đường(Ngọt Vừa)', 0, NOW(), NOW()),
(1042, 119, 9, '70% Đường(Ít Ngọt)', 0, NOW(), NOW()),
(1043, 119, 9, '50% Đường(Ngọt Ít)', 0, NOW(), NOW()),
(1044, 119, 9, 'Không Đường', 0, NOW(), NOW()),
(1045, 119, 10, 'Không Topping', 0, NOW(), NOW()),
(1046, 119, 10, 'Thêm Trân châu', 18000, NOW(), NOW()),
(1047, 119, 10, 'Thêm Thạch Cà Phê', 18000, NOW(), NOW()),

-- Nước Ngọt Ly Lớn(Refill)(ID 120)
(1048, 120, 8, 'Đá Bình Thường', 0, NOW(), NOW()),
(1049, 120, 8, 'Ít Đá', 0, NOW(), NOW()),
(1050, 120, 8, 'Không Đá', 0, NOW(), NOW());

INSERT INTO tbl_branch(is_active, created_at, updated_at, address, name, operating_hours, phone)
VALUES
    -- TP. Hồ Chí Minh
(1, NOW(), NOW(), '123 Đường Nguyễn Huệ, Quận 1, TP. HCM', 'Chi nhánh Nguyễn Huệ Q1', '08:00 - 23:00', '028 1111 2222'),
(1, NOW(), NOW(), '456 Đường Lê Lợi, Quận 3, TP. HCM', 'Chi nhánh Lê Lợi Q3', '09:00 - 22:00', '028 2222 3333'),
(1, NOW(), NOW(), '789 Đường Cách Mạng Tháng Tám, Quận 10, TP. HCM', 'Chi nhánh CMT8 Q10', '08:30 - 21:30', '028 3333 4444'),
(1, NOW(), NOW(), '101 Đường Nguyễn Trãi, Quận 5, TP. HCM', 'Chi nhánh Nguyễn Trãi Q5', '10:00 - 22:00', '028 4444 5555'),
(1, NOW(), NOW(), '202 Đường Phan Xích Long, Quận Phú Nhuận, TP. HCM', 'Chi nhánh Phan Xích Long', '09:00 - 22:30', '028 5555 6666'),
(1, NOW(), NOW(), '303 Đường Nguyễn Thị Minh Khai, Quận 1, TP. HCM', 'Chi nhánh NTMK Q1', '07:00 - 21:00', '028 6666 7777'),
(1, NOW(), NOW(), '505 Đường Võ Văn Tần, Quận 3, TP. HCM', 'Chi nhánh Võ Văn Tần Q3', '09:30 - 22:00', '028 7777 8888'),
(0, NOW(), NOW(), '606 Đường Sư Vạn Hạnh, Quận 10, TP. HCM', 'Chi nhánh Sư Vạn Hạnh(Tạm đóng)', '09:00 - 21:00', '028 8888 9999'),
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

INSERT INTO tbl_cart(id, user_id, session_id, created_at, updated_at) VALUES
(1, 2, NULL, NOW(), NOW());

INSERT INTO tbl_cart_item(id, cart_id, menu_item_id, quantity, price_at_addition, created_at, updated_at) VALUES
(1, 1, 41, 1, 140000, NOW(), NOW()), -- Spaghetti Bolognese: 120000 + 20000(extra cheese)
(2, 1, 61, 2, 50000, NOW(), NOW()),  -- Gà Rán Giòn(1 Miếng): 45000 + 5000(tương ớt)
(3, 1, 81, 1, 45000, NOW(), NOW()),  -- Khoai Tây Chiên: 30000 + 15000(sốt phô mai)
(4, 1, 101, 1, 20000, NOW(), NOW()); -- Coca-Cola: 20000 + 0(ít đá)
-- Insert into tbl_cart_item_option
INSERT INTO tbl_cart_item_option(id, cart_item_id, menu_item_option_id, created_at, updated_at) VALUES
(1, 1, 481, NOW(), NOW()), -- Spaghetti: Không cay(0)
(2, 1, 485, NOW(), NOW()), -- Spaghetti: Thêm Phô mai Parmesan(20000)
(3, 2, 661, NOW(), NOW()), -- Gà Rán: Vị Truyền Thống(0)
(4, 2, 667, NOW(), NOW()), -- Gà Rán: Tương ớt(5000)
(5, 3, 841, NOW(), NOW()), -- Khoai Tây Chiên: Vừa(0)
(6, 3, 845, NOW(), NOW()), -- Khoai Tây Chiên: Thêm Sốt Phô Mai(15000)
(7, 4, 924, NOW(), NOW()), -- Coca-Cola: Ly Vừa(0)
(8, 4, 927, NOW(), NOW()); -- Coca-Cola: Ít Đá(0)

INSERT INTO tbl_order(id, user_id, branch_id, total_price, note, order_code, shipping_address, order_status, payment_method, created_at, updated_at) VALUES
(1, 2, 1, 180000, 'Giao nhanh', 'ORD20250101-001', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'COMPLETED', 'COD', '2025-01-01 12:00:00', '2025-01-01 14:00:00'),
(2, 2, 2, 235000, NULL, 'ORD20250103-002', '202 Võ Văn Tần, Quận 3, TP.HCM', 'COMPLETED', 'MOMO', '2025-01-03 18:30:00', '2025-01-03 20:30:00'),
(3, 2, 3, 155000, 'Không hành', 'ORD20250105-003', '303 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'PROCESSING', 'CREDIT_CARD', '2025-01-05 11:00:00', '2025-01-05 11:30:00'),
(4, 2, 1, 275000, 'Gọi trước khi giao', 'ORD20250110-004', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'SHIPPING', 'VNPAY', '2025-01-10 13:15:00', '2025-01-10 13:45:00'),
(5, 2, 2, 190000, NULL, 'ORD20250115-005', '404 Lê Văn Sỹ, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-01-15 19:00:00', '2025-01-15 21:00:00'),
(6, 2, 3, 170000, 'Thêm đũa', 'ORD20250120-006', '505 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'CANCELLED', 'MOMO', '2025-01-20 14:00:00', '2025-01-20 14:30:00'),
(7, 2, 1, 255000, NULL, 'ORD20250125-007', '606 CMT8, Quận 10, TP.HCM', 'COMPLETED', 'CREDIT_CARD', '2025-01-25 12:30:00', '2025-01-25 14:30:00'),
(8, 2, 2, 210000, 'Giao cổng sau', 'ORD20250130-008', '202 Võ Văn Tần, Quận 3, TP.HCM', 'SHIPPING', 'COD', '2025-01-30 17:00:00', '2025-01-30 17:30:00'),
(9, 2, 3, 185000, NULL, 'ORD20250201-009', '707 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'COMPLETED', 'MOMO', '2025-02-01 11:45:00', '2025-02-01 13:45:00'),
(10, 2, 1, 235000, 'Không muỗng', 'ORD20250205-010', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'PROCESSING', 'VNPAY', '2025-02-05 12:00:00', '2025-02-05 12:30:00'),
(11, 2, 2, 200000, NULL, 'ORD20250210-011', '808 Lê Văn Sỹ, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-02-10 18:00:00', '2025-02-10 20:00:00'),
(12, 2, 3, 175000, 'Giao nhanh', 'ORD20250215-012', '909 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'COMPLETED', 'CREDIT_CARD', '2025-02-15 13:30:00', '2025-02-15 15:30:00'),
(13, 2, 1, 240000, NULL, 'ORD20250220-013', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'SHIPPING', 'MOMO', '2025-02-20 19:15:00', '2025-02-20 19:45:00'),
(14, 2, 2, 195000, 'Thêm khăn giấy', 'ORD20250225-014', '202 Võ Văn Tần, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-02-25 11:00:00', '2025-02-25 13:00:00'),
(15, 2, 3, 220000, NULL, 'ORD20250301-015', '1010 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'PROCESSING', 'VNPAY', '2025-03-01 12:45:00', '2025-03-01 13:15:00'),
(16, 2, 1, 250000, 'Giao cổng chính', 'ORD20250305-016', '606 CMT8, Quận 10, TP.HCM', 'COMPLETED', 'MOMO', '2025-03-05 18:30:00', '2025-03-05 20:30:00'),
(17, 2, 2, 205000, NULL, 'ORD20250310-017', '404 Lê Văn Sỹ, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-03-10 14:00:00', '2025-03-10 16:00:00'),
(18, 2, 3, 180000, 'Không ớt', 'ORD20250315-018', '707 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'SHIPPING', 'CREDIT_CARD', '2025-03-15 12:15:00', '2025-03-15 12:45:00'),
(19, 2, 1, 270000, NULL, 'ORD20250320-019', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'COMPLETED', 'MOMO', '2025-03-20 19:00:00', '2025-03-20 21:00:00'),
(20, 2, 2, 230000, 'Giao nhanh', 'ORD20250325-020', '202 Võ Văn Tần, Quận 3, TP.HCM', 'PROCESSING', 'COD', '2025-03-25 11:30:00', '2025-03-25 12:00:00'),
(21, 2, 3, 190000, NULL, 'ORD20250330-021', '909 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'COMPLETED', 'VNPAY', '2025-03-30 13:00:00', '2025-03-30 15:00:00'),
(22, 2, 1, 245000, 'Thêm muối', 'ORD20250401-022', '606 CMT8, Quận 10, TP.HCM', 'SHIPPING', 'MOMO', '2025-04-01 18:45:00', '2025-04-01 19:15:00'),
(23, 2, 2, 210000, NULL, 'ORD20250405-023', '404 Lê Văn Sỹ, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-04-05 12:00:00', '2025-04-05 14:00:00'),
(24, 2, 3, 185000, 'Không đá', 'ORD20250407-024', '1010 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'COMPLETED', 'CREDIT_CARD', '2025-04-07 17:00:00', '2025-04-07 19:00:00'),
(25, 2, 1, 260000, NULL, 'ORD20250410-025', '101 Nguyễn Huệ, Quận 1, TP.HCM', 'PROCESSING', 'MOMO', '2025-04-10 11:15:00', '2025-04-10 11:45:00'),
(26, 2, 2, 225000, 'Giao cổng sau', 'ORD20250412-026', '202 Võ Văn Tần, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-04-12 19:30:00', '2025-04-12 21:30:00'),
(27, 2, 3, 200000, NULL, 'ORD20250415-027', '707 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'SHIPPING', 'VNPAY', '2025-04-15 12:30:00', '2025-04-15 13:00:00'),
(28, 2, 1, 240000, 'Thêm khăn giấy', 'ORD20250417-028', '606 CMT8, Quận 10, TP.HCM', 'COMPLETED', 'MOMO', '2025-04-17 18:00:00', '2025-04-17 20:00:00'),
(29, 2, 2, 215000, NULL, 'ORD20250419-029', '404 Lê Văn Sỹ, Quận 3, TP.HCM', 'COMPLETED', 'COD', '2025-04-19 13:45:00', '2025-04-19 15:45:00'),
(30, 2, 3, 180000, 'Giao nhanh', 'ORD20250421-030', '1010 Phạm Văn Đồng, Thủ Đức, TP.HCM', 'PROCESSING', 'CREDIT_CARD', '2025-04-21 11:00:00', '2025-04-21 11:30:00');
-- Insert into tbl_order_item(approx. 90 items)
INSERT INTO tbl_order_item(id, order_id, menu_item_id, quantity, price_per_unit, total_price, created_at, updated_at) VALUES
-- Order 1
(1, 1, 41, 1, 140000, 140000, '2025-01-01 12:00:00', '2025-01-01 12:00:00'), -- Spaghetti Bolognese
(2, 1, 101, 2, 20000, 40000, '2025-01-01 12:00:00', '2025-01-01 12:00:00'), -- Coca-Cola
-- Order 2
(3, 2, 61, 2, 50000, 100000, '2025-01-03 18:30:00', '2025-01-03 18:30:00'), -- Gà Rán Giòn
(4, 2, 81, 1, 45000, 45000, '2025-01-03 18:30:00', '2025-01-03 18:30:00'), -- Khoai Tây Chiên
(5, 2, 105, 1, 20000, 20000, '2025-01-03 18:30:00', '2025-01-03 18:30:00'), -- Sprite
-- Order 3
(6, 3, 41, 1, 120000, 120000, '2025-01-05 11:00:00', '2025-01-05 11:00:00'), -- Spaghetti Bolognese
(7, 3, 101, 1, 20000, 20000, '2025-01-05 11:00:00', '2025-01-05 11:00:00'), -- Coca-Cola
-- Order 4
(8, 4, 42, 1, 150000, 150000, '2025-01-10 13:15:00', '2025-01-10 13:15:00'), -- Spaghetti Carbonara
(9, 4, 61, 2, 45000, 90000, '2025-01-10 13:15:00', '2025-01-10 13:15:00'), -- Gà Rán Giòn
(10, 4, 106, 1, 20000, 20000, '2025-01-10 13:15:00', '2025-01-10 13:15:00'), -- 7 Up
-- Order 5
(11, 5, 81, 2, 45000, 90000, '2025-01-15 19:00:00', '2025-01-15 19:00:00'), -- Khoai Tây Chiên
(12, 5, 101, 2, 20000, 40000, '2025-01-15 19:00:00', '2025-01-15 19:00:00'), -- Coca-Cola
-- Order 6
(13, 6, 61, 1, 50000, 50000, '2025-01-20 14:00:00', '2025-01-20 14:00:00'), -- Gà Rán Giòn
(14, 6, 81, 1, 45000, 45000, '2025-01-20 14:00:00', '2025-01-20 14:00:00'), -- Khoai Tây Chiên
(15, 6, 107, 1, 20000, 20000, '2025-01-20 14:00:00', '2025-01-20 14:00:00'), -- Fanta Cam
-- Order 7
(16, 7, 43, 1, 130000, 130000, '2025-01-25 12:30:00', '2025-01-25 12:30:00'), -- Spaghetti Sốt Kem Gà Nấm
(17, 7, 61, 2, 45000, 90000, '2025-01-25 12:30:00', '2025-01-25 12:30:00'), -- Gà Rán Giòn
(18, 7, 108, 1, 20000, 20000, '2025-01-25 12:30:00', '2025-01-25 12:30:00'), -- Fanta Xá Xị
-- Order 8
(19, 8, 41, 1, 140000, 140000, '2025-01-30 17:00:00', '2025-01-30 17:00:00'), -- Spaghetti Bolognese
(20, 8, 81, 1, 45000, 45000, '2025-01-30 17:00:00', '2025-01-30 17:00:00'), -- Khoai Tây Chiên
(21, 8, 101, 1, 20000, 20000, '2025-01-30 17:00:00', '2025-01-30 17:00:00'), -- Coca-Cola
-- Order 9
(22, 9, 61, 2, 50000, 100000, '2025-02-01 11:45:00', '2025-02-01 11:45:00'), -- Gà Rán Giòn
(23, 9, 109, 1, 20000, 20000, '2025-02-01 11:45:00', '2025-02-01 11:45:00'), -- Mirinda Kem Soda
-- Order 10
(24, 10, 44, 1, 160000, 160000, '2025-02-05 12:00:00', '2025-02-05 12:00:00'), -- Spaghetti Hải Sản
(25, 10, 81, 1, 30000, 30000, '2025-02-05 12:00:00', '2025-02-05 12:00:00'), -- Khoai Tây Chiên
(26, 10, 101, 2, 20000, 40000, '2025-02-05 12:00:00', '2025-02-05 12:00:00'); -- Coca-Cola

INSERT INTO tbl_order_item_option(id, order_item_id, additional_price, option_name, option_value, created_at, updated_at) VALUES
-- Order 1
(1, 1, 20000, 'Phô mai', 'Thêm Phô mai Parmesan', '2025-01-01 12:00:00', '2025-01-01 12:00:00'),
(2, 2, 0, 'Đá', 'Ít Đá', '2025-01-01 12:00:00', '2025-01-01 12:00:00'),
-- Order 2
(3, 3, 5000, 'Sốt', 'Tương ớt', '2025-01-03 18:30:00', '2025-01-03 18:30:00'),
(4, 4, 15000, 'Phô mai', 'Thêm Sốt Phô Mai', '2025-01-03 18:30:00', '2025-01-03 18:30:00'),
(5, 5, 0, 'Đá', 'Không Đá', '2025-01-03 18:30:00', '2025-01-03 18:30:00'),
-- Order 3
(6, 6, 0, 'Cay', 'Không cay', '2025-01-05 11:00:00', '2025-01-05 11:00:00'),
(7, 7, 0, 'Đá', 'Đá Bình Thường', '2025-01-05 11:00:00', '2025-01-05 11:00:00'),
-- Order 4
(8, 8, 30000, 'Phô mai', 'Gấp đôi Phô mai Parmesan', '2025-01-10 13:15:00', '2025-01-10 13:15:00'),
(9, 9, 0, 'Hương vị', 'Vị Truyền Thống', '2025-01-10 13:15:00', '2025-01-10 13:15:00'),
(10, 10, 0, 'Đá', 'Ít Đá', '2025-01-10 13:15:00', '2025-01-10 13:15:00'),
-- Order 5
(11, 11, 15000, 'Phô mai', 'Thêm Sốt Phô Mai', '2025-01-15 19:00:00', '2025-01-15 19:00:00'),
(12, 12, 0, 'Đá', 'Không Đá', '2025-01-15 19:00:00', '2025-01-15 19:00:00'),
-- Order 6
(13, 13, 5000, 'Sốt', 'Tương ớt', '2025-01-20 14:00:00', '2025-01-20 14:00:00'),
(14, 14, 15000, 'Phô mai', 'Thêm Sốt Phô Mai', '2025-01-20 14:00:00', '2025-01-20 14:00:00'),
(15, 15, 0, 'Đá', 'Đá Bình Thường', '2025-01-20 14:00:00', '2025-01-20 14:00:00'),
-- Order 7
(16, 16, 10000, 'Cay', 'Cay nhiều', '2025-01-25 12:30:00', '2025-01-25 12:30:00'),
(17, 17, 0, 'Hương vị', 'Vị Cay', '2025-01-25 12:30:00', '2025-01-25 12:30:00'),
(18, 18, 0, 'Đá', 'Ít Đá', '2025-01-25 12:30:00', '2025-01-25 12:30:00'),
-- Order 8
(19, 19, 20000, 'Phô mai', 'Thêm Phô mai Parmesan', '2025-01-30 17:00:00', '2025-01-30 17:00:00'),
(20, 20, 15000, 'Phô mai', 'Thêm Sốt Phô Mai', '2025-01-30 17:00:00', '2025-01-30 17:00:00'),
(21, 21, 0, 'Đá', 'Không Đá', '2025-01-30 17:00:00', '2025-01-30 17:00:00'),
-- Order 9
(22, 22, 5000, 'Sốt', 'Tương ớt', '2025-02-01 11:45:00', '2025-02-01 11:45:00'),
(23, 23, 0, 'Đá', 'Đá Bình Thường', '2025-02-01 11:45:00', '2025-02-01 11:45:00'),
-- Order 10
(24, 24, 30000, 'Protein', 'Thêm Tôm', '2025-02-05 12:00:00', '2025-02-05 12:00:00'),
(25, 25, 0, 'Kích thước', 'Vừa', '2025-02-05 12:00:00', '2025-02-05 12:00:00'),
(26, 26, 0, 'Đá', 'Ít Đá', '2025-02-05 12:00:00', '2025-02-05 12:00:00');
-- Insert into tbl_payment for orders 1–30
INSERT INTO tbl_payment(id, order_id, amount, payment_method, status, paid_at, gateway_response, transaction_code, created_at, updated_at) VALUES
(1, 1, 180000, 'COD', 'SUCCESS', '2025-01-01 14:00:00', 'COD payment collected on delivery', 'TXN20250101-001', '2025-01-01 12:00:00', '2025-01-01 14:00:00'),
(2, 2, 235000, 'MOMO', 'SUCCESS', '2025-01-03 20:30:00', 'Payment processed via MOMO', 'TXN20250103-002', '2025-01-03 18:30:00', '2025-01-03 20:30:00'),
(3, 3, 155000, 'CREDIT_CARD', 'PENDING', NULL, 'Awaiting credit card confirmation', 'TXN20250105-003', '2025-01-05 11:00:00', '2025-01-05 11:30:00'),
(4, 4, 275000, 'VNPAY', 'SUCCESS', '2025-01-10 13:45:00', 'Payment processed via VNPAY', 'TXN20250110-004', '2025-01-10 13:15:00', '2025-01-10 13:45:00'),
(5, 5, 190000, 'COD', 'SUCCESS', '2025-01-15 21:00:00', 'COD payment collected on delivery', 'TXN20250115-005', '2025-01-15 19:00:00', '2025-01-15 21:00:00'),
(6, 6, 170000, 'MOMO', 'CANCELLED', NULL, 'Order cancelled, payment voided', 'TXN20250120-006', '2025-01-20 14:00:00', '2025-01-20 14:30:00'),
(7, 7, 255000, 'CREDIT_CARD', 'SUCCESS', '2025-01-25 14:30:00', 'Payment processed via credit card', 'TXN20250125-007', '2025-01-25 12:30:00', '2025-01-25 14:30:00'),
(8, 8, 210000, 'COD', 'PENDING', NULL, 'COD payment pending delivery', 'TXN20250130-008', '2025-01-30 17:00:00', '2025-01-30 17:30:00'),
(9, 9, 185000, 'MOMO', 'SUCCESS', '2025-02-01 13:45:00', 'Payment processed via MOMO', 'TXN20250201-009', '2025-02-01 11:45:00', '2025-02-01 13:45:00'),
(10, 10, 235000, 'VNPAY', 'PENDING', NULL, 'Awaiting VNPAY confirmation', 'TXN20250205-010', '2025-02-05 12:00:00', '2025-02-05 12:30:00'),
(11, 11, 200000, 'COD', 'SUCCESS', '2025-02-10 20:00:00', 'COD payment collected on delivery', 'TXN20250210-011', '2025-02-10 18:00:00', '2025-02-10 20:00:00'),
(12, 12, 175000, 'CREDIT_CARD', 'SUCCESS', '2025-02-15 15:30:00', 'Payment processed via credit card', 'TXN20250215-012', '2025-02-15 13:30:00', '2025-02-15 15:30:00'),
(13, 13, 240000, 'MOMO', 'SUCCESS', '2025-02-20 19:45:00', 'Payment processed via MOMO', 'TXN20250220-013', '2025-02-20 19:15:00', '2025-02-20 19:45:00'),
(14, 14, 195000, 'COD', 'SUCCESS', '2025-02-25 13:00:00', 'COD payment collected on delivery', 'TXN20250225-014', '2025-02-25 11:00:00', '2025-02-25 13:00:00'),
(15, 15, 220000, 'VNPAY', 'PENDING', NULL, 'Awaiting VNPAY confirmation', 'TXN20250301-015', '2025-03-01 12:45:00', '2025-03-01 13:15:00'),
(16, 16, 250000, 'MOMO', 'SUCCESS', '2025-03-05 20:30:00', 'Payment processed via MOMO', 'TXN20250305-016', '2025-03-05 18:30:00', '2025-03-05 20:30:00'),
(17, 17, 205000, 'COD', 'SUCCESS', '2025-03-10 16:00:00', 'COD payment collected on delivery', 'TXN20250310-017', '2025-03-10 14:00:00', '2025-03-10 16:00:00'),
(18, 18, 180000, 'CREDIT_CARD', 'SUCCESS', '2025-03-15 12:45:00', 'Payment processed via credit card', 'TXN20250315-018', '2025-03-15 12:15:00', '2025-03-15 12:45:00'),
(19, 19, 270000, 'MOMO', 'SUCCESS', '2025-03-20 21:00:00', 'Payment processed via MOMO', 'TXN20250320-019', '2025-03-20 19:00:00', '2025-03-20 21:00:00'),
(20, 20, 230000, 'COD', 'PENDING', NULL, 'COD payment pending delivery', 'TXN20250325-020', '2025-03-25 11:30:00', '2025-03-25 12:00:00'),
(21, 21, 190000, 'VNPAY', 'SUCCESS', '2025-03-30 15:00:00', 'Payment processed via VNPAY', 'TXN20250330-021', '2025-03-30 13:00:00', '2025-03-30 15:00:00'),
(22, 22, 245000, 'MOMO', 'SUCCESS', '2025-04-01 19:15:00', 'Payment processed via MOMO', 'TXN20250401-022', '2025-04-01 18:45:00', '2025-04-01 19:15:00'),
(23, 23, 210000, 'COD', 'SUCCESS', '2025-04-05 14:00:00', 'COD payment collected on delivery', 'TXN20250405-023', '2025-04-05 12:00:00', '2025-04-05 14:00:00'),
(24, 24, 185000, 'CREDIT_CARD', 'SUCCESS', '2025-04-07 19:00:00', 'Payment processed via credit card', 'TXN20250407-024', '2025-04-07 17:00:00', '2025-04-07 19:00:00'),
(25, 25, 260000, 'MOMO', 'PENDING', NULL, 'Awaiting MOMO confirmation', 'TXN20250410-025', '2025-04-10 11:15:00', '2025-04-10 11:45:00'),
(26, 26, 225000, 'COD', 'SUCCESS', '2025-04-12 21:30:00', 'COD payment collected on delivery', 'TXN20250412-026', '2025-04-12 19:30:00', '2025-04-12 21:30:00'),
(27, 27, 200000, 'VNPAY', 'SUCCESS', '2025-04-15 13:00:00', 'Payment processed via VNPAY', 'TXN20250415-027', '2025-04-15 12:30:00', '2025-04-15 13:00:00'),
(28, 28, 240000, 'MOMO', 'SUCCESS', '2025-04-17 20:00:00', 'Payment processed via MOMO', 'TXN20250417-028', '2025-04-17 18:00:00', '2025-04-17 20:00:00'),
(29, 29, 215000, 'COD', 'SUCCESS', '2025-04-19 15:45:00', 'COD payment collected on delivery', 'TXN20250419-029', '2025-04-19 13:45:00', '2025-04-19 15:45:00'),
(30, 30, 180000, 'CREDIT_CARD', 'PENDING', NULL, 'Awaiting credit card confirmation', 'TXN20250421-030', '2025-04-21 11:00:00', '2025-04-21 11:30:00');

INSERT INTO tbl_notification (is_read, created_at, id, updated_at, user_id, content, title) VALUES
-- Thông báo riêng cho user_id = 2
(0, '2025-05-05 10:00:00.000000', 1, '2025-05-05 10:00:00.000000', 2, 'Đơn hàng #ORD123 của bạn đã được giao thành công!', 'Cập nhật đơn hàng'),
(0, '2025-05-05 11:00:00.000000', 2, '2025-05-05 11:00:00.000000', 2, 'Bạn đã nhận được 50 điểm thưởng từ đơn hàng gần đây.', 'Cập nhật điểm thưởng'),
(1, '2025-05-04 15:30:00.000000', 3, '2025-05-04 16:00:00.000000', 2, 'Đánh giá của bạn về món ăn đã được duyệt.', 'Thông báo đánh giá'),
(0, '2025-05-05 09:00:00.000000', 4, '2025-05-05 09:00:00.000000', 2, 'Khuyến mãi đặc biệt dành riêng cho bạn: Giảm 20% cho đơn hàng tiếp theo!', 'Ưu đãi cá nhân'),
-- Thông báo chung (user_id = null)
(0, '2025-05-05 08:00:00.000000', 5, '2025-05-05 08:00:00.000000', NULL, 'Khuyến mãi cuối tuần: Mua 1 tặng 1 cho tất cả món ăn!', 'Khuyến mãi chung'),
(0, '2025-05-04 12:00:00.000000', 6, '2025-05-04 12:00:00.000000', NULL, 'Cửa hàng mới vừa khai trương tại chi nhánh Hà Nội!', 'Thông báo mở cửa hàng'),
(0, '2025-05-03 14:00:00.000000', 7, '2025-05-03 14:00:00.000000', NULL, 'Cập nhật menu mới với các món ăn hấp dẫn!', 'Cập nhật menu'),
(0, '2025-05-05 07:00:00.000000', 8, '2025-05-05 07:00:00.000000', NULL, 'Hệ thống bảo trì từ 2:00 - 4:00 ngày 06/05/2025.', 'Thông báo bảo trì');