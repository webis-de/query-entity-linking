IMAGE_NAME=registry.webis.de/code-lib/public-images/query-entity-linking
VERSION=1.0

SHELL := /bin/bash

clean:
	mvn clean
	rm -rf venv data

download:
	 /bin/bash src/main/sh/install-el.sh

build: download
	docker build -t ${IMAGE_NAME}:${VERSION} .

deploy: build
	docker push ${IMAGE_NAME}:${VERSION}

tira:
	python3 -m venv venv
	source venv/bin/activate && pip install tira

tira-run: deploy tira
	source venv/bin/activate && tira-run \
		--image ${IMAGE_NAME}:${VERSION} \
		--input-dataset ir-benchmarks/${CORPUS} \
		--output-dir out/${CORPUS}/ \
		--command 'java -jar /query-entity-linking/target/query-entity-linking-1.0-jar-with-dependencies.jar --input $$inputDataset --output $$outputDir'
