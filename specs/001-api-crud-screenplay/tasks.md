# Tasks: API CRUD — Cuenta de Usuario

**Input**: Design documents from `/specs/001-api-crud-screenplay/`
**Prerequisites**: plan.md (required), spec.md (required), research.md, data-model.md, contracts/api-contract.md

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and Gradle/Serenity configuration

- [ ] T001 Create `build.gradle` with Serenity BDD 4.x, Serenity Rest, Serenity Screenplay, Cucumber 7.x, JUnit 5 dependencies
- [ ] T002 Create `settings.gradle` with project name `AUTO_API_SCREENPLAY`
- [ ] T003 [P] Create `src/test/resources/serenity.conf` with base URL and REST configuration
- [ ] T004 [P] Create `src/test/resources/logback-test.xml` with Serenity-compatible logging
- [ ] T005 [P] Create `serenity.properties` at project root

**Checkpoint**: `./gradlew dependencies` resolves without errors

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Data model, utilities, and Gherkin feature — required before any story implementation

- [ ] T006 [P] Create `src/test/java/com/automationexercise/models/UserData.java` — POJO builder for account payload (all fields from data-model.md)
- [ ] T007 [P] Create `src/test/java/com/automationexercise/utils/TestDataGenerator.java` — generates unique email with timestamp and default valid payload
- [ ] T008 Create `src/test/resources/features/api/user_account_crud.feature` — Gherkin with 4 scenarios (@api-post, @api-get, @api-put, @api-delete) plus @api-crud umbrella tag
- [ ] T009 Create `src/test/java/com/automationexercise/runners/CrudApiTestRunner.java` — Cucumber runner with Serenity, glue path, feature path, tags

**Checkpoint**: `./gradlew compileTestJava` succeeds; feature file is parseable

---

## Phase 3: User Story 1 — Crear cuenta (POST) (Priority: P1)

**Goal**: Registrar una cuenta de usuario vía POST y verificar respuesta 201/200

### Verification for User Story 1

- [ ] T010 [US1] Verify POST `/api/createAccount` returns responseCode 201 and message "User created!"

### Implementation for User Story 1

- [ ] T011 [US1] Create `src/test/java/com/automationexercise/tasks/CreateAccount.java` — Task que ejecuta POST con form-data
- [ ] T012 [US1] Create `src/test/java/com/automationexercise/questions/ResponseCode.java` — Question que extrae responseCode del JSON body
- [ ] T013 [US1] Create `src/test/java/com/automationexercise/questions/ResponseMessage.java` — Question que extrae message del JSON body
- [ ] T014 [US1] Add step definitions for POST scenario in `src/test/java/com/automationexercise/stepdefinitions/ApiCrudStepDefinitions.java`

**Checkpoint**: `./gradlew test -Dcucumber.filter.tags="@api-post"` passes

---

## Phase 4: User Story 2 — Consultar cuenta (GET) (Priority: P2)

**Goal**: Consultar detalles de la cuenta creada por email

### Verification for User Story 2

- [ ] T015 [US2] Verify GET `/api/getUserDetailByEmail` returns responseCode 200 and contains email

### Implementation for User Story 2

- [ ] T016 [US2] Create `src/test/java/com/automationexercise/tasks/GetUserDetail.java` — Task que ejecuta GET con query param email
- [ ] T017 [US2] Add step definitions for GET scenario in ApiCrudStepDefinitions.java

**Checkpoint**: `./gradlew test -Dcucumber.filter.tags="@api-get"` passes

---

## Phase 5: User Story 3 — Actualizar cuenta (PUT) (Priority: P3)

**Goal**: Actualizar campos de la cuenta y verificar éxito

### Verification for User Story 3

- [ ] T018 [US3] Verify PUT `/api/updateAccount` returns responseCode 200 and message "User updated!"

### Implementation for User Story 3

- [ ] T019 [US3] Create `src/test/java/com/automationexercise/tasks/UpdateAccount.java` — Task que ejecuta PUT con form-data
- [ ] T020 [US3] Add step definitions for PUT scenario in ApiCrudStepDefinitions.java

**Checkpoint**: `./gradlew test -Dcucumber.filter.tags="@api-put"` passes

---

## Phase 6: User Story 4 — Eliminar cuenta (DELETE) (Priority: P3)

**Goal**: Eliminar la cuenta y verificar que ya no existe

### Verification for User Story 4

- [ ] T021 [US4] Verify DELETE `/api/deleteAccount` returns responseCode 200 and message "Account deleted!"

### Implementation for User Story 4

- [ ] T022 [US4] Create `src/test/java/com/automationexercise/tasks/DeleteAccount.java` — Task que ejecuta DELETE con form-data (email + password)
- [ ] T023 [US4] Add step definitions for DELETE scenario in ApiCrudStepDefinitions.java

**Checkpoint**: `./gradlew test -Dcucumber.filter.tags="@api-delete"` passes

---

## Phase 7: Polish & Cross-Cutting

- [ ] T024 [P] Run full suite `./gradlew clean test` and verify all 4 scenarios pass
- [ ] T025 [P] Verify Serenity report generates at `build/reports/serenity/index.html`
- [ ] T026 Update README.md with final execution instructions

---

## Dependencies & Execution Order

- **Phase 1** → **Phase 2** → **Phase 3** → **Phase 4** → **Phase 5** → **Phase 6** → **Phase 7**
- Stories 3-6 depend on Phase 2 (models, utils, feature file, runner)
- Story 2 (GET) depends on Story 1 (POST) having created the account
- Story 3 (PUT) depends on the account existing
- Story 4 (DELETE) runs last to clean up

## Implementation Strategy

### MVP First (POST only)
1. Complete Phase 1 + Phase 2
2. Complete Phase 3 (POST)
3. Validate independently

### Full CRUD
1. Add GET → PUT → DELETE sequentially
2. Each verifies independently by tag
3. Full suite runs the complete flow: POST → GET → PUT → DELETE
