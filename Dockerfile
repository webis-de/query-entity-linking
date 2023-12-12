FROM maven:3.8.3-adoptopenjdk-15
LABEL authors="Marcel Gohsen"

RUN apt update

ADD src /query-entity-linking/src
ADD pom.xml /query-entity-linking/

ADD data/persistent/entity-commonness /query-entity-linking/data/persistent/entity-commonness
ADD data/persistent/wiki-entity-index /query-entity-linking/data/persistent/wiki-entity-index

WORKDIR /query-entity-linking

RUN mvn clean package


