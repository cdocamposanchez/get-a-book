import json
from config.settings import USERS_FILE, BOOKS_FILE, ORDERS_FILE

def load_json(file_path):
    with open(file_path, "r", encoding="utf-8") as f:
        return json.load(f)

def load_users():
    return load_json(USERS_FILE)

def load_books():
    return load_json(BOOKS_FILE)

def load_orders():
    return load_json(ORDERS_FILE)
