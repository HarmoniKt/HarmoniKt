from pydantic import BaseModel


class SpotRobotCreationRequest(BaseModel):
    """
    Represents a request to create a new Spot robot.
    This model is used for validating the request body when creating a new Spot robot.
    """

    username: str
    password: str
    address: str
    canonical_name: str
