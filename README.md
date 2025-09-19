ConvoCLI Server

Build:
  mvn clean package

Run locally:
  export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/convocli
  export SPRING_DATASOURCE_USERNAME=postgres
  export SPRING_DATASOURCE_PASSWORD=postgres
  export JWT_SECRET=change_me
  mvn spring-boot:run
