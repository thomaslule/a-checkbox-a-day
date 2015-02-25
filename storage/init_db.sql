drop table if exists tasks;
create table if not exists tasks (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    done BOOLEAN NOT NULL,
    PRIMARY KEY (id)
) ENGINE=MyISAM;
