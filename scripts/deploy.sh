#!/bin/bash

ENV=$1

echo "Deploying to $ENV environment..."

if [ "$ENV" == "BLUE"]; then
    PORT=8081
    NAME="blue-app"
else
    PORT=8082
    NAME="green-app"
fi

docker stop $NAME 2>/dev/null
docker rm $NAME 2>/dev/null

docker run -d --name $NAME -p $PORT:8081 nginx

echo "Deployment to $ENV environment completed successfully."
exit 0