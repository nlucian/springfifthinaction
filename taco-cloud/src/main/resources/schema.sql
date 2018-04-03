create table if not exists Ingredient (
	id varchar(4) not null,
	name varchar(25) not null,
	type varchar(10) not null
);

create table if not exists Taco (
	id identity,
	name varchar(50) not null,
	createdAt timestamp not null
);

create table if not exists Taco_Ingredient (
  tacoId bigint not null,
	ingredientId varchar(4) not null
);

alter table Taco_Ingredient
	add foreign key (tacoId) references Taco(id);
alter table Taco_Ingredient
	add foreign key (ingredientId) references Ingredient(id);

create table if not exists Taco_Order (
	id identity,
	name varchar(50) not null,
	street varchar(50) not null,
	city varchar(50) not null,
	state varchar(50) not null,
	zip varchar(10) not null,
	ccNumber varchar(16) not null,
	ccExpiration varchar(5) not null,
	ccCVV varchar(3) not null,
	placedAt timestamp not null
);

create table if not exists Taco_Order_Tacos (
	orderId bigint not null,
	tacoId bigint not null
);

create table User (
	username varchar(50) not null,
	password varchar(50) not null,
	enabled boolean not null
);

create table if not exists User_Role (
	username varchar(50) not null,
	role varchar(50) not null
);

alter table User_Role
	add foreign key (username) references User(username);


alter table Taco_Order_Tacos
	add foreign key (orderId) references Taco_Order(id);

alter table Taco_Order_Tacos
	add foreign key (tacoId) references Taco(id);
