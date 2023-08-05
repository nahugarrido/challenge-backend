# Establecemos la imagen base de Java
FROM openjdk:17-alpine

# Establecemos el directorio de trabajo en /app
WORKDIR /app

# Copiamos el JAR de la aplicación a la imagen
COPY target/challenge-0.0.1-SNAPSHOT.jar app.jar

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]