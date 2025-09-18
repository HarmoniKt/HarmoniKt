FROM python:3.13
RUN pip install --upgrade pip && pip install uv
WORKDIR /app
COPY pyproject.toml uv.lock ./
RUN uv sync --locked
COPY app ./app
EXPOSE 8080
CMD ["uv", "run", "fastapi", "run", "app/main.py", "--port", "8080"]
