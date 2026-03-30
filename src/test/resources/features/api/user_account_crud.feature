@api-crud
Feature: Ciclo CRUD de cuenta de usuario via API
  Como automatizador quiero validar el ciclo completo de vida de una cuenta
  de usuario usando los endpoints REST de AutomationExercise para verificar
  la integridad de los servicios de creacion, consulta, actualizacion y eliminacion.

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
