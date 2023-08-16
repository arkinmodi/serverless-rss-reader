MVN = ./mvnw
JAVA = java
JAR = target/rssreader-1.0.0.jar


.PHONY: all
all: build run

.PHONY: build
build:
	$(MVN) clean package

.PHONY: run
run:
	$(JAVA) -jar $(JAR)

.PHONY: clean
clean:
	$(MVN) clean

.PHONY: spotless
spotless:
	$(MVN) spotless:apply

.PHONY: prettier
prettier:
	npx prettier --write '**/*.{md,yml,yaml}'

.PHONY: yamllint
yamllint:
	yamllint --strict .
