MVN = ./mvnw


.PHONY: all
all: build

.PHONY: lint
lint: spotless prettier yamllint

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

.PHONY: prettier
prettier:
	prettier --write '**/*.{md,yml,yaml}'

.PHONY: yamllint
yamllint:
	yamllint --strict .
