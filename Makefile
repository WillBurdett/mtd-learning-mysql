project-build:
	@mvn clean package -DskipTests
.PHONY: project-build

api-build:
	@docker build --tag=mtd-sql:latest .
.PHONY: api-build

run:
	@docker-compose -f ./docker-compose.yml up
.PHONY: run

deploy: project-build api-build run

