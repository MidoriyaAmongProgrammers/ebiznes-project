# --- !Ups

CREATE TABLE "category" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
 "name" VARCHAR NOT NULL
);

CREATE TABLE "product" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
 "name" VARCHAR NOT NULL,
 "price" FLOAT NOT NULL,
 "description" TEXT NOT NULL,
 "subcategory" INTEGER NOT NULL,
  "img" TEXT NOT NULL,
 FOREIGN KEY(subcategory) references subcategory(id)
);


CREATE TABLE "coupon" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "name" VARCHAR NOT NULL,
 "code" VARCHAR NOT NULL,
 "value" INTEGER NOT NULL
);

CREATE TABLE "payment" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "value" FLOAT NOT NULL,
 "paid" BIT NOT NULL,
 "method" VARCHAR NOT NULL
);

CREATE TABLE "order" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "date" VARCHAR NOT NULL,
 "shipment" INTEGER NOT NULL,
 "payment" INTEGER NOT NULL,
 "coupon" INTEGER NOT NULL,
 "user" TEXT NOT NULL,
 FOREIGN KEY(shipment) references shipment(id),
 FOREIGN KEY('user') references user(id),
 FOREIGN KEY(payment) references payment(id)
);

CREATE TABLE "review" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "user" TEXT NOT NULL,
 "product" INTEGER NOT NULL,
 "content" TEXT NOT NULL,
 "rating" INTEGER NOT NULL,
 FOREIGN KEY('user') references 'user'(id),
 FOREIGN KEY(product) references product(id)
);

CREATE TABLE "shipment" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "date" VARCHAR NOT NULL,
 "address" INTEGER NOT NULL,
 "firstName" VARCHAR NOT NULL,
 "lastName" VARCHAR NOT NULL,
  "provider" VARCHAR NOT NULL,
  FOREIGN KEY(address) references shipmentAddress(id)
);

CREATE TABLE "shipmentAddress" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "address" VARCHAR NOT NULL,
 "city" VARCHAR NOT NULL,
 "postCode" VARCHAR NOT NULL
);

CREATE TABLE "subcategory" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" VARCHAR NOT NULL,
 "description" TEXT NOT NULL,
 "category" INTEGER NOT NULL,
  FOREIGN KEY(category) references category(id)
);

CREATE TABLE "basket" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user" INTEGER NOT NULL,
 FOREIGN KEY('user') references user(id)
);

CREATE TABLE "user_product" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "user" TEXT NOT NULL,
 "product" INTEGER NOT NULL,
 "quantity" INTEGER NOT NULL,
 FOREIGN KEY(user) references user(id),
 FOREIGN KEY(product) references product(id)
);

CREATE TABLE "LoginInfo" (
	"Id"	TEXT NOT NULL UNIQUE,
	"ProviderId"	TEXT NOT NULL,
	"ProviderKey"	TEXT NOT NULL
);

CREATE TABLE "UserLoginInfo" (
	"UserId"	TEXT NOT NULL,
	"LoginInfoId"	TEXT NOT NULL,
	FOREIGN KEY("UserId") REFERENCES "user"("Id"),
	FOREIGN KEY("LoginInfoId") REFERENCES "LoginInfo"("Id")
);

CREATE TABLE "PasswordInfo" (
	"Hasher"	TEXT NOT NULL,
	"Password"	TEXT NOT NULL,
	"Salt"	TEXT,
	"LoginInfoId"	TEXT NOT NULL,
	FOREIGN KEY("LoginInfoId") REFERENCES "LoginInfo"("Id")
);

CREATE TABLE "OAuth2Info" (
	"Id"	TEXT NOT NULL UNIQUE,
	"AccessToken"	TEXT NOT NULL,
	"TokenType"	TEXT,
	"ExpiresIn"	INTEGER,
	"RefreshToken"	TEXT,
	"LoginInfoId"	TEXT NOT NULL,
	PRIMARY KEY("Id"),
	FOREIGN KEY("LoginInfoId") REFERENCES "LoginInfo"("Id")
);

CREATE TABLE "user" (
	"Id"	TEXT NOT NULL UNIQUE,
	"Email"	TEXT NOT NULL,
	"FirstName"	TEXT,
	"LastName"	TEXT,
	PRIMARY KEY("Id")
);

# --- !Downs

DROP TABLE "category";
DROP TABLE "product";
DROP TABLE "coupon";
DROP TABLE "payment";
DROP TABLE "shipment";
DROP TABLE "review";
DROP TABLE "subcategory";
DROP TABLE "order";
DROP TABLE "basket";
DROP TABLE "user";
DROP TABLE "user_product";



