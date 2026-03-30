# Research: API CRUD — Cuenta de Usuario

**Date**: 2026-03-30
**Feature**: 001-api-crud-screenplay

## Decision 1: Framework de automatización API

**Decision**: Serenity BDD + Serenity Rest Assured + Screenplay pattern
**Rationale**: Requerido por la rúbrica del reto. Serenity Rest envuelve Rest
Assured y genera reportes HTML detallados de cada llamada HTTP. El patrón
Screenplay permite separar responsabilidades (Actor → Task → Interaction →
Question) de forma extensible.
**Alternatives considered**:
- Rest Assured puro: No genera reportes Serenity ni soporta Screenplay.
- Karate: No cumple el requisito de patrón Screenplay + Serenity BDD.

## Decision 2: Formato de envío de datos (form-data vs JSON body)

**Decision**: Usar `multipart/form-data` para POST, PUT y DELETE.
**Rationale**: La API de AutomationExercise espera parámetros como form fields,
no como JSON body. Los endpoints devuelven `405` o ignoran el cuerpo si se
envía JSON. Confirmado con la documentación oficial de la API list.
**Alternatives considered**:
- JSON body (`application/json`): La API no lo acepta para estos endpoints.

## Decision 3: Estrategia de datos de prueba

**Decision**: Email único con timestamp (`autoqa<epoch>@test.com`).
**Rationale**: Evita colisiones entre ejecuciones concurrentes. Permite
ejecución idempotente: crear → usar → eliminar en cada corrida.
**Alternatives considered**:
- Email fijo + cleanup manual: frágil y no concurrente.
- UUID en email: válido pero menos legible en reportes.

## Decision 4: Versiones de dependencias

**Decision**: Serenity BDD 4.2.x, Cucumber 7.x, JUnit 5 (Jupiter), Gradle 8.x.
**Rationale**: Serenity 4.x es la versión estable más reciente con soporte
nativo de Cucumber 7 y JUnit Platform. Gradle 8.x es compatible con Java 17.
**Alternatives considered**:
- Serenity 3.x + Cucumber 6: deprecated, no recibe parches.
- Maven: la rúbrica exige Gradle.

## Decision 5: Estructura de paquetes Screenplay

**Decision**: `tasks/`, `interactions/`, `questions/`, `models/`, `utils/`.
**Rationale**: Sigue la convención estándar de Serenity Screenplay. Cada Task
tiene una sola responsabilidad (SRP exigido por la rúbrica). Las Questions
encapsulan assertions sobre la respuesta.
**Alternatives considered**:
- Paquete plano (`steps/`): No cumple SRP ni la separación Screenplay.

## Decision 6: Manejo de respuestas HTML-wrapped

**Decision**: Extraer el JSON del body de respuesta usando `jsonPath()` de
Serenity Rest; si falla, loguear advertencia y marcar como fallo de contrato.
**Rationale**: Algunos endpoints de AutomationExercise devuelven el JSON
envuelto en HTML cuando se accede sin headers adecuados. Usando
`Content-Type: application/x-www-form-urlencoded` y parseando la respuesta
como texto, se puede extraer el JSON correctamente.
**Alternatives considered**:
- Ignorar el wrapper: podría enmascarar fallos reales de contrato.
