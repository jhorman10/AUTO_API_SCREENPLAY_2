# API Contract: AutomationExercise User Account CRUD

**Date**: 2026-03-30
**Base URL**: `https://automationexercise.com`

## POST /api/createAccount (API 11)

**Purpose**: Crear/registrar una nueva cuenta de usuario.

**Request**:
- Method: `POST`
- Content-Type: `multipart/form-data`
- Parameters (form fields):

| Campo | Tipo | Requerido |
|---|---|---|
| name | String | Sí |
| email | String | Sí |
| password | String | Sí |
| title | String | Sí |
| birth_date | String | Sí |
| birth_month | String | Sí |
| birth_year | String | Sí |
| firstname | String | Sí |
| lastname | String | Sí |
| company | String | No |
| address1 | String | Sí |
| address2 | String | No |
| country | String | Sí |
| zipcode | String | Sí |
| state | String | Sí |
| city | String | Sí |
| phone_number | String | Sí |

**Response (success)**:
```json
{
  "responseCode": 201,
  "message": "User created!"
}
```

**Response (email ya existe)**:
```json
{
  "responseCode": 400,
  "message": "Email already exists!"
}
```

---

## GET /api/getUserDetailByEmail (API 14)

**Purpose**: Consultar detalles de una cuenta por email.

**Request**:
- Method: `GET`
- Query Parameter: `email` (String, requerido)
- Ejemplo: `GET /api/getUserDetailByEmail?email=test@example.com`

**Response (success)**:
```json
{
  "responseCode": 200,
  "user": {
    "id": 12345,
    "name": "Test User",
    "email": "test@example.com",
    "title": "Mr",
    "birth_day": "15",
    "birth_month": "6",
    "birth_year": "1990",
    "first_name": "Test",
    "last_name": "User",
    "company": "TestCorp",
    "address1": "123 Main St",
    "address2": "",
    "country": "United States",
    "state": "California",
    "city": "Los Angeles",
    "zipcode": "90001"
  }
}
```

**Response (email no encontrado)**:
```json
{
  "responseCode": 404,
  "message": "Account not found with this email, try another email!"
}
```

---

## PUT /api/updateAccount (API 13)

**Purpose**: Actualizar campos de una cuenta existente.

**Request**:
- Method: `PUT`
- Content-Type: `multipart/form-data`
- Parameters: mismos que POST createAccount (al menos `email` y `password`
  son obligatorios para identificar la cuenta; los demás campos se actualizan).

**Response (success)**:
```json
{
  "responseCode": 200,
  "message": "User updated!"
}
```

---

## DELETE /api/deleteAccount (API 12)

**Purpose**: Eliminar una cuenta de usuario.

**Request**:
- Method: `DELETE`
- Content-Type: `multipart/form-data`
- Parameters:

| Campo | Tipo | Requerido |
|---|---|---|
| email | String | Sí |
| password | String | Sí |

**Response (success)**:
```json
{
  "responseCode": 200,
  "message": "Account deleted!"
}
```

**Response (método no soportado — si se usa GET en vez de DELETE)**:
```json
{
  "responseCode": 405,
  "message": "This request method is not supported."
}
```

---

## Notas generales

- Todos los endpoints esperan **form-data**, no JSON body.
- Los `responseCode` dentro del JSON son códigos de la aplicación, no del HTTP
  transport (el HTTP status puede ser 200 incluso cuando el `responseCode`
  del JSON sea 400 o 405).
- Para validaciones, se debe verificar **tanto** el HTTP status como el
  `responseCode` del JSON body.
