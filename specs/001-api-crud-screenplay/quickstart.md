# Quickstart: API CRUD — Cuenta de Usuario

**Date**: 2026-03-30
**Feature**: 001-api-crud-screenplay

## Prerrequisitos

1. Java 17 instalado: `java -version` debe mostrar `17.x`
2. Conexión a Internet (la API corre en `automationexercise.com`)
3. Gradle wrapper incluido (no necesitas instalar Gradle globalmente)

## Ejecución rápida

```bash
git clone <url-del-repo>
cd AUTO_API_SCREENPLAY
./gradlew clean test
```

## Verificación manual de endpoints (curl)

### 1. Crear cuenta (POST)

```bash
curl -X POST https://automationexercise.com/api/createAccount \
  -F "name=Auto QA" \
  -F "email=autoqa$(date +%s)@test.com" \
  -F "password=Test1234" \
  -F "title=Mr" \
  -F "birth_date=15" \
  -F "birth_month=6" \
  -F "birth_year=1990" \
  -F "firstname=Auto" \
  -F "lastname=QA" \
  -F "company=TestCorp" \
  -F "address1=123 Main St" \
  -F "address2=" \
  -F "country=United States" \
  -F "zipcode=90001" \
  -F "state=California" \
  -F "city=Los Angeles" \
  -F "phone_number=5551234567"
```

Respuesta esperada: `{"responseCode": 201, "message": "User created!"}`

### 2. Consultar cuenta (GET)

```bash
curl "https://automationexercise.com/api/getUserDetailByEmail?email=<EMAIL_CREADO>"
```

Respuesta esperada: JSON con `responseCode: 200` y objeto `user`.

### 3. Actualizar cuenta (PUT)

```bash
curl -X PUT https://automationexercise.com/api/updateAccount \
  -F "name=Updated QA" \
  -F "email=<EMAIL_CREADO>" \
  -F "password=Test1234" \
  -F "title=Mr" \
  -F "birth_date=15" \
  -F "birth_month=6" \
  -F "birth_year=1990" \
  -F "firstname=Updated" \
  -F "lastname=QA" \
  -F "company=TestCorp" \
  -F "address1=123 Main St" \
  -F "address2=" \
  -F "country=United States" \
  -F "zipcode=90001" \
  -F "state=California" \
  -F "city=Los Angeles" \
  -F "phone_number=5551234567"
```

Respuesta esperada: `{"responseCode": 200, "message": "User updated!"}`

### 4. Eliminar cuenta (DELETE)

```bash
curl -X DELETE https://automationexercise.com/api/deleteAccount \
  -F "email=<EMAIL_CREADO>" \
  -F "password=Test1234"
```

Respuesta esperada: `{"responseCode": 200, "message": "Account deleted!"}`

## Ver reportes

```bash
xdg-open build/reports/serenity/index.html   # Linux
open build/reports/serenity/index.html        # macOS
```

## Ejecución por tag

```bash
./gradlew clean test -Dcucumber.filter.tags="@api-post"
./gradlew clean test -Dcucumber.filter.tags="@api-get"
./gradlew clean test -Dcucumber.filter.tags="@api-put"
./gradlew clean test -Dcucumber.filter.tags="@api-delete"
```

## Flujo esperado del test

```
1. POST /api/createAccount  →  201 "User created!"
2. GET  /api/getUserDetailByEmail?email=...  →  200 + user object
3. PUT  /api/updateAccount  →  200 "User updated!"
4. DELETE /api/deleteAccount  →  200 "Account deleted!"
5. GET  /api/getUserDetailByEmail?email=...  →  404 "Account not found..."
```
