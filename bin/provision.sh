#! /usr/bin/env bash
# Author Thomas Bracher "thomas.bracher@cpe.fr"

# Provision vagrant box
curl -sSL https://get.docker.com/ | sh
sudo gpasswd -a vagrant docker
sudo service docker restart
