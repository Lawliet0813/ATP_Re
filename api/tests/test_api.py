import pytest
from fastapi.testclient import TestClient
from api.main import app

client = TestClient(app)


def test_read_root():
    """Test root endpoint"""
    response = client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert data["name"] == "ATP_Re API"
    assert data["status"] == "running"


def test_health_check():
    """Test health check endpoint"""
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "healthy"}


def test_api_docs():
    """Test API documentation is available"""
    response = client.get("/docs")
    assert response.status_code == 200


# Note: The following tests would require a database connection
# They are commented out to avoid errors in the test environment

# def test_create_task():
#     """Test creating a new task"""
#     task_data = {
#         "task_name": "Test Task",
#         "task_type": "decode",
#         "description": "Test task description"
#     }
#     response = client.post("/api/v1/tasks/", json=task_data)
#     assert response.status_code == 201
#     data = response.json()
#     assert data["task_name"] == task_data["task_name"]
#     assert data["task_type"] == task_data["task_type"]
#     assert data["status"] == "pending"


# def test_get_tasks():
#     """Test retrieving tasks"""
#     response = client.get("/api/v1/tasks/")
#     assert response.status_code == 200
#     assert isinstance(response.json(), list)


# def test_file_upload():
#     """Test file upload"""
#     test_file = ("test.dat", b"test content", "application/octet-stream")
#     response = client.post(
#         "/api/v1/upload/",
#         files={"file": test_file}
#     )
#     assert response.status_code == 201
