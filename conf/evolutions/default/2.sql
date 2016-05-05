# --- Sample dataset

# --- !Ups
insert into provider (id,email, fullname,tech, experience,rating, num_reviews,feedback) values(1,'marc@gmail.com','Marc Rudger','Salesforce',17,10,1, 'Talented');
insert into provider (id,email, fullname,tech, experience,rating, num_reviews,feedback) values(2,'nadellac@gmail.com','Nadella S','.NET',8,10,1, 'Good');
insert into provider (id,email, fullname,tech, experience,rating, num_reviews,feedback) values(3,'roger@yahoo.com','Roger M','Java',12,9,1, 'Friendly');

# --- !Downs

delete from provider;
