# Sistema de Gestión Escolar - Backend (Microservicios)

Sistema distribuido desarrollado en Java con Spring Boot, Spring Cloud y persistencia H2.

## Arquitectura y Puertos

| Servicio | Puerto | Descripción |
| :--- | :--- | :--- |
| **eureka-server** | 8761 | Servidor de descubrimiento de servicios |
| **config-server** | 8888 | Servidor de configuración centralizada |
| **gateway-service** | 8080 | API Gateway de entrada y enrutamiento principal |
| **alumno-service** | 8081 | Microservicio de gestión de alumnos |
| **curso-service** | 8082 | Microservicio de gestión de cursos |
| **admin-service** | 8083 | Microservicio de administración y autenticación JWT |

## Documentación de API (Swagger / OpenAPI)
- Swagger Admin: http://localhost:8083/swagger-ui.html
- Swagger Alumnos: http://localhost:8081/swagger-ui.html
- Swagger Cursos: http://localhost:8082/swagger-ui.html

## Despliegue en Máquina Virtual (Linux / Ubuntu)

1. Clonar el repositorio en la Máquina Virtual:
   git clone https://github.com/48115220-sketch/sistema-gestion-escolar-backend.git
   cd proyecto-escuela

2. Levantar toda la arquitectura con Docker Compose:
   docker compose up --build

3. Verificar en el navegador de la VM:
   - Panel de Eureka: http://localhost:8761
   - Config Server: http://localhost:8888/alumno-service/default
   - Gateway API: http://localhost:8080
