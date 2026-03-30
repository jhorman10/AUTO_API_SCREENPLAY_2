# Buenas prácticas para desarrollo de pruebas automatizadas

Esta guía recoge recomendaciones específicas para este proyecto (Serenity + Screenplay + Cucumber) y prácticas generales para garantizar que las pruebas sean fiables, mantenibles y útiles.

## Objetivo

Proveer criterios y convenciones para escribir, organizar, ejecutar y mantener pruebas automatizadas en el repositorio.

## Principios generales

- Determinismo: las pruebas deben producir el mismo resultado si el sistema bajo prueba no cambia.
- Rapidez razonable: diseñar pruebas rápidas; evitar pruebas que tarden mucho sin necesidad.
- Independencia: cada escenario debe poder ejecutarse aisladamente.
- Legibilidad: Gherkin claro, nombres semánticos en Tasks/Questions.
- Mantenibilidad: minimizar duplicación, centralizar configuraciones y constantes.

## Convenciones del proyecto

- Estructura: mantener los paquetes actuales (`runners`, `stepdefinitions`, `tasks`, `questions`, `models`, `utils`).
- Nomenclatura: `CreateAccount`, `GetUserDetail`, `UpdateAccount`, `DeleteAccount` (PascalCase para clases, camelCase para métodos).
- Código de pruebas: cada `Task` una responsabilidad; `Question` para comprobaciones; `Model` para datos.
- No usar comentarios inline en código de producción (la spec del proyecto lo exige).
 - Centralizar literales: rutas, nombres de campos, mensajes esperados y códigos HTTP deben residir en
	 `utils/Constants` y usarse desde `Tasks`, `Questions` y `Steps`. En las features se prefieren **claves**
	 (ej: `"USER_CREATED"`, `"STATUS_OK"`) que se resuelven mediante `Constants.Messages.forKey(...)`
	 y `Constants.Status.forKey(...)`.

## Gherkin y diseño de escenarios

- Escenarios orientados a comportamiento (qué hace el sistema), no implementación.
- Evitar pasos demasiado técnicos; usar Steps que deleguen en Tasks.
- Preferir escenarios cortos y enfocados; si un escenario crece, separar en varios.

## Screenplay — prácticas recomendadas

- Tasks: 1 responsabilidad; sin lógica de aserción.
- Questions: extraen información del sistema para aserciones.
- Interactions: encapsulan llamadas HTTP de bajo nivel (multipart, contentType, headers).
- Actor: mantener sin estado global compartido salvo `remember()` cuando sea imprescindible.

## Gestión de datos de prueba

- Generar identificadores únicos (emails con timestamp) desde `TestDataGenerator`.
- Evitar datos sensibles en el repositorio. Utilizar variables de entorno o secretos del CI para credenciales.
- Diseñar flujos que limpien los datos creados (DELETE al final del flujo). Los tests deben ser idempotentes.

## Configuración y entornos

- Base URL: usar `-Dapi.base.url=` o `serenity.conf` para apuntar al entorno correcto.
- No usar mocks por defecto: el repo actual ejecuta contra la API real; si se necesita un mock para desarrollo, aislarlo en una rama/feature y documentarlo; no mantener mocks en `main`.

## Ejecución local

Ejecutar suite completa:

```bash
./gradlew clean test
```

Ejecutar por tag:

```bash
./gradlew clean test -Dcucumber.filter.tags="@api-crud"
```

Abrir reporte Serenity generado:

```bash
xdg-open target/site/serenity/index.html
```

o servir carpeta de reportes:

```bash
python3 -m http.server --directory target/site/serenity 8000
# Abrir http://localhost:8000/index.html
```

## Integración en CI

- Ejecutar `./gradlew --no-daemon clean test` en un job dedicado.
- Publicar `target/site/serenity` como artefacto del job.
- Bloquear merge a `main` si la suite falla.

## Reportes y artefactos

- Serenity genera HTML y JSON en `target/site/serenity`.
- Mantener `summary.txt` como referencia rápida.

## Calidad del código de pruebas

- Evitar literales mágicos: centralizar rutas, nombres de campos y mensajes esperados en un archivo de constantes (`utils/Constants` o `utils/TestDataGenerator`).
- Mantener las clases pequeñas y con responsabilidad única.
- Evitar lógica condicional compleja dentro de Steps y Tasks.

## Branching y flujo de trabajo

- `main` debe contener código estable: tests que pasan y documentación actualizada.
- Trabajar en ramas feature: `feature/descripcion-corta` o `001-api-crud-screenplay`.
- Abrir Pull Requests y ejecutar la suite en el CI antes de mergear.

## Añadir nuevas pruebas

1. Crear un `feature` en `src/test/resources/features/api/` con Gherkin claro.
2. Implementar Steps que deleguen en Tasks existentes o crear nuevas Tasks si es necesario.
3. Añadir/actualizar `models` y `utils` para manejar datos.
4. Ejecutar `./gradlew clean test` y validar el reporte de Serenity.

## Mantenimiento y actualización de endpoints

- Centralizar endpoints en `utils/Constants` o propiedad en `serenity.conf`.
- Si cambian endpoints: actualizar la constante, ejecutar la suite completa y documentar el cambio en `docs/`.
 - Además de endpoints, centralizar también los códigos de respuesta y mensajes en `utils/Constants`.
	 Si cambian claves o textos esperados, actualiza `README.md` y `docs/BEST_PRACTICES_TESTS.md`.

## Troubleshooting rápido

- Fallos por tiempo de espera: validar conectividad y retry en el servicio externo.
- 400/422: comprobar payload y nombres de campos (`mobile_number` vs `phone_number`).
- 5xx: si el servicio está inestable, coordinar con el equipo de API; no introducir mocks directamente en `main`.

---

Mantener esta guía viva: actualiza `docs/BEST_PRACTICES_TESTS.md` cuando agregues convenciones o aprendizajes repetidos.
