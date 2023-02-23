create database second_hand;

use second_hand;

create table postings (
    -- primary key postings_id
    posting_id varchar(8) not null,
    posting_date varchar not null,
    name varchar(30) not null,
    email varchar(128) not null,
    phone varchar(20),
    title varchar(128),
    description text not null,
    image blob,

    primary key(posting_id)

);