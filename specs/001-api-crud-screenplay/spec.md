# Feature Specification: API CRUD — Cuenta de Usuario (AutomationExercise)

**Feature Branch**: `001-api-crud-screenplay`  
**Created**: 2026-03-30  
**Status**: Draft  
**Input**: Reto de nivelación — Automatización de API (CRUD) para AutomationExercise. Objetivo: validar ciclo completo POST/GET/PUT/DELETE sobre la entidad "Cuenta de Usuario" usando Screenplay + Serenity Rest.

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Crear cuenta de usuario (Priority: P1)

Como QA/automatizador quiero crear una cuenta de usuario vía API para poder
verificar que el servicio de registro crea recursos válidos y rastreables.

**Why this priority**: Crear datos es prerequisito para las demás operaciones
CRUD; sin creación no es posible validar lectura, actualización ni eliminación.

**Independent Test**: Ejecutar POST `/api/createAccount` con payload válido
usando un email único (timestamp). Verificar 200|201 y que la respuesta contenga
identificador/email.

**Primary Verification**: contrato HTTP (status 200/201), esquema JSON básico,
consulta `GET` posterior para confirmar persistencia.

**Acceptance Scenarios**:
1. **Given** que no existe una cuenta con `email=test+timestamp@example.com`,
   **When** se POSTea el payload requerido, **Then** la respuesta es 200 o 201 y
   el cuerpo contiene `email` y `id`.
2. **Given** un payload con campos obligatorios ausentes, **When** se POSTea,
   **Then** la respuesta es 4xx con mensaje de validación.

---

### User Story 2 - Consultar cuenta por email (Priority: P2)

Como QA quiero consultar la cuenta creada para validar que la información
persistida coincide con lo enviado al crearla.

**Why this priority**: Verifica integridad de lectura y contracto de respuesta.

**Independent Test**: Ejecutar GET `/api/getUserDetailByEmail?email=<email>` y
verificar código 200 y campos clave (`email`, `name`, `id`).

**Primary Verification**: respuesta 200 y comprobación de al menos 3 campos
coherentes con el payload de creación.

**Acceptance Scenarios**:
1. **Given** una cuenta existente, **When** se solicita GET por email, **Then**
   la respuesta es 200 y contiene `email`, `name` y `id`.

---

### User Story 3 - Actualizar cuenta (Priority: P3)

Como QA quiero modificar campos de la cuenta para validar que el endpoint de
actualización aplica cambios correctamente.

**Why this priority**: Asegura que la API soporta mutaciones y mantiene datos
consistentes.

**Independent Test**: Ejecutar PUT `/api/updateAccount` (o el endpoint
correspondiente) con campos modificados; verificar respuesta 200 y que un GET
posterior refleja los cambios.

**Primary Verification**: status 200 + verificación de campos actualizados vía
GET.

**Acceptance Scenarios**:
1. **Given** una cuenta existente, **When** se envía PUT con nuevo `name`,
   **Then** la respuesta es 200 y GET devuelve el `name` actualizado.

---

### User Story 4 - Eliminar cuenta (Priority: P3)

Como QA quiero eliminar la cuenta creada para comprobar el comportamiento de
eliminación y dejar el sistema sin residuos de pruebas.

**Why this priority**: Limpieza de datos de prueba y verificación del flujo
completo CRUD.

**Independent Test**: Ejecutar DELETE `/api/deleteAccount` con `email` y
`password` válidos; verificar 200 y que GET posterior devuelva 404 o mensaje de
usuario no encontrado.

**Primary Verification**: status 200 en DELETE y comprobación negativa vía GET.

---

### Edge Cases

- Crear con email duplicado → esperar 4xx o mensaje de conflicto (no duplicar).
- Intentar GET/PUT/DELETE sobre cuenta inexistente → esperar 404 o error claro.
- Intentar DELETE con credenciales inválidas → esperar 4xx.
- Respuesta del servicio fuera de contrato (campos faltantes) → marcar como
  fallo de contrato.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: La suite DEBE crear una cuenta de usuario vía POST `/api/createAccount`
  con payload válido y verificar respuesta 200 con mensaje de éxito.
- **FR-002**: La suite DEBE consultar (GET) los detalles de la cuenta creada vía
  `/api/getUserDetailByEmail` y verificar respuesta 200 con campos principales.
