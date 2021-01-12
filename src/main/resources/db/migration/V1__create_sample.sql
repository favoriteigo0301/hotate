create table sample_memo (
  id serial primary key not null, 
  subject varchar(40) not null, 
  memo text not null, 
  image blob  
);
