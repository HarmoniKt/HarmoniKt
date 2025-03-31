# HarmoniKt

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