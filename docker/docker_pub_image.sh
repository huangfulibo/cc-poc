#!/bin/bash

WORKSPACE=/Users/I519082/public-project/cc-poc-pub-sub/
JAR_NAME=cc-pub-multitopics.jar
IMAGE_NAME=cc-pub-multitopics:0.0.1
DOCKER_HUB_REPOSITORY=huangfulibo
DOCKER_HUB_REPOSITORY_PW=Badi12345678!
SAP_DOCKER_IMAGE_REPOSITORY=confluent-cloud-poc.common.repositories.cloud.sap
SAP_DOCKER_IMAGE_REPOSITORY_USERNAME=i519082
SAP_DOCKER_IMAGE_REPOSITORY_PW=AKCp8nyYF95UbQEmeuL6ADZ8i9EXJQSQdyfZkx3PJdXyXkqQyMhJyZdGcPhsFHhigTfmeb6Az


# 1. build jar
cd $WORKSPACE
./gradlew bootJar
cd "$WORKSPACE"build/libs
cp cc-poc-0.0.1-SNAPSHOT.jar $JAR_NAME

# 2. maker docker images
cp $JAR_NAME "$WORKSPACE"docker

docker build -t $IMAGE_NAME -f "$WORKSPACE"docker/cc_pub_multi_topics_Dockerfile .

# 3. push docker images to remote repository
docker login -u $DOCKER_HUB_REPOSITORY -p $DOCKER_HUB_REPOSITORY_PW
docker tag $IMAGE_NAME $DOCKER_HUB_REPOSITORY/$IMAGE_NAME
docker push $DOCKER_HUB_REPOSITORY/$IMAGE_NAME

# login in common.repositories.cloud.sap
docker login $SAP_DOCKER_IMAGE_REPOSITORY -u $SAP_DOCKER_IMAGE_REPOSITORY_USERNAME -p $SAP_DOCKER_IMAGE_REPOSITORY_PW
docker tag $IMAGE_NAME $SAP_DOCKER_IMAGE_REPOSITORY/$IMAGE_NAME
docker push $SAP_DOCKER_IMAGE_REPOSITORY/$IMAGE_NAME



