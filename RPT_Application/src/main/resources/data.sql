
INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_ADMIN', '1');
INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_USER', '2');

insert into `employee`(`id`,`username`,`password`) values('1','Geralt',HASH('SHA256',STRINGTOUTF8('12345'),1000));
insert into `employee`(`id`,`username`,`password`) values('2','Yennefer','6789');
insert into `employee`(`id`,`username`,`password`) values('3','Ciri','101112');

INSERT INTO `employee_authority`(`authority_id`, `employee_id`) VALUES ('1', '1');
INSERT INTO `employee_authority`(`authority_id`, `employee_id`) VALUES ('2', '2');
INSERT INTO `employee_authority`(`authority_id`, `employee_id`) VALUES ('1', '3');



