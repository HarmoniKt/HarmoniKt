FROM python:3.13-slim
RUN pip install --upgrade pip && pip install uv
WORKDIR /app
COPY pyproject.toml uv.lock ./
RUN uv pip install --prod --system
COPY app ./app
EXPOSE 8000
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]