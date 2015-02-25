FROM node:0.12
MAINTAINER Thomas Bracher "thomas.bracher@cpe.fr"

RUN npm install -g nodemon
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN cp local.json-dist local.json && \
    sed -i 's/localhost/mysql/g' local.json
RUN npm install
RUN node bin/init_jade.js

EXPOSE 8080
CMD [ "npm", "start" ]
