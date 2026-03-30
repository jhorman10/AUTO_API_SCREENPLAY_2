# Data Model: API CRUD — Cuenta de Usuario

**Date**: 2026-03-30
**Feature**: 001-api-crud-screenplay

## Entities

### UserAccount

Representa una cuenta de usuario registrada en AutomationExercise.

| Campo | Tipo | Requerido | Descripción |
|---|---|---|---|
| name | String | Sí | Nombre completo (usado en creación) |
| email | String | Sí | Email único del usuario (identifica la cuenta) |
| password | String | Sí | Contraseña (usada en creación, update y delete) |
| title | String | Sí | Título (Mr, Mrs, Miss) |
| birth_date | String | Sí | Día de nacimiento |
| birth_month | String | Sí | Mes de nacimiento |
| birth_year | String | Sí | Año de nacimiento |
| firstname | String | Sí | Nombre |
| lastname | String | Sí | Apellido |
| company | String | No | Empresa |
| address1 | String | Sí | Dirección principal |
| address2 | String | No | Dirección secundaria |
| country | String | Sí | País |
| zipcode | String | Sí | Código postal |
| state | String | Sí | Estado/Provincia |
| city | String | Sí | Ciudad |
| phone_number | String | Sí | Teléfono |

### Notas

- El `email` es el identificador natural de la cuenta; se usa como clave en
  GET, PUT y DELETE.
- La contraseña se envía como form field (no cifrada en tránsito salvo HTTPS).
- No existe un campo `id` explícito en la creación; el `id` puede aparecer en
  la respuesta GET.

## Estado del ciclo de vida

```
[No existe] --POST createAccount--> [Activa]
[Activa] --GET getUserDetail--> [Activa] (sin cambio)
[Activa] --PUT updateAccount--> [Activa] (campos actualizados)
[Activa] --DELETE deleteAccount--> [Eliminada]
[Eliminada] --GET getUserDetail--> Error (cuenta no encontrada)
```

## Validaciones de entrada (inferidas)

- `email` debe ser formato válido de correo.
- `password` debe tener al menos 1 carácter (no se documenta longitud mínima).
- `title` debe ser uno de: `Mr`, `Mrs`, `Miss`.
- `country` debe ser un valor aceptado por la API (ej: `India`, `United States`,
  `Canada`, `Australia`, etc.).
