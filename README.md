# Food Ordering Application

## Prerequisites
- Java 17
- Maven 3.6.3
- Docker 20.10.25

## Instructions
- Start a postgres container from Docker: 
```shell
docker run --name myPostgresDb -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=postgresDB -d postgres
```
- Check if the database started successfully:
```shell
 docker container ps
 CONTAINER ID   IMAGE      COMMAND                  CREATED       STATUS       PORTS                                       NAMES
5f790788a637   postgres   "docker-entrypoint.s…"   5 hours ago   Up 5 hours   0.0.0.0:5432->5432/tcp, :::5432->5432/tcp   myPostgresDb

```
- Run the FoodOrderingApplication.main method