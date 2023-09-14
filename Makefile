MVN = ./mvnw
JAVA = java
JAR = target/serverless-rss-reader-1.0.0.jar


.PHONY: all
all: build

.PHONY: lint
lint: spotless terraform-fmt prettier yamllint

.PHONY: build
build:
	$(MVN) clean package

.PHONY: upload-jars
upload-jars: build
	echo 'not implemented'
	exit 1

.PHONY: clean
clean:
	$(MVN) clean
	rm -rf dependency-reduced-pom.xml

.PHONY: update-deps
update-deps:
	$(MVN) versions:use-latest-versions

.PHONY: spotless
spotless:
	$(MVN) spotless:apply

.PHONY: terraform-fmt
terraform-fmt:
	terraform fmt terraform

.PHONY: prettier
prettier:
	prettier --write '**/*.{md,yml,yaml}'

.PHONY: yamllint
yamllint:
	yamllint --strict .
