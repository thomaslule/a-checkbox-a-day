#! /usr/bin/env bash

# Provision vagrant box
curl -sSL https://get.docker.com/ | sh
sudo gpasswd -a vagrant docker
sudo service docker restart
