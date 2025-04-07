# Usar uma imagem base com o JDK (Java)
FROM openjdk:22

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pelo build para o container
COPY target/lista-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]