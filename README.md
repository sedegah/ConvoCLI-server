# ConvoCLI – Server

A lightweight, RESTful backend that powers the ConvoCLI ecosystem.
This server provides secure API endpoints for real-time messaging, conversation management, and user authentication, enabling seamless communication with the ConvoCLI Client.

---

## Features

* **REST API** – Exposes clear, well-documented endpoints for sending and receiving messages.
* **Stateless Design** – Scalable architecture using JWT-based authentication.
* **Configurable** – Environment-based settings for database, security, and logging.
* **Cross-Platform Deployment** – Runs on Linux, macOS, or Windows.

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/sedegah/ConvoCLI-server.git
cd ConvoCLI-server
```

### 2. Create and Activate a Virtual Environment (Python 3.9+)

```bash
python3 -m venv .venv
source .venv/bin/activate   # Windows: .venv\Scripts\activate
pip install -r requirements.txt
```

### 3. Configure Environment Variables

Create a `.env` file in the project root with the following:

```
SECRET_KEY=your_secret_key
DATABASE_URL=sqlite:///data.db    # or your preferred DB
HOST=0.0.0.0
PORT=8000
```

---

## Usage

Start the development server:

```bash
uvicorn app.main:app --reload
```

Production example (using Gunicorn + Uvicorn workers):

```bash
gunicorn -k uvicorn.workers.UvicornWorker app.main:app
```

The server will be available at:
`http://localhost:8000`

---

## API Overview

* `POST /login` – Authenticate and obtain a JWT token.
* `POST /messages` – Send a message to a conversation.
* `GET /conversations` – Retrieve a list of conversations.
* `GET /messages/{id}` – Fetch messages from a specific conversation.

*Detailed API documentation is available via the built-in OpenAPI/Swagger UI at:*
`http://localhost:8000/docs`

---

## Project Structure

```
ConvoCLI-server/
├─ app/
│  ├─ main.py         # Application entry point
│  ├─ models.py       # Database models
│  ├─ routes/         # API route definitions
│  ├─ services/       # Business logic
│  └─ utils.py        # Utility functions
├─ requirements.txt
└─ README.md
```

---

## Author

**Kimathi Elikplim Sedegah**
GitHub: [@sedegah](https://github.com/sedegah)

---
