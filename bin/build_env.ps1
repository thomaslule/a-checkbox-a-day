$start = $pwd
$scriptPath = split-path -parent $MyInvocation.MyCommand.Definition

# Download nodejs dependencies
npm install

# Download selenium server
if(!(Test-Path -Path "$scriptPath\selenium-server-standalone-2.45.0.jar")) {
    $webclient = New-Object System.Net.WebClient
    $webclient.DownloadFile("http://selenium-release.storage.googleapis.com/2.45/selenium-server-standalone-2.45.0.jar", "$scriptPath\selenium-server-standalone-2.45.0.jar")
}

# Download mysql
boot2docker init
boot2docker start
boot2docker ssh "docker pull mysql:5.7"

# Set mysql ip in local.json
$fileFixed = (gc $scriptPath\..\local.json-dist) -replace 'localhost', '192.168.59.103'
[System.IO.File]::WriteAllLines("$scriptPath\..\local.json", $fileFixed)

# Configure ruby
gem install bundler
cd $scriptPath\..\test\acceptance
bundle install
cd $start
