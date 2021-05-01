-- ユーザテーブル
create table users
(
    id         bigint primary key not null auto_increment,
    name       varchar(40)        not null,
    password   char (60)       not null,
    created_at timestamp,
    updated_at timestamp
);

-- ロールテーブル
create table role
(
    id         bigint primary key not null auto_increment,
    user_id    bigint not null,
    name       varchar(40) not null,
    created_at timestamp,
    updated_at timestamp
);

-- メモテーブル
create table sample_memo
(
    id        bigint primary key not null auto_increment,
    user_id   bigint not null,
    subject   varchar(40)        not null,
    memo      text               not null,
    created_id bigint unsigned,
    created_at timestamp,
    updated_id bigint unsigned,
    updated_at timestamp
);

-- メモ_カテゴリーテーブル(中間テーブル)
create table sample_memo_category
(
    memo_id        bigint  not null,
    category_id   bigint not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (memo_id, category_id)
);

-- カテゴリーテーブル
create table sample_category
(
    id bigint primary key not null  auto_increment,
    memo_id bigint,
    name varchar(40) not null,
    created_at timestamp,
    updated_at timestamp
);
