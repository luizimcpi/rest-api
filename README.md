# rest api - Serasa Experian

## How to run

### Generate docker

#### Build jar file
```
./mvnw clean package
```

#### build docker image
```
docker build -t luizimcpi/rest-api .
```

#### test app docker local
```
docker run -p 8080:8080 luizimcpi/rest-api
```

### Execute Using class
```
Execute RestApiApplication.java class 
*You need jdk adoptopenjdk/openjdk11 installed and configured in your pc
```

## SWAGGER
```
http://localhost:8080/swagger-ui.html
```

## Utils
```
512 bits key generator for encryption
https://www.allkeysgenerator.com/
```

#### Instructions
```
import postman collection located at postman folder in this project

Create an user using '/usuario' endpoint

Do login using '/login' endpoint

Use 'Authorization' header content token that returned at success login and
pass to any another request.

```