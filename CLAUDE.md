# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Oscar-app is a client-server mobile application with two independent modules:
- **`api/`** — Spring Boot 4.1.0 REST backend (Java 21, Maven, PostgreSQL)
- **`android-app/`** — Native Android app (Kotlin, Gradle, Material Design 3)

## Commands

### Backend (`api/`)

```bash
mvn spring-boot:run       # Start the API server
mvn test                  # Run all tests
mvn compile               # Compile without running tests
mvn clean install         # Full build
```

### Android (`android-app/`)

```bash
./gradlew assembleDebug              # Build debug APK
./gradlew test                       # Run unit tests
./gradlew connectedAndroidTest       # Run instrumented tests on device/emulator
./gradlew build                      # Full build
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## Architecture

### Backend (`api/`)

Spring Boot REST API with PostgreSQL. Key dependencies already wired in `pom.xml`:
- **Spring Data JPA** — entity/repository layer
- **Spring Security** — authentication/authorization
- **Spring Validation** — request validation
- **Lombok** — boilerplate reduction (annotation processor configured)

Main class: `api/src/main/java/com/edukaiquedev/api/ApiApplication.java`  
Config: `api/src/main/resources/application.properties` (currently minimal — database URL and credentials need to be added)

### Android (`android-app/`)

Single-activity architecture (so far). Entry point: `app/src/main/java/com/edukaiquedev/android_app/MainActivity.kt`

- UI built with **ConstraintLayout** and **Material Design 3** (light/dark theme support)
- Version catalog at `gradle/libs.versions.toml` — add/update dependencies there, not inline in build files
- Min SDK 29, Target/Compile SDK 36

### Module Relationship

The Android app is intended to consume the Spring Boot REST API over HTTP. No networking layer has been implemented yet in either module.
