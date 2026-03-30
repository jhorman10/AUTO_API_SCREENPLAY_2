# AUTO_API_SCREENPLAY

Automatización de API — Ciclo completo CRUD sobre la entidad **Cuenta de Usuario** de [AutomationExercise](https://automationexercise.com).

Este repositorio resuelve el **punto 3** del reto de nivelación para automatización.

## Stack

| Herramienta | Versión / Detalle |
|---|---|
| Java | JDK 17 |
| Build | Gradle (wrapper incluido) |
| Framework | Serenity BDD + Serenity Rest |
| Patrón | Screenplay (Actores, Tareas, Interacciones, Preguntas) |
| BDD Runner | Cucumber |
| Reportes | Serenity HTML Reports |

## Constantes centralizadas

- Los literales clave (endpoints, campos de formulario, mensajes esperados y códigos HTTP)
  están centralizados en [src/test/java/com/automationexercise/utils/Constants.java](src/test/java/com/automationexercise/utils/Constants.java).
- En los archivos `.feature` preferimos usar **claves** para mensajes y códigos de estado en lugar de literales:
  - Mensajes: por ejemplo `"USER_CREATED"`, `"USER_UPDATED"`, `"ACCOUNT_DELETED"`.
  - Códigos HTTP: por ejemplo `"STATUS_CREATED"` (201) y `"STATUS_OK"` (200).
- Los StepDefinitions soportan ambas formas: el paso puede recibir un `{int}` (número) o un `{string}` (clave),
  que se resuelve internamente vía `Constants.Status.forKey(...)` y `Constants.Messages.forKey(...)`.

## Endpoints probados

| Verbo | Endpoint | API # | Descripción |
|---|---|---|---|
| POST | `/api/createAccount` | API 11 | Crear cuenta de usuario |
| GET | `/api/getUserDetailByEmail` | API 14 | Consultar cuenta por email |
| PUT | `/api/updateAccount` | API 13 | Actualizar cuenta de usuario |
| DELETE | `/api/deleteAccount` | API 12 | Eliminar cuenta de usuario |

Referencia completa: https://automationexercise.com/api_list

## Estructura del proyecto

```
src/test/
├── java/com/automationexercise/
│   ├── runners/          # Cucumber runners
│   ├── stepdefinitions/  # Glue entre Gherkin y Tasks
│   ├── tasks/            # Tareas Screenplay (una responsabilidad por clase)
│   ├── interactions/     # Interacciones de bajo nivel con la API
│   ├── questions/        # Preguntas Screenplay (assertions)
│   ├── models/           # POJOs / data builders
│   └── utils/            # Utilidades (generador de datos, constantes)
└── resources/
    ├── features/
    │   └── api/          # Archivos .feature (Gherkin)
    ├── serenity.conf     # Configuración de Serenity
    └── logback-test.xml  # Logging
```

## Prerequisitos

- **Java 17** instalado y en el PATH (`java -version`)
- Conexión a Internet (los tests llaman a la API pública)

## Ejecución

### Ejecutar toda la suite

```bash
./gradlew clean test
```

### Ejecutar por tag de Cucumber

```bash
./gradlew clean test -Dcucumber.filter.tags="@api-crud"
```

Tags disponibles:

| Tag | Escenario |
|---|---|
| `@api-crud` | Todos los escenarios API |
| `@api-post` | Crear cuenta (POST) |
| `@api-get` | Consultar cuenta (GET) |
| `@api-put` | Actualizar cuenta (PUT) |
| `@api-delete` | Eliminar cuenta (DELETE) |

### Ejecutar un feature específico

```bash
./gradlew clean test -Dcucumber.features="src/test/resources/features/api"
```

### Configurar base URL (opcional)

```bash
./gradlew clean test -Dapi.base.url=https://automationexercise.com/api
```

El valor por defecto ya apunta a `https://automationexercise.com/api` en `serenity.conf`.

### Notas sobre mocks

Este proyecto NO utiliza mocks en la ejecución por defecto: las pruebas apuntan
al API real configurado en `api.base.url`. No es necesario pasar propiedades
para activar mocks locales.

## Reportes

Después de ejecutar los tests, el reporte de Serenity se genera automáticamente:

```bash
# Linux
xdg-open target/site/serenity/index.html

# macOS
open target/site/serenity/index.html

# Windows
start target/site/serenity/index.html
```

## Estrategia de datos de prueba

- Cada ejecución genera un **email único con timestamp** (ej: `autoqa1711817400@test.com`)
  para evitar colisiones entre ejecuciones paralelas.
- El flujo CRUD sigue el orden: **POST → GET → PUT → DELETE**, asegurando que
  la cuenta creada se elimina al finalizar.
- Los tests son **idempotentes**: no dejan datos residuales en el sistema.

## Escenarios Gherkin (ejemplo)

```gherkin
@api-crud
Feature: Ciclo CRUD de cuenta de usuario via API

  @api-post
  Scenario: Crear una nueva cuenta de usuario
    Given el usuario prepara los datos de registro con un email unico
    When el usuario envia la solicitud de creacion de cuenta
    Then el codigo de respuesta es "STATUS_CREATED"
    And el mensaje de respuesta es "USER_CREATED"

  @api-get
  Scenario: Consultar los detalles de la cuenta creada
    Given existe una cuenta de usuario registrada
    When el usuario consulta los detalles de la cuenta por email
    Then el codigo de respuesta es "STATUS_OK"
    And los detalles contienen el email registrado

  @api-put
  Scenario: Actualizar los datos de la cuenta
    Given existe una cuenta de usuario registrada
    When el usuario actualiza el nombre de la cuenta
    Then el codigo de respuesta es "STATUS_OK"
    And el mensaje de respuesta es "USER_UPDATED"

  @api-delete
  Scenario: Eliminar la cuenta de usuario
    Given existe una cuenta de usuario registrada
    When el usuario envia la solicitud de eliminacion de cuenta
    Then el codigo de respuesta es "STATUS_OK"
    And el mensaje de respuesta es "ACCOUNT_DELETED"
```

## Criterios de evaluación cubiertos

- **Dominio técnico**: Patrón Screenplay con Serenity Rest (Actores, Tareas, Preguntas).
- **Arquitectura**: Gradle para dependencias, `serenity.conf` para configuración.
- **Gherkin**: Escenarios declarativos enfocados en comportamiento de negocio.
- **Código limpio**: Sin comentarios, nomenclatura semántica en clases y métodos.
- **Responsabilidad única**: Cada Task tiene una sola responsabilidad.

## Documentación y buenas prácticas

Consulta la guía de buenas prácticas para desarrollo de pruebas automatizadas y el proceso de contribución en:

- [docs/BEST_PRACTICES_TESTS.md](docs/BEST_PRACTICES_TESTS.md)
- [CONTRIBUTING.md](CONTRIBUTING.md)
