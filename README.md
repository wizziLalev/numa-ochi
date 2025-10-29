# Numa-Ochi E-book Library

> **Note:** This application is currently under active development and is not yet ready for production use. Features may be incomplete or unstable.

![Numa-Ochi](https://img.shields.io/badge/version-1.0.0-blue.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen.svg) ![React](https://img.shields.io/badge/React-18.3.1-blue.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)

Numa-Ochi is a modern, full-stack application for managing a personal library of e-books. It provides a clean, web-based interface to organize, search, and access your collection of digital series, chapters, and volumes.

## Features

- **Series Management**: Organize your e-books by series. Each series contains metadata such as title, author, description, cover image, publisher, and ISBN.
- **Chapter and Volume Organization**: Each series can be broken down into volumes, which in turn contain individual chapters (e.g., PDF, EPUB, CBR files).
- **Collection Grouping**: Group related series into custom collections for better organization.
- **User Authentication**: Secure user registration and login system.
- **Powerful Search**: Fast, full-text search across all series metadata, powered by Meilisearch.
- **RESTful & WebSocket API**: A robust backend API for all CRUD operations and real-time communication.

## Tech Stack

### Backend
- **Java 21**
- **Spring Boot 3.3.1**
- **Spring Modulith**: For modular application structure.
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA / Hibernate**: For database interaction.
- **PostgreSQL**: As the primary relational database.
- **Redis**: For distributed caching and session management.
- **Liquibase**: For database schema migrations.
- **Gradle (Kotlin DSL)**: As the build tool.

### Frontend
- **React 18**
- **TypeScript**
- **React Router**: For client-side routing.
- **ShadCn/ui & Tailwind CSS**: For a modern, responsive, and accessible UI.
- **Axios**: For making HTTP requests to the backend.

### Search & Infrastructure
- **Meilisearch**: For fast and relevant full-text search.
- **Docker & Docker Compose**: For containerizing the application and all its services for easy local development.

## Local Development Setup

There are two ways to run the application locally: using Docker Compose for the full stack, or running the services in Docker and the applications on your host machine for easier debugging.

### Prerequisites

- **Docker & Docker Compose**: [Install Docker](https://docs.docker.com/engine/install/) and [Docker Compose](https://docs.docker.com/compose/install/).
- **Java Development Kit (JDK) 21**: Required if you plan to run the backend locally.
- **Node.js and Yarn**: Required for frontend development.

### Option 1: Full Stack with Docker Compose (Recommended)

This is the easiest way to get the entire application stack up and running.

1.  **Generate Frontend Lock File**:
    Before building, you must generate the `yarn.lock` file for the frontend. This is crucial for reproducible builds.
    ```bash
    cd frontend
    yarn install
    cd ..
    ```

2.  **Build and Start Services**:
    From the project root, run the following command. This will build the backend and frontend images and start all services.
    ```bash
    # For Linux/macOS
    UID=$(id -u) GID=$(id -g) docker-compose up --build

    # For Windows (in Git Bash or similar)
    # export UID=$(id -u)
    # export GID=$(id -g)
    # docker-compose up --build
    ```

3.  **Access the Application**:
    - **Frontend**: `http://localhost:3000`
    - **Backend API**: `http://localhost:8080`
    - **Meilisearch UI**: `http://localhost:7700` (Master Key: `aSuperSecretMasterKey`)

### Option 2: Hybrid Setup for Debugging

This approach runs the database, cache, and search engine in Docker, while you run the backend and frontend on your host machine (e.g., from your IDE or terminal).

1.  **Start Supporting Services**:
    From the project root, start only the infrastructure services.
    ```bash
    docker-compose up -d postgres redis meilisearch
    ```

2.  **Run the Backend Locally**:
    - Navigate to the `backend` directory: `cd backend`
    - Run the application using the Gradle wrapper:
      ```bash
      ./gradlew bootRun
      ```
    - Alternatively, open the `backend` project in your IDE (like IntelliJ IDEA) and run the `NumaOchiApplication` main class.

3.  **Run the Frontend Locally**:
    - Navigate to the `frontend` directory: `cd frontend`
    - Install dependencies: `yarn install`
    - Create a `.env` file in the `frontend` directory with the following content to point to your local backend:
      ```
      REACT_APP_API_BASE_URL=http://localhost:8080
      ```
    - Start the development server:
      ```bash
      yarn start
      ```

### Stopping the Application

-   To stop the full Docker stack, run `docker-compose down` from the project root.
-   To stop only the supporting services (if using the hybrid setup), run `docker-compose down postgres redis meilisearch`.
-   To stop locally running applications, press `Ctrl+C` in their respective terminals.

## License

This project is licensed under the [GNU GPLv3 License](LICENSE).
