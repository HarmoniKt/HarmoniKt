# HarmoniKt

## Running the Project

The project uses Docker Compose to run all services. The application is now configured with a reverse proxy that serves as the single entry point for all services.

### Starting the Application

```bash
docker compose up
```

### Accessing Services for Development

All services are accessible through the Nginx reverse proxy on port 80. Use the following URL paths:

| Service               | URL Path                                |
|-----------------------|-----------------------------------------|
| Consul UI and API     | http://localhost/consul/                |
| Robot Manager         | http://localhost/robot-manager/         |
| Group Mission Manager | http://localhost/group-mission-manager/ |
| Map Manager           | http://localhost/map-manager/           |
| Spot Service          | http://localhost/spot-service/          |
| MIR Service           | http://localhost/mir-service/           |

For more details about the Nginx reverse proxy configuration, see the [nginx-proxy/README.md](proxy/README.md).

## Microservices

### mir-service

| API Path    | Assigned        |
|-------------|-----------------|
| /get-robots | angela, manuel  |
| /goto       | nicolas, davide |

### spot-service

| API Path    | Assigned        |
|-------------|-----------------|
| /get-robots | nicolas, manuel |
| /goto       | angela, davide  |

### robot-service

| API Path                        | Assigned        |
|---------------------------------|-----------------|
| mir-binding / position-service  | nicolas, angela |
| mir-binding / action-service    | davide, manuel  |
| spot-binding / position-service | angela, manuel  |
| spot-binding / action-service   | nicolas, davide |

### service-registry

Assigned: nicolas

### public-api

Assigned: nicolas, manuel, davide, angela

# Architecture Diagram

<img src="resources/harmonikt-architecture.svg">