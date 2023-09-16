DISCORD_LAMBDA_FUNCTION_NAME = serverless-rss-reader-discord-handler
JAR = target/serverless-rss-reader-1.0.0.jar
MVN = ./mvnw
RSS_LAMBDA_FUNCTION_NAME = serverless-rss-reader-rss-handler

.PHONY: all
all: build

.PHONY: lint
lint: spotless terraform-fmt prettier yamllint

.PHONY: build
build:
	$(MVN) clean package

.PHONY: update-lambdas
update-lambdas: update-discord-lambda update-rss-lambda

.PHONY: update-discord-lambda
update-discord-lambda: build
	aws lambda update-function-code \
		--function-name $(DISCORD_LAMBDA_FUNCTION_NAME) \
		--zip-file fileb://$(JAR)

.PHONY: update-rss-lambda
update-rss-lambda: build
	aws lambda update-function-code \
		--function-name $(RSS_LAMBDA_FUNCTION_NAME) \
		--zip-file fileb://$(JAR)

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
