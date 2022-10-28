# XM Crypto Recommendation Service

This service is built to help anyone who wants to invest in crypto but doesn't know which crypto to choose.

## How to build

### Clone this repository

```shell
git clone https://github.com/eparmon/crypto-recommendation-service.git
```

### Create MariaDB databases

If you don't have MariaDB server installed, install it.

```text
CREATE DATABASE crypto;
CREATE USER crypto IDENTIFIED BY '<password>';
GRANT ALL PRIVILEGES ON crypto.* TO crypto;
FLUSH PRIVILEGES;
```

Don't forget to change the password and save it.

Repeat this procedure for a test database ("cryptotest")

### Create the properties files

```shell
cp src/main/resources/application.properties.sample src/main/resources/application.properties
cp src/main/resources/liquibase.properties.sample src/main/resources/liquibase.properties
cp src/test/resources/application.properties.sample src/test/resources/application.properties
```

In newly created properties files, substitute placeholders with relevant values. Use values for test database in
`src/test/resources/application.properties` so that tests would have no influence on your primary database.

### Build

```shell
./mvnw clean install
```

### Run

```shell
./mvnw spring-boot:run
```

## Using Docker

This service can be run in Docker. You need to do the same steps as above up until the *Run* section. 
Note that you may need to change the databases' URLs so that they would be accessible in Docker.  

### Build

```shell
sudo docker build -t xm/recommendation-service .
```

### Run in Docker

```shell
sudo docker run xm/recommendation-service
```

## Use the service

Please use [Swagger](http://localhost:8080/webjars/swagger-ui/index.html) UI to learn the details about endpoints 
available and test them.