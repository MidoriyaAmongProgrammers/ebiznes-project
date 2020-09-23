# --- !Ups

INSERT INTO "category"("name") VALUES("Buty");
INSERT INTO "category"("name") VALUES("Ubrania");

INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Fila", 99.99, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam id nibh ut enim vestibulum lobortis. Donec nec eros mollis, lobortis enim in, egestas diam. Pellentesque vulputate risus et nisi malesuada, ac pretium turpis cursus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean vitae dui suscipit, facilisis diam eu, vulputate velit. Sed ante dolor, fringilla id quam at, lobortis egestas arcu. Nam arcu nisl, vulputate ut tortor et, pellentesque pharetra libero. Nulla tristique cursus semper. Pellentesque accumsan finibus lectus, in aliquam nibh vulputate et. Phasellus quis ipsum purus. Duis pretium eros libero, eu tempus quam mollis interdum. Duis et leo quis mauris aliquet semper eget at ligula. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquet felis orci, aliquam dapibus mauris iaculis ut. Etiam ut lectus at ipsum dapibus facilisis.", 1, "https://a.allegroimg.com/s512/030ac4/7ef1642145a8b82fda3a872c61a8/BUTY-FILA-DISRUPTOR-II-PREMIUM-39-5-GRATIS");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Adidas", 29.99, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam id nibh ut enim vestibulum lobortis. Donec nec eros mollis, lobortis enim in, egestas diam. Pellentesque vulputate risus et nisi malesuada, ac pretium turpis cursus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean vitae dui suscipit, facilisis diam eu, vulputate velit. Sed ante dolor, fringilla id quam at, lobortis egestas arcu. Nam arcu nisl, vulputate ut tortor et, pellentesque pharetra libero. Nulla tristique cursus semper. Pellentesque accumsan finibus lectus, in aliquam nibh vulputate et. Phasellus quis ipsum purus. Duis pretium eros libero, eu tempus quam mollis interdum. Duis et leo quis mauris aliquet semper eget at ligula. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquet felis orci, aliquam dapibus mauris iaculis ut. Etiam ut lectus at ipsum dapibus facilisis.", 1, "https://www.eobuwie.com.pl/media/catalog/product/cache/image/650x650/0/0/0000206701275_01_pc.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Zimowe Pro", 99.99, "Nowe buty zimowe pro", 2, "https://5.allegroimg.com/s512/017fa4/25ad483b440d9f591d2268aabf25/TRAPERY-TIMBERY-Buty-Zimowe-Meskie-Bezowe-43");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Zimowe", 29.99, "Nowe buty zimowe", 2, "https://obuwiejoanna.pl/66474-large_default/cieple-damskie-buty-zimowe-emu-niskie-.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Klapki pro", 99.99, "Nowe klapki pro", 3, "https://www.californiaskateshop.pl/data/gfx/pictures/medium/7/2/21727_1.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Klapki", 29.99, "Nowe klapki", 3, "https://c.allegroimg.com/s512/03097f/ad6d111d42fba2b236b76484bf8c/Buty-klapki-medyczne-saboty-skorzane-SD7-czarne-34");

INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Koszulka Adidas", 99.99, "Nowa koszulka Adidas", 4, "https://blob.sxv.pl/shops/media/f1000/2018/adidas/95790/koszulka-adidas-trefoil-cw0709-5cc04b57c31e8.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Koszulka Nike", 29.99, "Nowa koszulka Nike", 4, "https://desportivo.pl/userdata/public/gfx/f3b77220c20e993289736e58e8badf64.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Bluza Adidas", 99.99, "Nowa bluza Adidas", 5, "https://image.ceneostatic.pl/data/products/60812572/i-bluza-adidas-trefoil-hoodie-czarna-cw1240.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Bluza Nike", 29.99, "Nowa bluza Nike", 5, "https://kajasport.pl/pol_pl_Bluza-meska-Nike-Team-Club-19-Hoodie-czarna-z-kapturem-38524_1.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Sweter pro", 99.99, "Nowy sweter pro", 6, "https://volcano.pl/pub/media/catalog/product/cache/74c1057f7991b4edb2bc7bdaa94de933/s/-/s-fly_705.jpg");
INSERT INTO "product"("name", "price", "description", "subcategory", "img") VALUES ("Sweter", 29.99, "Nowy sweter", 6, "https://magum.pl/6101-thickbox_default/sweter-pinewood-new-stormy-9547.jpg");

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

INSERT INTO "subcategory"("name", "description", "category") VALUES ("Adidasy", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("Zimowe", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("Klapki", "buty sportowe", 1);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("Koszulki", "fajne koszulki", 2);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("Bluzy", "fajne koszulki", 2);
INSERT INTO "subcategory"("name", "description", "category") VALUES ("Swetry", "fajne koszulki", 2);


INSERT INTO "basket"("user") VALUES ("1");
INSERT INTO "basket"("user") VALUES ("2");


# --- !Downs

