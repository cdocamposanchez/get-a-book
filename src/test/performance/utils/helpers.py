import uuid
from config.settings import COMMON_HEADERS

def generate_uuid():
    return str(uuid.uuid4())

def get_auth_headers(token: str):
    return {
        **COMMON_HEADERS,
        "Authorization": f"Bearer {token}"
    }
