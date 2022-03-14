# Database Migration with Spring Batch

## Examples:

* Migrate Postgres to Postgres with JDBC
* Migrate Postgres to Postgres with JPA
* Migrate Postgres to Postgres with JPA reader and JDBC writer

*JPA examples with Primary Key and Composite Primary Key*

*Spring Batch metadata tables creating on H2, check in DataSourceConfig.class*

# Instructions to create Postgres instances on Docker

* docker run -d -p 5432:5432 -v postgres_legacy:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres --name imdb postgres

* docker run -d -p 5433:5432 -v postgres_new:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres --name imdb2 postgres

### If you want to populate a database with Spring Batch and CSV, you can find here:
#### https://github.com/hmfurtado/batch-csv