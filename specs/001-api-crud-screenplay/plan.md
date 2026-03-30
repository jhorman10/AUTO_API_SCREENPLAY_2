# Implementation Plan: API CRUD — Cuenta de Usuario

**Branch**: `001-api-crud-screenplay` | **Date**: 2026-03-30 | **Spec**: [spec.md](spec.md)
**Input**: Feature specification from `/specs/001-api-crud-screenplay/spec.md`

## Summary

Construir una suite de pruebas automatizadas que valide el ciclo CRUD completo
(POST crear, GET consultar, PUT actualizar, DELETE eliminar) sobre la entidad
"Cuenta de Usuario" de la API pública de AutomationExercise. Se usa el patrón
Screenplay con Serenity Rest + Cucumber sobre Gradle, cumpliendo los criterios
del reto de nivelación (código limpio, reportes Serenity, arquitectura extensible).

## Technical Context

**Language/Version**: Java 17 (LTS)
**Primary Dependencies**: Serenity BDD 4.x, Serenity Screenplay, Serenity Rest Assured, Cucumber 7.x, JUnit 5
**Storage**: N/A (no persistencia local; los datos viven en la API remota)
**Testing**: Cucumber + Serenity BDD (reporte HTML) ejecutado vía `./gradlew clean test`
**Target Platform**: JVM multiplataforma (Linux, macOS, Windows)
**Project Type**: Test automation suite (API testing)
**Performance Goals**: Suite completa ejecuta en < 60 s
**Constraints**: Requiere conectividad a `automationexercise.com`; los endpoints usan form-data (no JSON body)
**Scale/Scope**: 4 escenarios (1 por verbo HTTP), 1 entidad (UserAccount), 4 endpoints

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Spec define user stories independientes con criterios de éxito medibles y
      supuestos explícitos.
- [x] El MVP (User Story 1 — POST crear) se puede entregar sin depender de
      historias de menor prioridad.
- [x] La verificación requerida para cada historia está identificada antes de
      implementar (status HTTP + campo clave + GET de confirmación).
- [x] Los contratos de API están documentados en `contracts/api-contract.md`.
- [x] No quedan placeholders ni clarifications bloqueantes en este plan.

> Nota: La constitución del proyecto (`.specify/memory/constitution.md`) está aún
> en estado de plantilla. Los gates anteriores se derivan de los principios
> implícitos del reto de nivelación y del workflow Speckit.

## Project Structure

### Documentation (this feature)

```text
specs/001-api-crud-screenplay/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── api-contract.md
├── checklists/
│   └── requirements.md
└── tasks.md
```

### Source Code (repository root)

```text
build.gradle
settings.gradle
gradlew / gradlew.bat
gradle/wrapper/
serenity.properties
src/test/
├── java/com/automationexercise/
│   ├── runners/
│   │   └── CrudApiTestRunner.java
│   ├── stepdefinitions/
│   │   └── ApiCrudStepDefinitions.java
│   ├── tasks/
│   │   ├── CreateAccount.java
│   │   ├── GetUserDetail.java
│   │   ├── UpdateAccount.java
│   │   └── DeleteAccount.java
│   ├── interactions/
│   │   └── ExecuteRequest.java
│   ├── questions/
│   │   ├── ResponseCode.java
│   │   └── ResponseMessage.java
│   ├── models/
│   │   └── UserData.java
│   └── utils/
│       └── TestDataGenerator.java
└── resources/
    ├── features/
    │   └── api/
    │       └── user_account_crud.feature
    ├── serenity.conf
    └── logback-test.xml
```

**Structure Decision**: Proyecto Gradle único de tipo test-only (sin `src/main`).
Todo el código vive bajo `src/test/` siguiendo la convención estándar de Serenity
BDD para suites de automatización. Los paquetes siguen el patrón Screenplay:
`tasks`, `interactions`, `questions`, `models`, `utils`.

## Complexity Tracking

> No hay violaciones de constitución que justificar.
