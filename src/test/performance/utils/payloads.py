import random
from io import BytesIO

from PIL import Image
from faker import Faker

from config.settings import DEFAULT_USER_EMAIL, DEFAULT_USER_PASSWORD

fake = Faker()

def build_user_payload():
    return {
        "firstName": fake.first_name(),
        "lastName": fake.last_name(),
        "email": fake.email(),
        "password": "Password123!"
    }

def build_login_payload():
    return {
        "email": DEFAULT_USER_EMAIL,
        "password": DEFAULT_USER_PASSWORD
    }

def build_book_payload():
    data = {
        "title": fake.sentence(nb_words=3),
        "publisher": fake.company(),
        "description": fake.text(),
        "year": str(random.randint(1990, 2024)),
        "quantity": str(random.randint(1, 100)),
        "price": str(round(random.uniform(10.0, 150.0), 2)),
        "qualification": str(round(random.uniform(1.0, 5.0), 1)),
        "categories": "TestBook",
    }

    image_stream = BytesIO()
    image = Image.new("RGB", (100, 100), color=(255, 0, 0))
    image.save(image_stream, format='JPEG')
    image_stream.seek(0)
    files = {
        "image": ("test.jpg", image_stream, "image/jpeg")
    }

    return data, files

def build_order_payload(customer_id, book_id):
    return {
        "customerId": customer_id,
        "orderName": "Sample Order",
        "shippingAddress": {
            "firstName": fake.first_name(),
            "lastName": fake.last_name(),
            "emailAddress": fake.email(),
            "addressLine": fake.street_address(),
            "country": fake.country(),
            "city": fake.city(),
            "zipCode": fake.zipcode()
        },
        "billingAddress": {
            "firstName": fake.first_name(),
            "lastName": fake.last_name(),
            "emailAddress": fake.email(),
            "addressLine": fake.street_address(),
            "country": fake.country(),
            "city": fake.city(),
            "zipCode": fake.zipcode()
        },
        "orderStatus": "PENDING",
        "orderItems": [
            {
                "bookId": book_id,
                "quantity": random.randint(1, 3)
            }
        ]
    }
