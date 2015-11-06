create table if not exists items (
    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(25) NOT NULL,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(25) NOT NULL,
    list_type VARCHAR(25) NOT NULL,
    list_id INT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=MyISAM;

create table if not exists journal_entry (
    `date` DATE NOT NULL,
    `text` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`date`)
) ENGINE=MyISAM;
