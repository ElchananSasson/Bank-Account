-- create tables
create table accountOperations (
	act text,
	amount real,
	aType text,
	description text,
    aDate date
);

create table balance (
    amount real
);

insert into balance values(0);
