# Flight Manager

# Online Flight Management System

This is created as an assignment for a Software Engineering university course.

It is designed for managing flights online. The system allows users to view and book flights, while administrators can manage flight data and view reservations.

The application consists of:

- A **Java (Spring Boot)** backend with **Maven** for building and managing the project.
- A **React** frontend served as static files by the Spring Boot backend.
- An **SMTP relay** to send email notifications to users upon successful flight reservations.

## Features

- **User Features**:

  - View available flights.
  - Book flights and make reservations.
  - Receive email notifications upon successful reservation.

- **Administrator Features**:
  - Manage flight details (add, edit, delete flights).
  - View all user reservations.

## Tech Stack

- **Backend**: Java (Spring Boot)
- **Frontend**: React
- **Database**: H2 Database
- **Email**: SMTP relay (for sending reservation notifications)
- **Build Tool**: Maven (with Maven Wrapper)
- **Containerization**: Docker

## Setup and Usage

### Prerequisites

Before getting started, ensure that the following tools are installed:

- **Java 17+** (JDK) for backend development.
- **Node.js and npm** for building the frontend (React).
- **Docker** (optional, for containerized setup).

### Running Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/georgi-marchev/flight-manager.git
   cd flight-manager
   ```

2. **Backend Setup** (Java + Maven)

- Navigate to the `backend` directory.
- You can use the **Maven Wrapper** to build the project:

  ```bash
  ./mvnw clean install
  ```

- Alternatively, if Maven is installed globally, run:

  ```bash
  mvn clean install
  ```

- This command will:
  - **Build the React frontend** using the Maven frontend-maven-plugin plugin configured in the `pom.xml` file.
  - **Package the backend** into a `.jar` file, which includes the React build files in the `static` directory.

3. **Frontend Setup** (optional): If you want to build the frontend separately before running the backend:

- Navigate to the `frontend` directory.
- Install the required npm dependencies:

  ```bash
  npm install
  ```

- Build the React app:

  ```bash
  npm run build
  ```

4. **Docker Setup** (optional):

   - The project includes a **Dockerfile** that handles both building the frontend and packaging the backend into a Docker container. To build and run the app using Docker, simply execute the following commands:

     ```bash
     docker build -t flight-management-app .
     docker run -p 8080:8080 flight-management-app
     ```

   - Once the container is running, the application will be accessible at `http://localhost:8080` (as long as Spring is configured to run on port 8080 in [application.yaml](./backend/src/main/resources/application.yaml)).

### Configuration

1. **Application Configuration**:

   - In order to run the application, you need to configure the [application.yaml](./backend/src/main/resources/application.yaml). This file contains settings for your database, email service (SMTP relay), and other application-specific settings.

2. **SMTP Configuration**:
   - Fill in the SMTP settings in [application.yaml](./backend/src/main/resources/application.yaml) to enable email notifications upon successful flight reservations.

### Build Process with Maven

- The build process is handled using **Maven**, which includes the following steps:
  - **Building the React frontend**: The frontend is built using the **Maven plugin** for **npm** (`frontend-maven-plugin`). This step is automatically triggered during the `mvn clean install` or `mvn package` command.
  - **Packaging the backend**: The backend is packaged into a **JAR file** that includes the built React files in the `static` folder.
  - The frontend React build files are placed in the `resources/static` directory of the Spring Boot application, allowing the backend to serve them as static content.

### Deploying to Production

To deploy the application, you can either:

1. **Use Docker**:

   - Build and run the application as a Docker container using the provided Dockerfile.
   - Make sure to configure the [application.yaml](./backend/src/main/resources/application.yaml) file for production (e.g., database, SMTP server, etc.).

2. **Traditional Deployment**:
   - Build the Spring Boot application with Maven:
     ```bash
     mvn clean package
     ```
   - Deploy the generated `.jar` file on your server, and ensure that the [application.yaml](./backend/src/main/resources/application.yaml) file is properly configured.

## Documentation

### API Documentation

The API documentation is located in the [API README.md](./docs/api/README.md) file. It provides detailed information about the available REST endpoints, request/response formats, and authentication details.

### Diagrams

The following diagrams are included in the project to visualize the system architecture and database structure:

[Software Architecture Diagram](./docs/diagrams/software_architecture_diagram.svg): it outlines the overall architecture of the application, including interactions between the frontend, backend, and external services.
[Database ER Diagram](./docs/diagrams/software_architecture_diagram.svg): Located in docs/diagrams/database_er_diagram.svg, this diagram illustrates the entity relationships for the database.
