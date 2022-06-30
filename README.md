# POC: Spring Liquibase

It demonstrates how to run database migrations using Liquibase, Spring Boot and Gradle.

We want to write SQL files with instructions to set up a database and run them systematically, according to our needs.
For example, if the application needs a table to store customers, those scripts should execute the statements
to create that table, constraints, index and so on. The execution of those scripts should be managed by Liquibase.

Also, we want the option to rollback scripts whenever we want, as long as everything is automated and documented properly.

## How to run

| Description                    | Command                                             |
|:-------------------------------|:----------------------------------------------------|
| Run tests                      | `./gradlew test`                                    |
| Run database migrations        | `./gradlew update`                                  |
| Rollback N database migrations | `./gradlew rollbackCount -PliquibaseCommandValue=N` |
| Provision database¹            | `docker-compose up --detach`                        |
| Destroy database¹              | `docker-compose down --volumes`                     |

> ¹Required for manual testing only, automated tests provision and destroy a database automatically. Must run
> inside `infrastructure` folder.

## Preview

Running database migrations:

```text
Starting Liquibase at 19:19:37 (version 4.10.0 #2501 built at 2022-05-04 14:27+0000)
Liquibase Version: 4.10.0
Liquibase Community 4.10.0 by Liquibase
[2022-06-30 19:19:37] INFO [liquibase.database] Set default schema name to public
[2022-06-30 19:19:37] INFO [liquibase.lockservice] Successfully acquired change log lock
[2022-06-30 19:19:37] INFO [liquibase.changelog] Creating database history table with name: public.databasechangelog
[2022-06-30 19:19:37] INFO [liquibase.changelog] Reading from public.databasechangelog
Running Changeset: src/main/resources/db/changelog/main.yaml::20220629-001::lucas
[2022-06-30 19:19:37] INFO [liquibase.changelog] SQL in file changes/001-create-country-table.sql executed
[2022-06-30 19:19:37] INFO [liquibase.changelog] SQL in file changes/002-create-customer-table.sql executed
[2022-06-30 19:19:37] INFO [liquibase.changelog] ChangeSet src/main/resources/db/changelog/main.yaml::20220629-001::lucas ran successfully in 32ms
Running Changeset: src/main/resources/db/changelog/main.yaml::20220629-002::lucas
[2022-06-30 19:19:37] INFO [liquibase.changelog] SQL in file changes/003-insert-countries.sql executed
[2022-06-30 19:19:37] INFO [liquibase.changelog] ChangeSet src/main/resources/db/changelog/main.yaml::20220629-002::lucas ran successfully in 5ms
[2022-06-30 19:19:37] INFO [liquibase.lockservice] Successfully released change log lock
Liquibase command 'update' was executed successfully.
```

Rollback two database migrations:

```text
Starting Liquibase at 19:17:29 (version 4.10.0 #2501 built at 2022-05-04 14:27+0000)
Liquibase Version: 4.10.0
Liquibase Community 4.10.0 by Liquibase
[2022-06-30 19:17:29] INFO [liquibase.database] Set default schema name to public
[2022-06-30 19:17:29] INFO [liquibase.lockservice] Successfully acquired change log lock
[2022-06-30 19:17:29] INFO [liquibase.changelog] Reading from public.databasechangelog
Rolling Back Changeset: src/main/resources/db/changelog/main.yaml::20220629-002::lucas
Rolling Back Changeset: src/main/resources/db/changelog/main.yaml::20220629-001::lucas
[2022-06-30 19:17:29] INFO [liquibase.lockservice] Successfully released change log lock
Liquibase command 'rollbackCount' was executed successfully.
```

Running migrations on application startup/testing:

