# SaberPRO – Banco de Preguntas (Taller 13: DDD + Hexagonal + Docker)

## Microservicios y puertos
| Servicio | Puerto | URL base |
|---|---|---|
| validation-service | 8082 | http://localhost:8082 |
| question-service | 8083 | http://localhost:8083 |
| lifecycle-service | 8084 | http://localhost:8084 |
| RabbitMQ (AMQP) | 5672 | – |
| RabbitMQ Dashboard | 15672 | http://localhost:15672 (guest / guest) |

## Ejecutar
```bash
docker compose up -d --build
docker ps
docker logs -f saberpro-lifecycle-service
```
Detener: `docker compose down`

## Endpoints (question-service)
- `GET  /api/preguntas` → 200
- `GET  /api/preguntas/{id}` → 200 / 404
- `POST /api/preguntas` → 201 (o 400 si viola reglas)
- `POST /api/preguntas/{id}/enviar-revision` → 202 (publica en RabbitMQ)

## Intercambiar adaptadores (Hexagonal)
En `question-service/src/main/resources/application.properties`:
`saberpro.persistence=jpa` (H2) o `saberpro.persistence=memory` (HashMap).
El dominio y la capa de aplicación no cambian; solo se elige el `@Bean` en `infrastructure/config/BeanConfig.java`.
