insert into customer(name, email) values('John', 'john.smith@gmail.com');

insert into restaurant(name) values('McDonalds');
insert into restaurant(name) values('Burger King');
insert into restaurant(name) values('KFC');

insert into item(name, price, restaurant_id) values('Big Mac', 3.99, 1);
insert into item(name, price, restaurant_id) values('Whopper', 4.99, 2);
insert into item(name, price, restaurant_id) values('Chicken Wings', 5.99, 3);

insert into orders(customer_id, restaurant_id, status) values(1, 1, 'RECEIVED');
insert into orders(customer_id, restaurant_id, status) values(1, 2, 'RECEIVED');
insert into orders(customer_id, restaurant_id, status) values(1, 3, 'RECEIVED');

insert into ordered_item(order_id, item_id) values(1, 1);
insert into ordered_item(order_id, item_id) values(1, 2);