```text
18:15:10.770 [Test worker] INFO 🐳 [postgres:latest] - Container postgres:latest started in PT1.135467413S
18:15:10.780 [Test worker] DEBUG org.springframework.test.context.support.DependencyInjectionTestExecutionListener - Performing dependency injection for test context [[DefaultTestContext@4d0402b testClass = ApplicationTest, testInstance = com.example.ApplicationTest@100c8b75, testMethod = [null], testException = [null], mergedContextConfiguration = [MergedContextConfiguration@2fa7ae9 testClass = ApplicationTest, locations = '{}', classes = '{class com.example.Application}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@20a8a64e, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@42f48531, org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4c4d27c8, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@505a9d7c, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@ea9b7c6, org.springframework.test.context.support.DynamicPropertiesContextCustomizer@e65ed12b, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@54e041a4], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]]].

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::             (v3.0.0-M3)

2022-06-30T18:15:10.909-03:00  INFO 28221 --- [    Test worker] com.example.ApplicationTest              : Starting ApplicationTest using Java 18.0.1.1 on pc with PID 28221 (started by lucas in /home/lucas/Downloads/poc-spring-liquibase)
2022-06-30T18:15:10.910-03:00  INFO 28221 --- [    Test worker] com.example.ApplicationTest              : No active profile set, falling back to 1 default profile: "default"
2022-06-30T18:15:11.097-03:00  INFO 28221 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2022-06-30T18:15:11.117-03:00  INFO 28221 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 16 ms. Found 2 JPA repository interfaces.
2022-06-30T18:15:11.335-03:00  INFO 28221 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2022-06-30T18:15:11.429-03:00  INFO 28221 --- [    Test worker] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@118cbded
2022-06-30T18:15:11.430-03:00  INFO 28221 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2022-06-30T18:15:11.493-03:00  INFO 28221 --- [    Test worker] liquibase.database                       : Set default schema name to public
2022-06-30T18:15:11.548-03:00  INFO 28221 --- [    Test worker] liquibase.lockservice                    : Successfully acquired change log lock
2022-06-30T18:15:11.656-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : Creating database history table with name: public.databasechangelog
2022-06-30T18:15:11.659-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : Reading from public.databasechangelog
Running Changeset: classpath:db/changelog/main.yaml::20220629-001::lucas
2022-06-30T18:15:11.694-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : SQL in file changes/001-create-country-table.sql executed
2022-06-30T18:15:11.698-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : SQL in file changes/002-create-customer-table.sql executed
2022-06-30T18:15:11.698-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : ChangeSet classpath:db/changelog/main.yaml::20220629-001::lucas ran successfully in 13ms
Running Changeset: classpath:db/changelog/main.yaml::20220629-002::lucas
2022-06-30T18:15:11.705-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : SQL in file changes/003-insert-countries.sql executed
2022-06-30T18:15:11.705-03:00  INFO 28221 --- [    Test worker] liquibase.changelog                      : ChangeSet classpath:db/changelog/main.yaml::20220629-002::lucas ran successfully in 3ms
2022-06-30T18:15:11.708-03:00  INFO 28221 --- [    Test worker] liquibase.lockservice                    : Successfully released change log lock
2022-06-30T18:15:11.753-03:00  INFO 28221 --- [    Test worker] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2022-06-30T18:15:11.774-03:00  INFO 28221 --- [    Test worker] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.9.Final
2022-06-30T18:15:11.852-03:00  INFO 28221 --- [    Test worker] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2022-06-30T18:15:11.906-03:00  INFO 28221 --- [    Test worker] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL10Dialect
2022-06-30T18:15:12.177-03:00  INFO 28221 --- [    Test worker] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2022-06-30T18:15:12.182-03:00  INFO 28221 --- [    Test worker] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2022-06-30T18:15:12.376-03:00  INFO 28221 --- [    Test worker] com.example.ApplicationTest              : Started ApplicationTest in 1.585 seconds (process running for 4.091)
2022-06-30T18:15:13.092-03:00  INFO 28221 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2022-06-30T18:15:13.094-03:00  INFO 28221 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2022-06-30T18:15:13.095-03:00  INFO 28221 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
```
