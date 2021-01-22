create table users
(
    id         int primary key not null auto_increment,
    name       varchar(40)        not null,
    password   varchar(20)        not null,
    created_at timestamp,
    updated_at timestamp
);

create table sample_memo
(
    id        int primary key not null auto_increment,
    user_id   int not null,
    subject   varchar(40)        not null,
    memo      text               not null,
    image     blob,
    image_name varchar(40),
    create_at timestamp,
    update_at timestamp,
    foreign key (user_id) references users (id)
);

insert into users(name,password,created_at,updated_at) values ('テスト','0000',now(),now());
