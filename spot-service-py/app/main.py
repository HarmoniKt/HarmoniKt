import os
import socket
import requests
from pydantic import BaseModel, Field
import logging
from fastapi import FastAPI, HTTPException

logging.basicConfig(
    level=logging.INFO,
    format="%(levelname)s:%(message)s",
)

app = FastAPI(title="Spot Service", version="1.0.0")

import os
import socket
import requests


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

@app.get('/robots')
async def get_robots():
    return {'msg': 'It works!'}