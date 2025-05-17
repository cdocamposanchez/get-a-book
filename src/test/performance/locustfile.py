from locust import HttpUser, between

from config.settings import BASE_URL
from tasks.book_tasks import BookTasks
from tasks.order_tasks import OrderTasks
from tasks.user_tasks import UserTasks

import logging

logging.basicConfig(level=logging.DEBUG)

class BookUser(HttpUser):
    tasks = [BookTasks]
    wait_time = between(1, 3)
    host = BASE_URL


class OrderUser(HttpUser):
    tasks = [OrderTasks]
    wait_time = between(1, 3)
    host = BASE_URL


class UserUser(HttpUser):
    tasks = [UserTasks]
    wait_time = between(1, 3)
    host = BASE_URL
