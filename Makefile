MVN = ./mvnw
JAVA = java
JAR = target/serverless-rss-reader-1.0.0.jar


.PHONY: all
all: build run

.PHONY: lint
lint: spotless prettier yamllint

.PHONY: build
build:
	$(MVN) clean package

.PHONY: run
run:
	$(JAVA) -jar $(JAR)

.PHONY: clean
clean:
	$(MVN) clean
	rm -rf dependency-reduced-pom.xml

.PHONY: spotless
spotless:
	$(MVN) spotless:apply

.PHONY: prettier
prettier:
	npx prettier --write '**/*.{md,yml,yaml}'

.PHONY: yamllint
yamllint:
	yamllint --strict .
