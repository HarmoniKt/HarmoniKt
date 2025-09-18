import os
import socket
import requests
import logging
from fastapi import FastAPI

# Import handlers
from app.handlers.robot_handlers import setup_robot_handlers

logging.basicConfig(
    level=logging.INFO,
    format="%(levelname)s:%(message)s",
)

app = FastAPI(title="Spot Service", version="1.0.0")


def register_consul_service(
    consul_url: str | None = None,
    service_name: str = "spot-service",
    address: str | None = None,
    port: int = 8080,
    service_id: str | None = None,
    token: str | None = None,
):
    consul_url = consul_url or os.getenv("CONSUL_URL", "http://localhost:8500")
    address = address or socket.gethostname()
    service_id = service_id or f"{service_name}-{address}-{port}"

    payload = {
        "ID": service_id,
        "Name": service_name,
        "Address": address,
        "Port": port,
    }

    headers = {}
    if token:
        headers["X-Consul-Token"] = token

    resp = requests.put(
        f"{consul_url}/v1/agent/service/register",
        json=payload,
        headers=headers,
        timeout=5,
    )
    resp.raise_for_status()
    return resp


resp = register_consul_service()
logging.info("Registered to Consul: %s", resp.status_code)

if os.getenv("MOCKED") == "true":
    from app.repositories.spot_robot_repository import MockSpotRobotRepositoryImpl
    from app.services.spot_robot_service import FakeSpotRobotServiceImpl

    robot_repository = MockSpotRobotRepositoryImpl()
    robot_service = FakeSpotRobotServiceImpl(robot_repository)
else:
    from app.repositories.spot_robot_repository import SpotRobotRepositoryImpl
    from app.services.spot_robot_service import SpotRobotServiceImpl

    robot_repository = SpotRobotRepositoryImpl()
    robot_service = SpotRobotServiceImpl(robot_repository)


# Health check endpoint
@app.get("/")
async def health_check():
    return {"message": "Hello, world from Spot Service!"}


# Set up the handlers with the repositories
robot_router = setup_robot_handlers(robot_repository, robot_service)


# Include the routers in the FastAPI app
app.include_router(robot_router)
