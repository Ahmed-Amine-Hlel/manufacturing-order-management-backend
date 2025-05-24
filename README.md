# Manufacturing Order Management Backend

A robust Spring Boot backend for managing manufacturing orders, machines, products, and employees. This system is designed for manufacturing environments to streamline order processing, resource allocation, and KPI tracking.

---

## Features

- **Manufacturing Orders**: Create, update, and track manufacturing orders with status management.
- **Machines**: Manage machine inventory, status, and maintenance records.
- **Products**: Track product types, stock levels, and suppliers.
- **Employees**: Assign employees to machines and manage their roles.
- **KPIs**: Retrieve key performance indicators such as order status counts, machine status, product stock alerts, and employee workload.
- **RESTful API**: Well-structured endpoints for all resources.
- **Validation**: Input validation using Jakarta Validation.
- **CORS Support**: Configured for cross-origin requests.
- **PostgreSQL Integration**: Uses PostgreSQL for persistent storage.
- **Auditing**: Automatic tracking of creation and modification timestamps.
- **Dockerized Deployment**: Easily run the backend and database using Docker and Docker Compose.

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Lombok**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## Project Structure

```
├── src/main/java/com/manufacturing/backend
│   ├── common/         # DTOs and Enums
│   ├── config/         # Configuration (CORS, etc.)
│   ├── controller/     # REST Controllers
│   ├── model/          # JPA Entities
│   ├── repository/     # Spring Data JPA Repositories
│   └── service/        # Business Logic
├── src/main/resources
│   └── application.properties
├── docker-compose.yml  # Docker Compose setup for backend & PostgreSQL
├── Dockerfile          # Docker image for backend
├── pom.xml             # Maven configuration
└── ...
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd manufacturing-order-management-backend
```

### 2. Run with Docker Compose (Recommended)

You can run both the backend and PostgreSQL database using Docker Compose:

```bash
docker-compose up --build
```

- The backend will be available on port `8082`.
- PostgreSQL will be available on port `5433` (mapped to container's `5432`).
- To stop the services, press `Ctrl+C` if running in the foreground, or run `docker-compose down` in another terminal.

#### Environment Variables

The backend is configured to use the following database connection (see `src/main/resources/application.properties`):

```
spring.datasource.url=jdbc:postgresql://postgres:5432/manufacturing_order_management_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

> **Note:** When running locally (without Docker), use `localhost:5433` as the host and port for PostgreSQL.

### 3. (Alternative) Start PostgreSQL with Docker Only

If you only want to run the database in Docker and the backend locally:

```bash
docker-compose up -d postgres
```

Then run the backend with Maven:

```bash
./mvnw spring-boot:run
```

Or, on Windows:

```bat
mvnw.cmd spring-boot:run
```

---

## API Endpoints

- **Manufacturing Orders**: `/api/manufacturing-orders`
- **Machines**: `/api/machines`
- **Products**: `/api/products`
- **Employees**: `/api/employees`
- **KPIs**: `/api/kpis`

Each resource supports standard CRUD operations and validation. See controller classes for detailed endpoint documentation.

---

## Database Schema

- **Entities**: ManufacturingOrder, Machine, Product, Employee
- **Relationships**: Orders are linked to Products and Machines; Employees are assigned to Machines.
- **Auditing**: Automatic `createdAt` and `updatedAt` fields.

---

## Development & Contribution

- Fork and clone the repository.
- Create a feature branch (`git checkout -b feature/your-feature`).
- Commit your changes and open a pull request.

---

## License

This project is licensed under the Apache License 2.0.

---

## Contact

For questions or support, please contact the project maintainer.
