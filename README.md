# Oscar App

Aplicacao cliente-servidor composta por uma API REST em Spring Boot e um aplicativo Android nativo.

## Modulos

- `api/` - Backend Spring Boot com PostgreSQL
- `android-app/` - Aplicativo Android nativo em Kotlin

## Requisitos

- Docker e Docker Compose
- Java 21 e Maven (desenvolvimento local da API)
- Android Studio (desenvolvimento do app Android)

## Executar com Docker

```bash
docker compose up --build
```

A API ficara disponivel em `http://localhost:8080`.  
O banco de dados PostgreSQL ficara disponivel em `localhost:5432`.

## Desenvolvimento local da API

```bash
cd api
mvn spring-boot:run
```

Configure as variaveis de ambiente antes de iniciar:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/oscar
SPRING_DATASOURCE_USERNAME=oscar
SPRING_DATASOURCE_PASSWORD=oscar
```

## Testes da API

```bash
cd api
mvn test
```

## Build do Android

```bash
cd android-app
./gradlew assembleDebug
```
