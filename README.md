# store-locator-backend

This project consists in a rewrite of the https://github.com/Helio-Health-Group/store-locator project using http4s for the web api and PostgreSQL for persistence. Replicating the `/api/stores?zip_code=` endpoint.

## Tech used

The project was rewritten using Scala 2.13.8 and SBT 1.6.2.

- [http4s](https://http4s.org) and [circe](https://circe.github.io/circe/) for the application layer logic and JSON handling
- [PostgreSQL 13.0](https://www.postgresql.org) with [PostGIS](https://postgis.net) capabilities through [ScalikeJDBC](http://scalikejdbc.org) for data persistence
- [Flyway](https://flywaydb.org) for database migrations
- [Typesafe Config](https://github.com/lightbend/config) for general configuration
- [Log4s](https://github.com/Log4s/log4s) and [Logback](https://logback.qos.ch) for logging

## Setting up and running

You can either run the application through SBT or using docker compose.

### Using docker-compose

For docker-compose, it is required to set your `.env` file using the `.env.example` as a base with your preferred configs. Then, you can simply get the containers running:

```console
user@domain:~$ docker-compose up --build -d
```

The first run will get the flyway container running after the postgres container being healthy and will apply the project migrations automatically. A container with [Adminer](https://www.adminer.org) was also conveniently added for database browsing and can be accessed through localhost on port 8081 by default.

The service will be up and running in your localhost on the defined `SERVER_PORT` in the `.env`.

### SBT

Running through SBT requires filling the `application.conf` with your preferred configs. It will require a PostgreSQL database with the PostGIS extension installed (you can still use the dockerized image from compose for this!).

Then, you can either run through the shell:

```console
user@domain:~$ sbt
sbt:store-locator-backend> compile
sbt:store-locator-backend> run
```

Or in batch mode:
```console
user@domain:~$ sbt compile && sbt run
```

Migrating the database will require either running `migrate` through flyway or applying the sql files in `flyway/sql` manually.

## Loading stores from the provided JSON

A importer class was provided for the initial load. It will require filling the `application.conf` database settings accordingly for the usage.

You can run it through sbt:

```console
user@domain:~$ sbt
sbt:store-locator-backend> runMain com.helio.integration.StoreImporter
```

And through the docker image built from compose:

```console
user@domain:~$ docker run store-locator-backend "runMain com.helio.integration.StoreImporter"
```

## Misc

An optional `radius` query parameter was added to the stores endpoint for the store search radius.

## TO-DOs:

- Improve the provided Dockerfile for better developing experience
- Add prod settings once I figure out how to work with both typesafe config and dotenv variables nicely through scala
- Add some neat testing
- Add CI/CD after adding some neat testing

Thank you,

Caio
