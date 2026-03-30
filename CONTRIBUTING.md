# Contributing / Flujo de trabajo

Este documento describe pasos recomendados para contribuir al repositorio, normas de branching y comandos útiles para mover los cambios a `main`.

## Prerrequisitos

- Tener `git` configurado y acceso al remoto (credenciales/SSH según corresponda).
- Java (JDK 17 o superior) y `./gradlew` en el repo.

## Branching

- Trabajar en ramas feature basadas en `main`:

```
git checkout -b feature/descripcion-corta
```

- Nombres de ramas: `feature/`, `bugfix/`, `hotfix/` seguido de descripción corta.

## Commits y mensajes

- Usar mensajes claros y con alcance reducido. Ejemplo:

```
git add -A
git commit -m "docs: add testing best practices and contributing guide"
```

## Proceso para mover cambios a `main` (localmente)

1. Asegúrate de que todos los cambios están añadidos y commiteados en tu rama feature.
2. Ejecuta la suite localmente y valida que pasa:

```bash
./gradlew --no-daemon clean test
```

3. Cambia a `main` y actualiza desde el remoto (si existe):

```bash
git fetch origin
git checkout main
git pull origin main
```

4. Fusiona tu rama feature en `main` (preferible vía PR en remoto; para merge local):

```bash
git merge --no-ff feature/mi-feature
```

5. Ejecuta la suite en `main` y valida reportes:

```bash
./gradlew clean test
```

6. Empuja `main` al remoto:

```bash
git push origin main
```

> Nota: Si `main` no existe localmente, crea y empuja desde tu rama:

```bash
git checkout -b main
git push -u origin main
```

## Pull Requests y revisión

- Preferir Pull Requests para revisar cambios antes de mergear en `main`.
- Checklist mínimo para PRs:
  - Tests locales pasan (`./gradlew clean test`).
  - Cambios documentados en `README.md` o `docs/` si es necesario.
  - No se introducen mocks permanentes en `main`.

- Si se modifican claves o valores en `src/test/java/com/automationexercise/utils/Constants.java`,
  actualiza `README.md` y `docs/` y ejecuta la suite antes de abrir el PR.

## Publicar reportes

- Los reportes de Serenity se generan en `target/site/serenity`. Adjuntar o publicar estos artefactos en el CI si se desea revisión visual.

## Información de contacto

Para cambios en la API o fallos sistemáticos, coordinar con el propietario del API antes de modificar las pruebas.
