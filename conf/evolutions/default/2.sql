# --- !Ups

INSERT INTO "category"("name") VALUES("buty");
INSERT INTO "category"("name") VALUES("ubrania");

INSERT INTO "product"("name", "price", "description", "subcategory") VALUES ("buty", 99.99, "Nowe buty", 1);
INSERT INTO "product"("name", "price", "description", "subcategory") VALUES ("bluzka", 29.99, "Nowa bluzka", 2);

INSERT INTO "user"("username", "password", "email") VALUES ("king123", "myPassword", "myEmail@gmail.com");
INSERT INTO "user"("username", "password", "email") VALUES ("haha123", "1234", "haha@gmail.com");

INSERT INTO "coupon"("name", "code", "value") VALUES ("wiosenny", "wiosna2020", 10);
INSERT INTO "coupon"("name", "code", "value") VALUES ("zimowy", "zima2020", 20);

INSERT INTO "payment"("value", "paid") VALUES (12, TRUE);
INSERT INTO "payment"("value", "paid") VALUES (100, FALSE);

INSERT INTO "order"("date","shipment", "payment", "coupon", "user") VALUES ("2007-01-01 10:00:00", 1, 1, 1, 1);
INSERT INTO "order"("date","shipment", "payment", "coupon", "user") VALUES ("2008-01-01 10:00:00", 2, 2, 2, 2);

INSERT INTO "review"("user", "product", "content", "rating") VALUES (1, 1, "fajny", 5);
INSERT INTO "review"("user", "product", "content", "rating") VALUES (2, 2, "głupi", 1);

INSERT INTO "shipment"("date", "address") VALUES ("2007-01-01 10:00:00", "Katowice");
INSERT INTO "shipment"("date", "address") VALUES ("2008-01-01 10:00:00", "Warszawa");

INSERT INTO "subcategory"("name", "description", "category") VALUES ("adidasy", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("koszulki", "fajne koszulki", 2);

INSERT INTO "basket"("user") VALUES (1);
INSERT INTO "basket"("user") VALUES (2);


# --- !Downs

