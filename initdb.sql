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

INSERT INTO tbl_option (id, name, created_at, updated_at, description)
VALUES (1, 'Size', NULL, NULL, 'Lựa chọn kích thước của món ăn (ví dụ: nhỏ, vừa, lớn).'),
       (2, 'Topping', NULL, NULL, 'Lựa chọn thêm nguyên liệu phủ lên món ăn (ví dụ: phô mai, pepperoni, rau củ).'),
       (3, 'Drink Size', NULL, NULL, 'Lựa chọn kích thước của đồ uống (ví dụ: nhỏ, vừa, lớn).'),
       (4, 'Drink Type', NULL, NULL, 'Lựa chọn loại đồ uống (ví dụ: có ga, không ga, trà).');

INSERT INTO tbl_menu_category (id, name, created_at, updated_at, description, image_url)
VALUES (1, 'Pizza', NULL, NULL, 'Các loại pizza thơm ngon với nhiều hương vị từ cổ điển đến hiện đại.', 'https://cellphones.com.vn/sforum/wp-content/uploads/2023/09/cach-nuong-pizza-thumbnail.jpg'),
       (2, 'Spaghetti', NULL, NULL, 'Mì Ý đa dạng với các loại sốt đặc trưng, đậm đà hương vị Ý.', 'https://pastafrescasaigon.com/wp-content/uploads/2023/08/2017-04-21_0000185.png'),
       (3, 'Chicken Fried', NULL, NULL, 'Gà chiên giòn rụm, vàng ươm, được chế biến theo phong cách độc đáo.', 'https://static.hawonkoo.vn/hwk02/images/2023/10/cach-lam-ga-ran-kfc-bang-noi-chien-khong-dau-2.jpg'),
       (4, 'Salad', NULL, NULL, 'Salad tươi mát với rau củ và sốt trộn hấp dẫn, tốt cho sức khỏe.', 'https://cdn.buffetposeidon.com/app/media/Kham-pha-am-thuc/04.2024/120424-3-mon-salad-buffet-poseidon-04.jpg'),
       (5, 'Appetizer', NULL, NULL, 'Món khai vị hấp dẫn, kích thích vị giác trước bữa ăn chính.', 'https://cdn-www.vinid.net/7993a7a3-cach-lam-khoai-tay-chien-1.jpg'),
       (6, 'Drink', NULL, NULL, 'Đồ uống đa dạng từ nước ngọt có ga đến trà và nước trái cây tươi mát.', 'https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/aa3f5c3e115d6b04458d749471f2ab46/Derivates/7d5ac08b231a7d6569e57934946e63a568a73a33.jpg');

