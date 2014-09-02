create table user_upload(
upload_ID   int     not null    AUTO_INCREMENT,
user_id   int not null,
upload_specie   varchar(128)    not null,
PRIMARY KEY (upload_ID),
KEY `FK_Relationship__1` (`user_id`),
CONSTRAINT `FK_Relationship__1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`)
);


