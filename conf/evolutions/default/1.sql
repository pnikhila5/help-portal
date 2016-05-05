# Provider Schema

# --- !Ups

CREATE TABLE  provider(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    fullname varchar(255) NOT NULL,
    tech varchar(255) NOT NULL,
    experience int(4),
    rating int(4) NOT NULL,
    num_reviews int(4),
    feedback varchar(255),
    PRIMARY KEY(id)

);

# --- !Downs

DROP TABLE provider;
