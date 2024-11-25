
FROM eclipse-temurin:21-jdk-jammy

# Define el directorio de trabajo
WORKDIR /app

# Copia el JAR construido en el directorio target a la imagen
COPY target/admin-service-0.0.1-SNAPSHOT.jar /app/admin-service.jar

# Expone el puerto en el que la aplicación escuchará
EXPOSE 8086

# Comando para ejecutar el JAR cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "admin-service.jar"]
