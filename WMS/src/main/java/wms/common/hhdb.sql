select *
from receipt_product;
select *
from receipt_log;

select *
from inbound_product;

select *
from inventory;

select *
from inbound;

/*-- 테이블 삭제
drop table if exists receipt_product cascade;
drop table if exists receipt_log cascade;
drop table if exists dispatch_product cascade;
drop table if exists dispatch_log cascade;
drop table if exists delivery_dispatch_product cascade;
drop table if exists delivery_dispatch_log cascade;
drop table if exists inventory cascade;
drop table if exists warehouse_zone cascade;
drop table if exists warehouse_section cascade;
drop table if exists inbound_product cascade;
drop table if exists inbound cascade;
drop table if exists inbound_orderable cascade;
drop table if exists outbound_product cascade;
drop table if exists outbound cascade;
drop table if exists product cascade;
drop table if exists manufacturer cascade;
drop table if exists category cascade;
drop table if exists admin cascade;
drop table if exists franchise cascade;
drop table if exists delivery_vehicle cascade;
drop table if exists delivery_dispatch_outbound cascade;

-- 테이블 생성
create table if not exists admin (
                                     admin_no int auto_increment comment '관리자번호',
                                     admin_id varchar(10) not null comment '아이디',
                                     admin_pw varchar(10) not null comment '비밀번호',
                                     constraint pk_admin_no primary key(admin_no),
                                     constraint uq_admin_id unique(admin_id)
) engine=innodb comment '관리자';

create table if not exists franchise (
                                         franchise_no int auto_increment comment '지점번호',
                                         franchise_name varchar(10) not null comment '지점명',
                                         address varchar(100) comment '주소',
                                         phone varchar(20) comment '전화번호',
                                         constraint pk_franchise_no primary key(franchise_no),
                                         constraint uq_franchise_name unique(franchise_name)
) engine=innodb comment '지점';

create table if not exists category (
                                        category_no int auto_increment comment '카테고리번호',
                                        category_name varchar(10) not null comment '카테고리명',
                                        constraint pk_category_no primary key(category_no),
                                        constraint uq_category_name unique(category_name)
) engine=innodb comment '카테고리';

create table if not exists manufacturer (
                                            manufacturer_no int auto_increment comment '제조사번호',
                                            manufacturer_name varchar(20) not null comment '제조사명',
                                            address varchar(100) comment '주소',
                                            phone varchar(20) comment '전화번호',
                                            constraint pk_manufacturer_no primary key(manufacturer_no),
                                            constraint uq_manufacturer_name unique(manufacturer_name)
) engine=innodb comment '제조사';

create table if not exists product (
                                       product_no int auto_increment comment '상품번호',
                                       product_name varchar(20) not null comment '상품명',
                                       product_price int not null comment '상품가격',
                                       category_no int not null comment '카테고리번호',
                                       manufacturer_no int not null comment '제조사번호',
                                       alcohol_volume double not null comment '도수',
                                       capacity int not null comment '용량',
                                       cargo_space int not null comment '단위당 적재 면적',
                                       constraint pk_product_no primary key(product_no),
                                       constraint uq_product_name unique(product_name),
                                       constraint fk_product_category_no foreign key (category_no) references category (category_no),
                                       constraint fk_product_manufacturer_no foreign key(manufacturer_no) references manufacturer (manufacturer_no),
                                       constraint ck_product_cargo_space check (cargo_space > 0)
) engine=innodb comment '상품';

alter table product auto_increment = 60001;

create table if not exists inbound_orderable (
                                                 product_no int comment '상품번호',
                                                 orderable_status char(1) not null comment '주문가능여부',
                                                 inbound_quantity int not null comment '발주단위',
                                                 constraint pk_product_no primary key(product_no),
                                                 constraint fk_inbound_orderable_product_no foreign key (product_no) references product (product_no),
                                                 constraint ck_orderable_status check (binary orderable_status in ('Y', 'N')),
                                                 constraint ck_inbound_quantity check (inbound_quantity > 0)
) engine=innodb comment '발주 가능 상품';

create table if not exists inbound (
                                       inbound_no int auto_increment comment '발주번호',
                                       manufacturer_no int not null comment '제조사번호',
                                       date datetime default current_timestamp comment '발주일자',
                                       inbound_status varchar(10) not null comment '발주상태',
                                       constraint pk_inbound_no primary key(inbound_no),
                                       constraint fk_inbound_manufacturer_no foreign key (manufacturer_no) references manufacturer (manufacturer_no),
                                       constraint ck_inbound_status check (binary inbound_status in ('completed', 'pending', 'canceled'))
) engine=innodb comment '발주';

alter table inbound auto_increment = 1111;

create table if not exists inbound_product (
                                               inbound_no int comment '발주번호',
                                               product_no int not null comment '상품번호',
                                               amount int not null comment '수량',
                                               constraint pk_inbound_no_product_no primary key(inbound_no, product_no),
                                               constraint fk_inbound_product_inbound_no foreign key (inbound_no) references inbound (inbound_no),
                                               constraint fk_inbound_product_product_no foreign key (product_no) references inbound_orderable (product_no),
                                               constraint ck_inbound_product_amount check (amount > 0)
) engine=innodb comment '발주_상품';

create table if not exists outbound (
                                        outbound_no int auto_increment comment '수주번호',
                                        franchise_no int not null comment '지점번호',
                                        date datetime default current_timestamp comment '수주일자',
                                        outbound_status varchar(10) not null comment '수주상태',
                                        constraint pk_outbound_no primary key(outbound_no),
                                        constraint fk_outbound_franchise_no foreign key (franchise_no) references franchise (franchise_no),
                                        constraint ck_outbound_status check (binary outbound_status in ('completed', 'preparing', 'canceled'))
) engine=innodb comment '수주';

alter table outbound auto_increment = 9990;

create table if not exists outbound_product (
                                                outbound_no int comment '수주번호',
                                                product_no int comment '상품번호',
                                                amount int not null comment '수량',
                                                constraint pk_outbound_no_product_no primary key(outbound_no, product_no),
                                                constraint fk_outbound_product_outbound_no foreign key (outbound_no) references outbound (outbound_no),
                                                constraint fk_outbound_product_product_no foreign key (product_no) references product (product_no),
                                                constraint ck_outbound_product_amount check (amount > 0)
) engine=innodb comment '수주_상품';

create table if not exists warehouse_section (
                                                 section_no int auto_increment comment '구역번호',
                                                 section_name varchar(10) not null comment '구역이름',
                                                 category_no int not null comment '카테고리번호',
                                                 section_space int not null comment '총 구역 적재공간',
                                                 constraint pk_section_no primary key(section_no),
                                                 constraint uq_section_name unique(section_name),
                                                 constraint fk_warehouse_section_category_no foreign key (category_no) references category (category_no),
                                                 constraint ck_warehouse_section_cargo_space check (section_space > 0)
) engine=innodb comment '창고구역';

alter table warehouse_section auto_increment = 500;

create table if not exists warehouse_zone (
                                              zone_no int auto_increment comment '존번호',
                                              section_no int not null comment '구역번호',
                                              zone_space int not null comment '총 존 적재공간',
                                              constraint pk_zone_no primary key(zone_no),
                                              constraint fk_warehouse_zone_section_no foreign key (section_no) references warehouse_section (section_no),
                                              constraint ck_zone_space check (zone_space > 0)
) engine=innodb comment '창고존';

alter table warehouse_zone auto_increment = 10;

create table if not exists inventory (
                                         section_no int comment '구역번호',
                                         product_no int comment '상품번호',
                                         amount int not null comment '수량',
                                         constraint pk_section_no_product_no primary key(section_no, product_no),
                                         constraint fk_inventory_section_no foreign key (section_no) references warehouse_section (section_no),
                                         constraint fk_inventory_product_no foreign key (product_no) references product (product_no),
                                         constraint ck_inventory_amount check (amount > 0)
) engine=innodb comment '재고';

create table if not exists delivery_vehicle (
                                                registration_no varchar(10) comment '차량번호',
                                                vehicle_status varchar(10) not null comment '차량상태',
                                                cargo_space int not null comment '적재공간',
                                                constraint pk_registration_no primary key(registration_no),
                                                constraint ck_delivery_vehicle_vehicle_status check (vehicle_status in ('배차전', '배차완료')),
                                                constraint ck_delivery_vehicle_cargo_space check (cargo_space > 0)
) engine=innodb comment '차량';

create table if not exists delivery_dispatch_log (
                                                     dispatch_no int auto_increment comment '배차번호',
                                                     registration_no varchar(10) not null comment '차량번호',
                                                     date datetime default current_timestamp comment '배차일자',
                                                     constraint pk_dispatch_no primary key(dispatch_no),
                                                     constraint fk_delivery_dispatch_log_registration_no foreign key (registration_no) references delivery_vehicle (registration_no)
) engine=innodb comment '배차내역';

create table if not exists delivery_dispatch_product (
                                                         dispatch_no int comment '배차번호',
                                                         product_no int comment '상품번호',
                                                         amount int not null comment '수량',
                                                         constraint pk_dispatch_no_product_no primary key(dispatch_no, product_no),
                                                         constraint fk_delivery_dispatch_product_dispatch_no foreign key (dispatch_no) references delivery_dispatch_log (dispatch_no),
                                                         constraint fk_delivery_dispatch_product_product_no foreign key (product_no) references product (product_no),
                                                         constraint ck_delivery_dispatch_product_amount check (amount > 0)
) engine=innodb comment '배차상품';

create table if not exists dispatch_log (
                                            dispatch_no int auto_increment comment '출고번호',
                                            date datetime default current_timestamp comment '출고일자',
                                            constraint pk_dispatch_no primary key(dispatch_no)
) engine=innodb comment '출고기록';

alter table dispatch_log auto_increment = 81111;

create table if not exists dispatch_product (
                                                dispatch_no int comment '출고번호',
                                                outbound_no int comment '수주번호',
                                                product_no int not null comment '상품번호',
                                                amount int not null comment '수량',
                                                constraint pk_dispatch_no_outbound_no primary key(dispatch_no, outbound_no),
                                                constraint fk_dispatch_product_dispatch_no foreign key (dispatch_no) references dispatch_log (dispatch_no),
                                                constraint fk_dispatch_product_outbound_no foreign key (outbound_no) references outbound (outbound_no),
                                                constraint fk_dispatch_product_product_no foreign key (product_no) references product (product_no),
                                                constraint ck_dispatch_product_amount check (amount > 0)
) engine=innodb comment '출고상품';

create table if not exists receipt_log (
                                           receipt_no int auto_increment comment '입고번호',
                                           date datetime default current_timestamp comment '입고일자',
                                           constraint pk_receipt_no primary key(receipt_no)
) engine=innodb comment '입고기록';

alter table receipt_log auto_increment = 21111;

create table if not exists receipt_product (
                                               receipt_no int comment '입고번호',
                                               inbound_no int comment '발주번호',
                                               product_no int not null comment '상품번호',
                                               amount int not null comment '수량',
                                               cargo_space int not null comment '상품 적재공간',
                                               constraint pk_receipt_no_inbound_no primary key(receipt_no, inbound_no),
                                               constraint fk_receipt_product_receipt_no foreign key (receipt_no) references receipt_log (receipt_no),
                                               constraint fk_receipt_product_inbound_no foreign key (inbound_no) references inbound (inbound_no),
                                               constraint fk_receipt_product_product_no foreign key (product_no) references product (product_no),
                                               constraint ck_receipt_product_amount check (amount > 0),
                                               constraint ck_receipt_product_cargo_space check (cargo_space > 0)
) engine=innodb comment '입고상품';

create table if not exists delivery_dispatch_outbound(
                                                         dispatch_no int comment '배차번호',
                                                         inbound_no int comment '수주번호',
                                                         constraint pk_receipt_no_inbound_no primary key(dispatch_no),
                                                         constraint fk_delivery_dispatch_outbound_dispatch_no foreign key (dispatch_no) references delivery_dispatch_log (dispatch_no),
                                                         constraint delivery_dispatch_outbound_inbound_no foreign key (inbound_no) references inbound (inbound_no)
) engine=innodb comment '배차수주';

-- 데이터 삽입
insert into admin values (null, 'admin1', '1234');
insert into admin values (null, 'admin2', '2345');
insert into admin values (null, 'admin3', '3456');

insert into franchise values (null, '역삼점', '서울특별시 강남구 테헤란로 231', '02-833-1234');
insert into franchise values (null, '여의도점', '서울특별시 영등포구 여의대로 108', '02-655-4567');
insert into franchise values (null, '성수점', '서울특별시 성동구 성수이로14길 14', '010-8682-7888');
insert into franchise values (null, '양재점', '서울특별시 강남구 도곡1동 949-3', '02-999-8888');

insert into category values (null, '소주');
insert into category values (null, '맥주');
insert into category values (null, '위스키');
insert into category values (null, '보드카');
insert into category values (null, '와인');
insert into category values (null, '기타주류');

insert into manufacturer values (null, '하이트진로㈜', '서울특별시 강남구 영동대로 714 하이트진로빌딩', '02-3219-0114');
insert into manufacturer values (null, '롯데칠성음료㈜', '서울특별시 송파구 올림픽로 269 롯데캐슬골드 5층', '02-3479-9114');
insert into manufacturer values (null, '산토리 홀딩스㈜', '오사카시 기타구 도지마하마 2-1-40', '(81) 06-6346-1131');
insert into manufacturer values (null, '페르노리카코리아㈜', '경기도 이천시 마장면 서이천로 91', '02-3466-5700');
insert into manufacturer values (null, '로버트 몬다비', '7801 St Helena Hwy, Napa, California 94558, USA', '(1) 707-636-6554');

insert into product values (null, '테라', 3300, 2, 1, 4.6, 500, 2);
insert into product values (null, '켈리', 1550, 2, 1, 4.5, 500, 2);
insert into product values (null, '참이슬', 2100, 1, 1, 16.5, 360, 2);
insert into product values (null, '진로', 1900, 1, 1, 15.5, 360, 2);
insert into product values (null, '진로 골드', 1200, 1, 1, 15.5, 360, 2);
insert into product values (null, '이슬톡톡', 1900, 6, 1, 3.0, 355, 1);
insert into product values (null, '새로 살구', 1390, 1, 2, 12, 350, 2);
insert into product values (null, '처음처럼', 1900, 1, 2, 16.5, 360, 2);
insert into product values (null, '클라우드', 1900, 2, 2, 5.0, 500, 2);
insert into product values (null, '청하', 2500, 1, 2, 13.0, 300, 2);
insert into product values (null, '마주앙', 15000, 5, 2, 13.5, 750, 3);
insert into product values (null, '스카치블루', 120000, 3, 2, 25.0, 500, 3);
insert into product values (null, '모차르트 크림', 17000, 6, 2, 17.0, 500, 1);
insert into product values (null, '산토리', 45000, 3, 3, 40.0, 600, 3);
insert into product values (null, '짐빔 애플', 29000, 3, 3, 35.0, 700, 3);
insert into product values (null, '앱솔루트 라즈베리', 37000, 4, 4, 40.0, 750, 3);
insert into product values (null, '말리부', 26000, 6, 4, 21.0, 750, 1);
insert into product values (null, '발렌타인 23Y', 320000, 3, 4, 40.0, 700, 3);
insert into product values (null, '까베르네소비뇽', 14900, 5, 5, 14.0, 750, 3);
insert into product values (null, '샤도네이', 6900, 5, 5, 13.5, 750, 3);

insert into inbound_orderable values (60001, 'Y', 3);
insert into inbound_orderable values (60002, 'N', 3);
insert into inbound_orderable values (60003, 'Y', 3);
insert into inbound_orderable values (60004, 'Y', 3);
insert into inbound_orderable values (60005, 'N', 3);
insert into inbound_orderable values (60006, 'Y', 2);
insert into inbound_orderable values (60007, 'Y', 3);
insert into inbound_orderable values (60008, 'Y', 3);
insert into inbound_orderable values (60009, 'Y', 3);
insert into inbound_orderable values (60010, 'N', 3);
insert into inbound_orderable values (60011, 'Y', 5);
insert into inbound_orderable values (60012, 'Y', 5);
insert into inbound_orderable values (60013, 'N', 2);
insert into inbound_orderable values (60014, 'Y', 5);
insert into inbound_orderable values (60015, 'Y', 5);
insert into inbound_orderable values (60016, 'Y', 5);
insert into inbound_orderable values (60017, 'Y', 2);
insert into inbound_orderable values (60018, 'Y', 5);
insert into inbound_orderable values (60019, 'Y', 5);
insert into inbound_orderable values (60020, 'N', 5);

insert into inbound values (null, 1, '2024-05-10 12:04:30', 'completed');
insert into inbound values (null, 2, '2024-05-17 18:15:45', 'pending');

insert into inbound_product values (1111, 60001, 300);
insert into inbound_product values (1112, 60006, 250);
insert into inbound_product values (1112, 60007, 369);

insert into outbound values (null, 2, '2024-05-12 14:38:07', 'completed');
insert into outbound values (null, 1, '2024-05-14 08:49:12', 'preparing');
insert into outbound values (null, 1, '2024-05-15 19:21:23', 'canceled');

insert into outbound_product values (9990, 60003, 234);
insert into outbound_product values (9991, 60014, 65);
insert into outbound_product values (9991, 60018, 20);
insert into outbound_product values (9992, 60007, 63);
insert into outbound_product values (9992, 60019, 80);

insert into warehouse_section values (null, 'A', 1, 5000);
insert into warehouse_section values (null, 'B', 2, 1000);
insert into warehouse_section values (null, 'C', 3, 5000);
insert into warehouse_section values (null, 'D', 4, 5000);
insert into warehouse_section values (null, 'E', 5, 5000);
insert into warehouse_section values (null, 'F', 6, 1000);

insert into warehouse_zone values (null, 500, 1000);
insert into warehouse_zone values (null, 500, 1000);
insert into warehouse_zone values (null, 500, 1000);
insert into warehouse_zone values (null, 501, 1000);
insert into warehouse_zone values (null, 502, 1000);
insert into warehouse_zone values (null, 502, 1000);
insert into warehouse_zone values (null, 502, 1000);
insert into warehouse_zone values (null, 503, 1000);
insert into warehouse_zone values (null, 503, 1000);
insert into warehouse_zone values (null, 504, 1000);
insert into warehouse_zone values (null, 504, 1000);
insert into warehouse_zone values (null, 504, 1000);
insert into warehouse_zone values (null, 505, 1000);

insert into inventory values (501, 60001, 135);
insert into inventory values (501, 60002, 54);
insert into inventory values (500, 60003, 300);
insert into inventory values (500, 60004, 69);
insert into inventory values (500, 60005, 234);
insert into inventory values (505, 60006, 128);
insert into inventory values (500, 60007, 15);
insert into inventory values (500, 60008, 93);
insert into inventory values (501, 60009, 264);
insert into inventory values (500, 60010, 123);
insert into inventory values (504, 60011, 340);
insert into inventory values (502, 60012, 230);
insert into inventory values (505, 60013, 106);
insert into inventory values (502, 60014, 200);
insert into inventory values (502, 60015, 360);
insert into inventory values (503, 60016, 25);
insert into inventory values (505, 60017, 178);
insert into inventory values (502, 60018, 50);
insert into inventory values (504, 60019, 70);
insert into inventory values (504, 60020, 380);

insert into delivery_vehicle values ('58누 5999', '배차전', 2000);
insert into delivery_vehicle values ('12나 8484', '배차전', 2000);
insert into delivery_vehicle values ('77라 9462', '배차전', 3000);
insert into delivery_vehicle values ('34마 1549', '배차전', 5000);

insert into delivery_dispatch_log values (null, '58누 5999', '2024-05-14 10:12:15');

insert into delivery_dispatch_product values (1, 60003, 234);

insert into dispatch_log values (null, '2024-05-14 10:12:15');

insert into dispatch_product values (81111, 9990, 60003, 234);

insert into receipt_log values (null, '2024-05-13 08:23:08');

insert into receipt_product values (21111, 1111, 60001, 300, 600);

commit;*/