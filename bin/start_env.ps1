$scriptPath = split-path -parent $MyInvocation.MyCommand.Definition

boot2docker start
Start-Job -Name mysql { boot2docker ssh "docker run -p 3306:3306 --name acad_mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=acad -e MYSQL_PASSWORD=secret -e MYSQL_DATABASE=acad_db mysql:5.7"; }
Start-Job -Name app -inputobject "$scriptPath\.." -scriptblock { cd "$input"; npm start }
Start-Job -Name selenium -inputobject "$scriptPath" { java -jar "$input\selenium-server-standalone-2.45.0.jar" }
