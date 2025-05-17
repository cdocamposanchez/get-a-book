import os
from dotenv import load_dotenv

load_dotenv()

BASE_URL = os.getenv("BASE_URL", "http://localhost:8080")

DEFAULT_USER_EMAIL = os.getenv("DEFAULT_USER_EMAIL", "user@mail.com")
DEFAULT_USER_PASSWORD = os.getenv("DEFAULT_USER_PASSWORD", "123456")
DEFAULT_USER_ID = os.getenv("DEFAULT_USER_ID", "da7d0221-7f36-4bd5-93fd-e86211756347")
DEFAULT_BOOK_ID = os.getenv("DEFAULT_BOOK_ID", "065712ba-c07c-48ca-82b8-692273e470d1")
DEFAULT_ORDER_ID = os.getenv("DEFAULT_ORDER_ID", "2c5e76ac-0cb7-4641-9e63-d42cb47bb433")

DATA_PATH = os.path.join(os.path.dirname(__file__), "../data")
USERS_FILE = os.path.join(DATA_PATH, "users.json")
BOOKS_FILE = os.path.join(DATA_PATH, "books.json")
ORDERS_FILE = os.path.join(DATA_PATH, "orders.json")

COMMON_HEADERS = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}

REQUEST_TIMEOUT = 10
MIN_WAIT = 1000
MAX_WAIT = 3000
