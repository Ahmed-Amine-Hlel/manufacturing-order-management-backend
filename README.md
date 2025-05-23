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

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Lombok**
- **PostgreSQL**
- **Docker Compose** (for local database)

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
├── docker-compose.yml  # PostgreSQL setup
├── pom.xml             # Maven configuration
└── ...
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker (for local PostgreSQL)

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd manufacturing-order-management-backend
```

### 2. Start PostgreSQL with Docker

```bash
docker-compose up -d
```

This will start a PostgreSQL instance on port `5433` with the database `manufacturing_order_management_db`.

### 3. Configure Application Properties

The default configuration is set for local development:

```
src/main/resources/application.properties
```

- `spring.datasource.url=jdbc:postgresql://localhost:5433/manufacturing_order_management_db`
- `spring.datasource.username=postgres`
- `spring.datasource.password=postgres`

### 4. Build and Run the Application

```bash
./mvnw spring-boot:run
```

Or, on Windows:

```bat
mvnw.cmd spring-boot:run
```

The backend will start on port `8082`.

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
