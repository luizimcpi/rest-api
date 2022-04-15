# rest api - Serasa Experian

## How to run

### Generate docker

#### Build jar file
```
./mvnw clean package
```

### build docker image
```
docker build -t luizimcpi/rest-api .
```

### test app docker local
```
docker run -p 8080:8080 luizimcpi/rest-api
```

### Execute Using class
```
Execute RestApiApplication.java class 
*You need jdk adoptopenjdk/openjdk11 installed and configured in your pc
```

## H2 Console
```
http://localhost:8080/h2-console
```

## SWAGGER
```
http://localhost:8080/swagger-ui.html
```