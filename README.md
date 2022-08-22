# sb-streams-app

Spring Boot: https://spring.io/guides/gs/spring-boot/

Pub/Sub: https://www.baeldung.com/spring-kafka

Spring Boot + Kafka Streams: https://www.baeldung.com/spring-boot-kafka-streams

Postgres+Debezium+Kafka: https://arctype.com/blog/kafka-tutorial-1/

How to run (refer to the documentaion above):
1. Init config (refer to the doc above)
2. `./run_all.sh` - make sure all containers up and running
3. Init DB: from the DB docker machine:
   1. `createuser --interactive --pwprompt` with superuser `smartbrick` and the same password
   2. `createdb -O smartbrick smartbrick_db`
   3. `docker exec -i CONTAINER_ID bash -c "PGPASSWORD=smartbrick psql --username smartbrick smartbrick_db" < path/to/dump.sql`
   4. Connect to DB: `psql -U smartbrick -d smartbrick_db`
   5. Prepare DB for debezium connect: `alter system set wal_level to 'logical';`
   6. setup Replica identity for all tables used by Debezium - `alter table <TABLE_NAME> REPLICA IDENTITY FULL;`, this is needed to track previous record data in "delete" events (more on this - https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-replica-identity)
   7. From the docker machine (outside of psql context) install wal2json (pay attention to the postgres version): `$ apt-get update && apt-get install postgresql-14-wal2json
      `
4. From the `/docker` folder:
   1. `./consume_topic.sh` - start Kafka console consumer against test topic
   2. Enter Postgres docker and insert some record to the table: `postgres=# insert into test (name) values ('Arctype Kafka Test 4!');`
   3. Check Kafka consumer console - make sure JSON events appear

### Debezium
#### Connector
List of active connectors: `curl 127.0.0.1:8083/connectors`

### Kafka
