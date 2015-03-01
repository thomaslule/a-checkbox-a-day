create table if not exists tasks (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=MyISAM;
