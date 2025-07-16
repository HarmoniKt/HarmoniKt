import os
import socket
import requests
import logging
from fastapi import FastAPI

# Import models and repositories
from .repositories.spot_marker_repository import FakeSpotMarkerRepository
from .repositories.spot_robot_repository import FakeSpotRobotRepository

# Import handlers
from .handlers.robot_handlers import setup_robot_handlers
from .handlers.marker_handlers import setup_marker_handlers

logging.basicConfig(
    level=logging.INFO,
    format="%(levelname)s:%(message)s",
)

app = FastAPI(title="Spot Service", version="1.0.0")

def register_consul_service(
    consul_url: str | None = None,
    service_name: str = "spot-service-py",
    address: str | None = None,
    port: int = 8000,
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

# Initialize repositories
marker_repository = FakeSpotMarkerRepository()
robot_repository = FakeSpotRobotRepository()

# Health check endpoint
@app.get("/")
async def health_check():
    return {"message": "Hello, world from Spot Service!"}

# Set up the handlers with the repositories
robot_router = setup_robot_handlers(robot_repository)
marker_router = setup_marker_handlers(marker_repository)

# Include the routers in the FastAPI app
app.include_router(robot_router)
app.include_router(marker_router)
