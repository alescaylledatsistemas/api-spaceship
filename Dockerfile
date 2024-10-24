# Usar una imagen base de Java
FROM bitnami/java:21

WORKDIR /app

COPY target/tvmedia-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que utiliza la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
