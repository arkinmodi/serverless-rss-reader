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

.PHONY: spotless
spotless:
	$(MVN) spotless:apply

.PHONY: clean
clean:
	$(MVN) clean
