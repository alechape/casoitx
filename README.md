# Caso ITX - Sistema de Gestión de Precios

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen.svg)](https://spring.io/projects/spring-boot)

## Descripción del Proyecto

**Caso ITX** es una aplicación desarrollada con Spring Boot diseñada para gestionar y consultar los precios aplicables a productos de una cadena de comercio electrónico. La aplicación permite determinar el precio final que debe aplicarse a un producto específico de una marca en una fecha determinada, considerando prioridades y rangos de fechas.

Este proyecto ha sido construido siguiendo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)** para garantizar un desacoplamiento efectivo entre la lógica de negocio y las tecnologías externas (bases de datos, APIs REST, etc.).

## Requisitos Previos

Para ejecutar este proyecto localmente, asegúrate de tener instalado:

- **Java JDK 21** o superior.
- **Maven 3.8+** (opcional, se incluye `mvnw`).
- Un IDE compatible (IntelliJ IDEA, Eclipse, VS Code).

## Arquitectura y Tecnologías

### Arquitectura Hexagonal
El proyecto está organizado en tres capas principales:
- **Domain:** Modelos de negocio, excepciones y puertos (interfaces). No tiene dependencias externas.
- **Application:** Implementación de los casos de uso del sistema.
- **Infrastructure:** Adaptadores de entrada (REST Controllers) y salida (JPA Repositories, configuración de base de datos).

### Stack Tecnológico
- **Spring Boot 4.0.6**: Framework principal.
- **Spring Data JPA**: Para la persistencia de datos.
- **H2 Database**: Base de datos en memoria para desarrollo y pruebas.
- **Flyway**: Gestión de migraciones de base de datos.
- **Lombok**: Para reducir el código boilerplate.
- **MapStruct/Manual Mapping**: Para la transformación entre DTOs, Modelos y Entidades.
- **OpenAPI / Swagger**: Definición y documentación de la API.
- **JUnit 5 & Mockito**: Para pruebas unitarias e integración.

## Configuración Local

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/alechape/casoitx.git
    cd casoitx
    ```

2.  **Construir el proyecto:**
    Utiliza el Maven Wrapper incluido para compilar y descargar las dependencias:
    ```bash
    ./mvnw clean install
    ```

## Ejecución de la Aplicación

Existen varias formas de ejecutar la aplicación:

### Desde la línea de comandos:
```bash
./mvnw spring-boot:run
```

### Ejecutando el JAR generado:
```bash
java -jar target/casoitx-1.0.0.jar
```

La aplicación estará disponible en `http://localhost:8080`.

## Pruebas

Para ejecutar el conjunto completo de pruebas unitarias e integración:
```bash
./mvnw test
```

## API Endpoints

La API principal está expuesta bajo el prefijo `/api/v1`.

### Consultar Precio Aplicable
Busca el precio que debe aplicarse según los criterios de fecha, producto y marca.

- **URL:** `/api/v1/prices`
- **Método:** `GET`
- **Parámetros de consulta:**
    - `applicationDate` (string, format: date-time): Fecha en la que se desea consultar el precio (Ej: `2020-06-14T10:00:00`).
    - `productId` (long): Identificador del producto.
    - `brandId` (long): Identificador de la cadena (ZARA = 1).

- **Ejemplo de petición:**
  ```http
  GET /api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
  ```

- **Respuesta Exitosa (200 OK):**
  ```json
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00",
    "price": 25.45,
    "curr": "EUR"
  }
  ```

## Colección de Postman

En la raíz del proyecto se encuentra el archivo `casos_prueba_itx.postman_collection.json`. Este archivo contiene una colección de Postman con los casos de prueba listos para ser importados y ejecutados, permitiendo validar los diferentes escenarios de precios de forma automatizada.

---
Desarrollado por [Alexander Echavarría Perez](mailto:alex.echavarria.perez@gmail.com).