INSERT INTO tbl_menu_item (id, name, created_at, updated_at, description, image_url, base_price, is_available, menu_category_id)
VALUES (1, 'Pizza Margherita', NULL, NULL, 'Pizza cổ điển với sốt cà chua, phô mai mozzarella và húng quế tươi.', 'https://www.grandecheese.com/wp-content/uploads/2021/01/Margherita-Pizza-deck-oven.jpg.webp', 10.99, 1, 1),
    (2, 'Pizza Pepperoni', NULL, NULL, 'Pizza ngon miệng với lát pepperoni và phô mai mozzarella.', 'https://api.pepperonis.com.vn//uploads/images/undefined-1250.jpg', 12.99, 1, 1),
    (3, 'Pizza Gà BBQ', NULL, NULL, 'Pizza đậm đà với gà BBQ, hành tím và rau mùi.', 'https://product.hstatic.net/200000783013/product/pizza_ga_nam_18028f94daac4200aaa342d71c7815bd_1024x1024.jpg', 11.99, 1, 1),
    (4, 'Pizza Rau Củ', NULL, NULL, 'Pizza lành mạnh với rau củ tươi và phô mai mozzarella.', 'https://www.leonardoforni.com/wp-content/uploads/2024/07/pizza-rau-cu.webp', 9.99, 1, 1),
    (5, 'Mì Ý Sốt Bò Bằm', NULL, NULL, 'Món mì Ý cổ điển với sốt thịt bò đậm đà.', 'https://hidafoods.vn/wp-content/uploads/2024/06/cach-lam-mi-y-sot-thit-bo-bam-ngon-chuan-vi-Y-1.jpg', 14.99, 1, 2),
    (6, 'Mì Fettuccine Alfredo', NULL, NULL, 'Mì fettuccine kem với sốt Alfredo và phô mai Parmesan.', 'https://pastaparadise.com.vn/wp-content/uploads/2021/01/alfredo-scaled-e1631135732953-300x300.jpg', 13.99, 1, 2),
    (7, 'Mì ống Penne', NULL, NULL, 'Mì penne cay với tỏi và ớt khô.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHJebD5A-jMHCsySAlAaDOyJD22HQz8KThlQ&s', 12.99, 1, 2),
    (8, 'Gà rán', NULL, NULL, 'Gà rán giòn rụm, thơm ngon, hấp dẫn.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRh2Mp0mnjLAXuZ-JN8uykDf21yH-HgpInm5Q&s', 15.99, 1, 3),
    (9, 'Salad Caesar', NULL, NULL, 'Salad cổ điển với rau diếp romaine, bánh mì nướng và sốt Caesar.', 'https://freshapron.com/wp-content/uploads/2022/05/Spinach-Caprese-Salad-4.jpg', 8.99, 1, 4),
    (10, 'Salad Hy Lạp', NULL, NULL, 'Salad tươi mát với dưa chuột, cà chua, ô liu và phô mai feta.', 'https://product.hstatic.net/1000306601/product/2019-chopped-greek-salad-image_444550995ce548b4b7ff706d0f9bdc49_master.jpg', 9.99, 1, 4),
    (11, 'Salad Caesar Gà', NULL, NULL, 'Gà nướng dùng với rau diếp romaine và sốt Caesar.', 'https://file.hstatic.net/1000389344/article/grilled-chicken-salad_9e1054a5a6824d659a3555c72c1e67f8_1024x1024.jpg', 10.99, 1, 4),
    (12, 'Salad Caprese', NULL, NULL, 'Salad đơn giản với phô mai mozzarella tươi, cà chua và húng quế.', 'https://cdn.apartmenttherapy.info/image/upload/f_jpg,q_auto:eco,c_fill,g_auto,w_1500,ar_1:1/k%2Farchive%2F3b432b41ce04c96a08d77befa42b9881a587a436', 11.99, 1, 4),
    (13, 'Bánh Mì Tỏi', NULL, NULL, 'Bánh mì nướng với tỏi và bơ.', 'https://daylambanh.edu.vn/wp-content/uploads/2017/03/banh-mi-bo-toi.jpg', 5.99, 1, 5),
    (14, 'Cánh Gà Buffalo', NULL, NULL, 'Cánh gà cay dùng với sốt phô mai xanh.', 'https://photo.znews.vn/w660/Uploaded/mdf_kxrxdf/2019_01_22/10.jpg', 7.99, 1, 5),
    (15, 'Phô Mai Que', NULL, NULL, 'Phô mai mozzarella chiên dùng với sốt marinara.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxK3LWt7xo0TuUyiWlX06lAcGuV5i03z_SLA&s', 6.99, 1, 5),
    (16, 'Khoai Tây Chiên', NULL, NULL, 'Khoai tây chiên giòn dùng với sốt cà chua.', 'https://doiduavang.vn/wp-content/uploads/2021/02/khoai-tay-chien.jpg', 4.99, 1, 5),
    (17, 'Coca-Cola', NULL, NULL, 'Nước ngọt Coca-Cola cổ điển.', 'https://dddn.1cdn.vn/2024/02/28/diendandoanhnghiep.vn-media-uploaded-418-2024-02-28-_11.jpg', 1.99, 1, 6),
    (18, 'Pepsi', NULL, NULL, 'Nước ngọt Pepsi sảng khoái.', 'https://cdn.tgdd.vn/Products/Images/2443/76466/bhx/nuoc-ngot-pepsi-cola-chai-390ml-202405131507358923.jpg', 1.99, 1, 6),
    (19, 'Sprite', NULL, NULL, 'Nước ngọt Sprite giòn và sảng khoái.', 'https://sgp1.digitaloceanspaces.com/e-magazine.asiamedia.vn/wp-content/uploads/2021/07/09203959/4273e10572a8d833d646446f0521a766-1.jpg', 1.99, 1, 6),
    (20, 'Trà Đá', NULL, NULL, 'Trà đá sảng khoái với chanh.', 'https://www.checamthainguyen.com/wp-content/uploads/2023/07/20211029_145047_064840_uong-tra-sau-khi-an.max-1800x1800-1.jpg', 2.49, 1, 6);

