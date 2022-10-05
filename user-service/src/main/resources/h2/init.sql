create table if not exists users(
	id bigint auto_increment primary key,
	name varchar(255),
	balance int
);

create table if not exists user_transaction(
	id bigint auto_increment primary key,
	user_id bigint,
	amount int,
	transaction_date timestamp,
	foreign key (user_id) references users(id) on delete cascade
);

insert into users (name, balance) values
	('sam', 1000),
	('mike', 2000),
	('saim', 5000),
	('fahad', 100);
