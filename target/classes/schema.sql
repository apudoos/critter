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

CREATE TABLE IF NOT EXISTS schedule (
	    id bigint(20) NOT NULL AUTO_INCREMENT,
	    date date NOT NULL,
	    PRIMARY KEY (id)
);

create table if not exists schedule_employee (
   schedule_id bigint not null,
   employee_id bigint not null,
   foreign key (schedule_id) references schedule(id) on delete cascade
--   foreign key (employee_id) references employee(id) on delete cascade
);

create table if not exists schedule_pets (
   schedule_id bigint not null,
   pets_id bigint not null,
   foreign key (schedule_id) references schedule(id),
   foreign key (pets_id) references pets(pet_id) on delete cascade
);

create table if not exists schedule_activity (
   schedule_id bigint not null,
   activity nvarchar(15) not null,
   foreign key (schedule_id) references schedule(id) on delete cascade
);