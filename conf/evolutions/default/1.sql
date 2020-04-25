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
 "paid" BIT NOT NULL
);

CREATE TABLE "order" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "date" VARCHAR NOT NULL,
 "shipment" INTEGER NOT NULL,
 "payment" INTEGER NOT NULL,
 "coupon" INTEGER NOT NULL,
 "user" INTEGER NOT NULL,
 FOREIGN KEY(shipment) references shipment(id),
 FOREIGN KEY('user') references user(id),
 FOREIGN KEY(payment) references payment(id)
);

CREATE TABLE "review" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "user" INTEGER NOT NULL,
 "product" INTEGER NOT NULL,
 "content" TEXT NOT NULL,
 "rating" INTEGER NOT NULL,
 FOREIGN KEY('user') references 'user'(id),
 FOREIGN KEY(product) references product(id)
);

CREATE TABLE "shipment" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "date" VARCHAR NOT NULL,
 "address" VARCHAR NOT NULL
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

CREATE TABLE "basket_products" (
 "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 "basket" INTEGER NOT NULL,
 "product" INTEGER NOT NULL,
 FOREIGN KEY(basket) references basket(id),
 FOREIGN KEY(product) references product(id)
);

CREATE TABLE "user" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "username" VARCHAR NOT NULL,
  "password" VARCHAR NOT NULL,
  "email" VARCHAR NOT NULL
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


