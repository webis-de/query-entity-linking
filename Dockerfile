FROM registry.webis.de/code-lib/public-images/query-entity-linking:1.0
LABEL authors="Marcel Gohsen"

RUN rm -R /query-entity-linking/src \
  && rm -R /query-entity-linking/target \
  && rm /query-entity-linking/pom.xml

ADD src /query-entity-linking/src
ADD pom.xml /query-entity-linking/

RUN mvn clean package

ENTRYPOINT [ "java", "-jar", "/query-entity-linking/target/query-entity-linking-1.0-jar-with-dependencies.jar", "--input", "$inputDataset", "--output", "$outputDir" ]

