from locust import TaskSet, task, between, User
from utils.auth import get_token
from utils.helpers import get_auth_headers
from utils.payloads import build_user_payload, fake
from config.settings import DEFAULT_USER_ID
import uuid

class UserTasks(TaskSet):
    wait_time = between(1, 3)

    def __init__(self, parent: User):
        super().__init__(parent)
        self.created_users = []
        self.created_users_tokens = []
        self.token = get_token()
        self.headers = get_auth_headers(self.token)

    @task(2)
    def get_all_users(self):
        params = {"page": 0, "size": 10}
        with self.client.get(
            "/users",
            headers=self.headers,
            params=params,
            name="/users?page=0&size=10",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ALL USERS] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def register_user(self):
        payload = build_user_payload()
        with self.client.post(
            "/users/auth/register",
            json=payload,
            name="/users/auth/register",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                self.created_users.append(response.json().get("data", {}).get("userId"))
                self.created_users_tokens.append(response.json().get("data", {}).get("token"))
                response.success()
            else:
                response.failure(f"[REGISTER USER] Status: {response.status_code}, Body: {response.text}")


    @task(1)
    def update_user(self):
        if not self.created_users:
            return
        user_token = self.created_users_tokens[-1]
        payload = {
            "firstName": "UpdatedName",
            "lastNames": "UpdatedLastName",
            "email": fake.email(),
            "password": "UpdatedPassword123!",
            "role": "CLIENT",
            "favorites": []
        }
        with self.client.put(
            "/users",
            json=payload,
            headers={"Authorization": f"Bearer {user_token}"},
            name="/users [PUT]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[UPDATE USER] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def update_favorites(self):
        book_id = str(uuid.uuid4())
        with self.client.put(
            f"/users/update-favorite?bookId={book_id}",
            headers=self.headers,
            name="/users/update-favorite [PUT]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[UPDATE FAVORITES] Book ID: {book_id}, Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_user_by_id(self):
        user_id = DEFAULT_USER_ID
        with self.client.get(
            f"/users/{user_id}",
            headers=self.headers,
            name="/users/{id}",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET USER BY ID] ID: {user_id}, Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def delete_user(self):
        if not self.created_users:
            return
        user_id = self.created_users.pop()
        with self.client.delete(
            f"/users?userId={user_id}",
            headers=self.headers,
            name="/users [DELETE]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[DELETE USER] ID: {user_id}, Status: {response.status_code}, Body: {response.text}")