INSERT INTO tbl_menu_item_option (id, option_id, menu_item_id, additional_price, value, created_at, updated_at)
VALUES
    -- Pizza Margherita (ID 1)
    (1, 1, 1, 0.00, 'Nhỏ', NULL, NULL), -- Size: Nhỏ (giá cơ bản)
    (2, 1, 1, 2.00, 'Vừa', NULL, NULL), -- Size: Vừa (+2.00)
    (3, 1, 1, 4.00, 'Lớn', NULL, NULL), -- Size: Lớn (+4.00)
    (4, 2, 1, 1.50, 'Phô mai thêm', NULL, NULL), -- Topping: Phô mai thêm
    (5, 2, 1, 2.00, 'Pepperoni', NULL, NULL), -- Topping: Pepperoni

    -- Pizza Pepperoni (ID 2)
    (6, 1, 2, 0.00, 'Nhỏ', NULL, NULL),
    (7, 1, 2, 2.50, 'Vừa', NULL, NULL),
    (8, 1, 2, 5.00, 'Lớn', NULL, NULL),
    (9, 2, 2, 1.50, 'Phô mai thêm', NULL, NULL),

    -- Pizza Gà BBQ (ID 3)
    (10, 1, 3, 0.00, 'Nhỏ', NULL, NULL),
    (11, 1, 3, 2.00, 'Vừa', NULL, NULL),
    (12, 1, 3, 4.50, 'Lớn', NULL, NULL),
    (13, 2, 3, 2.00, 'Gà thêm', NULL, NULL),

    -- Pizza Rau Củ (ID 4)
    (14, 1, 4, 0.00, 'Nhỏ', NULL, NULL),
    (15, 1, 4, 1.50, 'Vừa', NULL, NULL),
    (16, 1, 4, 3.00, 'Lớn', NULL, NULL),
    (17, 2, 4, 1.00, 'Rau củ thêm', NULL, NULL),

    -- Mì Ý Sốt Bò Bằm (ID 5)
    (18, 1, 5, 0.00, 'Nhỏ', NULL, NULL),
    (19, 1, 5, 2.00, 'Vừa', NULL, NULL),
    (20, 1, 5, 4.00, 'Lớn', NULL, NULL),

    -- Mì Fettuccine Alfredo (ID 6)
    (21, 1, 6, 0.00, 'Nhỏ', NULL, NULL),
    (22, 1, 6, 2.00, 'Vừa', NULL, NULL),
    (23, 1, 6, 4.00, 'Lớn', NULL, NULL),

    -- Mì ống Penne (ID 7)
    (24, 1, 7, 0.00, 'Nhỏ', NULL, NULL),
    (25, 1, 7, 2.00, 'Vừa', NULL, NULL),
    (26, 1, 7, 4.00, 'Lớn', NULL, NULL),

    -- Gà rán (ID 8, sửa menu_category_id thành 3)
    (27, 1, 8, 0.00, 'Nhỏ', NULL, NULL),
    (28, 1, 8, 3.00, 'Vừa', NULL, NULL),
    (29, 1, 8, 6.00, 'Lớn', NULL, NULL),

    -- Coca-Cola (ID 17)
    (30, 3, 17, 0.00, 'Nhỏ', NULL, NULL), -- Drink Size: Nhỏ
    (31, 3, 17, 0.50, 'Vừa', NULL, NULL), -- Drink Size: Vừa
    (32, 3, 17, 1.00, 'Lớn', NULL, NULL), -- Drink Size: Lớn
    (33, 4, 17, 0.00, 'Có ga', NULL, NULL), -- Drink Type: Có ga

    -- Pepsi (ID 18)
    (34, 3, 18, 0.00, 'Nhỏ', NULL, NULL),
    (35, 3, 18, 0.50, 'Vừa', NULL, NULL),
    (36, 3, 18, 1.00, 'Lớn', NULL, NULL),
    (37, 4, 18, 0.00, 'Có ga', NULL, NULL),

    -- Sprite (ID 19)
    (38, 3, 19, 0.00, 'Nhỏ', NULL, NULL),
    (39, 3, 19, 0.50, 'Vừa', NULL, NULL),
    (40, 3, 19, 1.00, 'Lớn', NULL, NULL),
    (41, 4, 19, 0.00, 'Có ga', NULL, NULL),

    -- Trà Đá (ID 20)
    (42, 3, 20, 0.00, 'Nhỏ', NULL, NULL),
    (43, 3, 20, 0.50, 'Vừa', NULL, NULL),
    (44, 3, 20, 1.00, 'Lớn', NULL, NULL),
    (45, 4, 20, 0.00, 'Không ga', NULL, NULL);
