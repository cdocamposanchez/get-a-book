from locust import TaskSet, task, between, User
from utils.auth import get_token
from utils.helpers import get_auth_headers
from utils.payloads import build_book_payload
from config.settings import DEFAULT_BOOK_ID

class BookTasks(TaskSet):
    wait_time = between(1, 3)

    def __init__(self, parent: User):
        super().__init__(parent)
        self.books_created = None
        self.token = get_token()
        self.headers = None

    def on_start(self):
        self.headers = get_auth_headers(self.token)
        self.books_created = []

    @task(2)
    def get_all_books(self):
        params = {"page": 0, "size": 10}
        with self.client.get("/books/list", headers=self.headers, params=params, name="/books/list?page=0&size=10", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[GET ALL BOOKS] Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def create_book(self):
        data, files = build_book_payload()

        with self.client.post("/books", data=data,
        files=files, headers={"Authorization": f"Bearer {self.token}"}, name="/books [POST]", catch_response=True) as response:
            if response.status_code == 201:
                book_id = response.json().get("data", {}).get("id")
                if book_id:
                    self.books_created.append(book_id)
                response.success()
            else:
                response.failure(f"[CREATE BOOK] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_book_by_id(self):
        book_id = DEFAULT_BOOK_ID
        with self.client.get(f"/books/{book_id}", headers=self.headers, name="/books/{id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[GET BOOK BY ID] ID: {book_id}, Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def search_by_title_regex(self):
        query = "a"
        with self.client.get(f"/books/search?titleRegex={query}&page=0&size=10", headers=self.headers, name="/books/search?titleRegex={regex}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[SEARCH TITLE REGEX] Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def filter_by_category(self):
        category = "TestBook"
        with self.client.get(f"/books/category?category={category}&page=0&size=10", headers=self.headers, name="/books/category?category={category}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[FILTER BY CATEGORY] Category: {category}, Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def filter_by_year(self):
        year = 2023
        with self.client.get(f"/books/year?year={year}&page=0&size=10", headers=self.headers, name="/books/year?year={year}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[FILTER BY YEAR] Year: {year}, Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def filter_by_publisher(self):
        publisher = "Penguin"
        with self.client.get(f"/books/publisher?publisher={publisher}&page=0&size=10", headers=self.headers, name="/books/publisher?publisher={publisher}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"[FILTER BY PUBLISHER] Publisher: {publisher}, Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()

    @task(1)
    def delete_book(self):
        if not self.books_created:
            return
        book_id = self.books_created.pop()
        with self.client.delete(f"/books?bookId={book_id}", headers=self.headers, name="/books?bookId={id} [DELETE]", catch_response=True) as response:
            if response.status_code != 201:
                response.failure(f"[DELETE BOOK] ID: {book_id}, Status: {response.status_code}, Body: {response.text}")
            else:
                response.success()
