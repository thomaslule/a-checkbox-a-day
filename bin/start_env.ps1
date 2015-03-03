Start-Job -Name mysql { boot2docker start; boot2docker ssh "docker run -p 3306:3306 --name acad_mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=acad -e MYSQL_PASSWORD=secret -e MYSQL_DATABASE=acad_db mysql:5.7"; }
Start-Job -Name app -inputobject $pwd -scriptblock { cd "$input"; npm start }
