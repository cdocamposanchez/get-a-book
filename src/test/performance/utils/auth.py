import time

import requests
from config.settings import BASE_URL, DEFAULT_USER_EMAIL, DEFAULT_USER_PASSWORD, COMMON_HEADERS


def get_token(retries=5, delay=2):
    url = f"{BASE_URL}/users/auth/login"
    payload = {
        "email": DEFAULT_USER_EMAIL,
        "password": DEFAULT_USER_PASSWORD
    }

    for attempt in range(retries):
        try:
            response = requests.post(url, json=payload, headers=COMMON_HEADERS)
            response.raise_for_status()
            return response.json()["data"]["token"]
        except requests.exceptions.HTTPError as e:
            print(f"[ERROR] Login failed (attempt {attempt + 1}/{retries}): {e}")
            if attempt < retries - 1:
                time.sleep(delay * (attempt + 1))
            else:
                raise
