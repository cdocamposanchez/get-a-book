from locust import TaskSet, task, between, User
from utils.auth import get_token
from utils.helpers import get_auth_headers
from utils.payloads import build_order_payload
from config.settings import DEFAULT_USER_ID, DEFAULT_BOOK_ID, DEFAULT_ORDER_ID

class OrderTasks(TaskSet):
    wait_time = between(1, 3)

    def __init__(self, parent: User):
        super().__init__(parent)
        self.books_created = []
        self.orders_created = []
        self.headers = None
        self.token = get_token()

    def on_start(self):
        self.headers = get_auth_headers(self.token)
        self.orders_created = []

    @task(2)
    def get_all_orders(self):
        params = {"page": 0, "size": 10}
        with self.client.get(
            "/orders",
            headers=self.headers,
            params=params,
            name="/orders?page=0&size=10",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ALL ORDERS] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def create_order(self):
        payload = build_order_payload(DEFAULT_USER_ID, DEFAULT_BOOK_ID)
        with self.client.post(
            "/orders/confirm-order",
            json=payload,
            headers=self.headers,
            name="/orders/confirm-order [POST]",
            catch_response=True
        ) as response:
            if response.status_code == 201:
                self.orders_created.append(response.json().get("data", {}).get("id"))
                response.success()
            else:
                response.failure(f"[CREATE ORDER] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_order_by_id(self):
        order_id = DEFAULT_ORDER_ID
        with self.client.get(
            f"/orders/{order_id}",
            headers=self.headers,
            name="/orders/{id} [GET]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ORDER BY ID] ID: {order_id}, Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def delete_order(self):
        if not self.orders_created:
            return
        order_id = self.orders_created.pop()
        with self.client.delete(
            f"/orders?orderId={order_id}",
            headers=self.headers,
            name="/orders?orderId={id} [DELETE]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[DELETE ORDER] ID: {order_id}, Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_orders_by_status(self):
        status = "PENDING"
        params = {"status": status, "page": 0, "size": 10}
        with self.client.get(
            "/orders/status",
            headers=self.headers,
            params=params,
            name=f"/orders/status?status={status} [GET]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ORDERS BY STATUS] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_orders_by_customer(self):
        customer_id = DEFAULT_USER_ID
        params = {"customerId": customer_id, "page": 0, "size": 10}
        with self.client.get(
            "/orders/customer",
            headers=self.headers,
            params=params,
            name="/orders/customer?customerId={id} [GET]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ORDERS BY CUSTOMER ID] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def get_orders_by_name(self):
        order_name = "Sample Order"
        params = {"orderName": order_name, "page": 0, "size": 10}
        with self.client.get(
            "/orders/name",
            headers=self.headers,
            params=params,
            name="/orders/name?orderName=Sample Order [GET]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[GET ORDERS BY NAME] Status: {response.status_code}, Body: {response.text}")

    @task(1)
    def update_order_status(self):
        if not self.orders_created:
            return
        order_id = self.orders_created[-1]
        params = {"orderId": order_id, "orderStatus": "SHIPPED"}
        with self.client.put(
            "/orders",
            headers=self.headers,
            params=params,
            name="/orders [PUT]",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"[UPDATE ORDER STATUS] ID: {order_id}, Status: {response.status_code}, Body: {response.text}")
