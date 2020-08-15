create table if not exists customer (
   id bigint not null AUTO_INCREMENT,
   name nvarchar(255),
   phone nvarchar(15),
   notes nvarchar(255),
   primary key (id)
);

create table if not exists pets (
   pet_id bigint not null AUTO_INCREMENT,
   type nvarchar(15),
   name nvarchar(255),
   owner_id bigint not null,
   birth_date date,
   notes nvarchar(255),
   primary key (pet_id),
   foreign key (owner_id) references customer(id) ON DELETE CASCADE
);

