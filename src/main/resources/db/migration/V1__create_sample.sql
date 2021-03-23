-- ユーザテーブル
create table users
(
    id         bigint primary key not null auto_increment,
    name       varchar(40)        not null,
    password   char (60)       not null,
    role       varchar(40) not null,
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
    created_at timestamp,
    updated_at timestamp
);

-- カテゴリーテーブル
create table sample_category
(
    id bigint primary key not null  auto_increment,
    memo_id bigint,
    name varchar(40) not null,
    register_count bigint,
    created_at timestamp,
    updated_at timestamp
);
