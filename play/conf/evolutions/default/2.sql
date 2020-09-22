# --- !Ups

INSERT INTO "category"("name") VALUES("buty");
INSERT INTO "category"("name") VALUES("ubrania");

INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("adidasy1", 99.99, "Nowe buty", 1, "https://a.allegroimg.com/s512/030ac4/7ef1642145a8b82fda3a872c61a8/BUTY-FILA-DISRUPTOR-II-PREMIUM-39-5-GRATIS");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("adidasy2", 29.99, "Nowa bluzka", 1, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("trekkingowe1", 99.99, "Nowe buty", 2, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("trekkingowe2", 29.99, "Nowa bluzka", 2, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("do biegania1", 99.99, "Nowe buty", 3, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("do biegania2", 29.99, "Nowa bluzka", 3, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");

INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("koszulka1", 99.99, "Nowe buty", 4, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("koszulka2", 29.99, "Nowa bluzka", 4, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("t-shirt1", 99.99, "Nowe buty", 5, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("t-shirt2", 29.99, "Nowa bluzka", 5, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("sweter1", 99.99, "Nowe buty", 6, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("sweter2", 29.99, "Nowa bluzka", 6, "https://www.holiday24.com.pl/images/holiday24compl/953-buty-nike-obuwie-damskie-i-m%C4%99skie-silverduck-4092.jpg");

INSERT INTO "user"("Id", "firstName", "lastName", "email") VALUES ("1","Krystian", "Krawczyk", "myEmail@gmail.com");
INSERT INTO "user"("Id", "firstName", "lastName", "email") VALUES ("2","Monika", "Kowal", "haha@gmail.com");

INSERT INTO "coupon"("name", "code", "value") VALUES ("wiosenny", "wiosna2020", 10);
INSERT INTO "coupon"("name", "code", "value") VALUES ("zimowy", "zima2020", 20);

INSERT INTO "payment"("value", "paid", "method") VALUES (12.54, TRUE, "Blick");
INSERT INTO "payment"("value", "paid", "method") VALUES (100.00, FALSE, "Cash");

INSERT INTO "order"("date","shipment", "payment", "coupon", "user") VALUES ("2007-01-01 10:00:00", 1, 1, 1, "1");
INSERT INTO "order"("date","shipment", "payment", "coupon", "user") VALUES ("2008-01-01 10:00:00", 2, 2, 2, "2");

INSERT INTO "review"("user", "product", "content", "rating") VALUES ("1", 1, "fajny", 5);
INSERT INTO "review"("user", "product", "content", "rating") VALUES ("2", 2, "głupi", 1);

INSERT INTO "shipmentAddress"("address", "city", "postCode") VALUES ("Lipowa 12", "Warszawa", "12-300");
INSERT INTO "shipmentAddress"("address", "city", "postCode") VALUES ("Majowa 17", "Kraków", "12-600");


INSERT INTO "shipment"("date", "address", "firstName", "lastName", "provider") VALUES ("2007-01-01 10:00:00", 1, "Krzysztof", "Adamczyk", "UPS");
INSERT INTO "shipment"("date", "address", "firstName", "lastName", "provider") VALUES ("2008-01-01 10:00:00", 2, "Marzena", "Konieczna", "DHL");

INSERT INTO "subcategory"("name", "description", "category") VALUES ("adidasy", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("trekkingowe", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("do biegania", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("koszulki", "fajne koszulki", 2);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("t-shirt", "fajne koszulki", 2);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("sweter", "fajne koszulki", 2);


INSERT INTO "basket"("user") VALUES ("1");
INSERT INTO "basket"("user") VALUES ("2");


# --- !Downs

