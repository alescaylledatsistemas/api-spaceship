# api-spaceship
Prueba técnica Spring Boot

En este repo podemos encontrar la aplicación desarrolla en base a la prueba técnica presentada.

La aplicación ha sido dockerizada. La imagen está disponible en Docker Hub con la referencia "alldaudinot/api-spaceship:1".

El directorio functionality-test contiene una colección de postman mediante la cual podemos testear las distintas funcionalidades. La api ha sido securizada. Con la petición "create-token" podemos generar el JWT requerido para las restantes peticiones menos "kafka-broker" (creada solamente para probar el envio de mensajes a un tópico). Tenemos las credenciales admin/admin con roles ADMIN y USER y user/user con el rol de USER. 

En el archivo openapi.yaml podemos encontrar la definición del api.
