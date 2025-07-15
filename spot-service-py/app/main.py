from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

app = FastAPI(title="Spot Service", version="1.0.0")

@app.get('/robots')
async def get_robots():
    return {'msg': 'It works!'}