- **FR-003**: La suite DEBE actualizar (PUT) campos editables de la cuenta vía
  `/api/updateAccount` y verificar respuesta 200 con mensaje de éxito.
- **FR-004**: La suite DEBE eliminar (DELETE) la cuenta vía `/api/deleteAccount`
  con email y password válidos y verificar respuesta 200.
- **FR-005**: Las pruebas DEBEN usar datos únicos por ejecución (email con
  timestamp) para evitar colisiones entre ejecuciones paralelas.
- **FR-006**: Cada escenario DEBE validar: código HTTP esperado y al menos un
  campo clave del cuerpo de respuesta.
- **FR-007**: La suite DEBE ser ejecutable con `./gradlew clean test` y generar
  reportes Serenity BDD en `build/reports/serenity/`.

### Operational & Contract Requirements

- **OCR-001**: Base URL configurable vía `-Dapi.base.url` (default:
  `https://automationexercise.com/api`).
- **OCR-002**: Endpoints primarios:
  - POST `/api/createAccount` — API 11 (crear cuenta)
  - GET `/api/getUserDetailByEmail` — API 14 (consultar por email)
  - PUT `/api/updateAccount` — API 13 (actualizar cuenta)
  - DELETE `/api/deleteAccount` — API 12 (eliminar cuenta, requiere email + password)
- **OCR-003**: La respuesta de creación puede ser 200 (la API no usa 201).
  Las validaciones deben tolerar este comportamiento.

### Code Quality Requirements (mandatory)

- **CQR-001**: No debe haber comentarios en el código fuente de la suite de
  pruebas. Las explicaciones, decisiones de diseño o notas operativas deben
  registrarse en la especificación o en la documentación del proyecto (por
  ejemplo, `README.md`), no en comentarios inline dentro de las clases, tareas
  o step definitions.
- **CQR-002**: No se permiten "magic strings" ni números literales sueltos en
  el código. Todos los valores reutilizables (rutas de endpoints, mensajes
  esperados, códigos numéricos significativos, timeouts, nombres de campo usados
  en varias pruebas, etc.) deben declararse como constantes con nombres
  descriptivos y centralizados (por ejemplo, una clase/archivo de constantes)
  y referenciarse desde allí.
- **CQR-003**: Las constantes que representan contratos, mensajes esperados o
  valores que puedan variar entre entornos deben ser fácilmente parametrizables
  (p. ej. a través de propiedades o variables de entorno) y no hardcodeadas en
  los flujos de prueba.
- **Verification**: El cumplimiento será verificado mediante revisión de código
  y revisiones de PR: no debe existir código con comentarios explicativos ni
  literales repetidos; las constantes deben estar definidas y en uso.

### Key Entities

- **UserAccount**: Cuenta de usuario registrada vía API. Atributos principales:
  `name`, `email`, `password`, `title`, `birth_date`, `birth_month`,
  `birth_year`, `firstname`, `lastname`, `company`, `address1`, `address2`,
  `country`, `zipcode`, `state`, `city`, `phone_number`.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Los 4 escenarios CRUD pasan exitosamente en menos de 60 segundos
  de ejecución total local.
- **SC-002**: Cada escenario valida el código HTTP esperado (200) y al menos un
  campo clave del recurso en la respuesta.
- **SC-003**: La suite genera un reporte Serenity accesible en
  `build/reports/serenity/index.html` sin errores de parsing.
- **SC-004**: La ejecución es idempotente: usa email único con timestamp y
  elimina la cuenta al finalizar, sin dejar datos residuales.

## Assumptions

- La API de AutomationExercise está disponible públicamente y no requiere token
  de autenticación para las operaciones de cuenta de usuario.
- El endpoint POST `/api/createAccount` devuelve 200 (no 201) al crear
  exitosamente; se acepta este comportamiento como el contrato real.
- Los endpoints PUT y DELETE requieren `email` y `password` como parámetros
  de formulario (form-data), no JSON body.
- La API puede devolver HTML wrapper en algunas respuestas; las validaciones
  deben extraer el JSON del cuerpo cuando sea necesario.
- Se usa email con timestamp (`autoqa<ts>@test.com`) para evitar colisiones
  entre ejecuciones concurrentes.
- Java 17 y Gradle wrapper están disponibles en el entorno de ejecución.
