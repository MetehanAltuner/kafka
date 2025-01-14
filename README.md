# Dynamic Kafka Consumer with Debezium Integration

This project implements a dynamic Kafka consumer using Spring Boot, which integrates with Debezium to listen for changes in multiple databases and syncs them dynamically with target databases based on configurable mappings. The architecture supports dynamic topic subscription and real-time data synchronization.

---

## Features

- **Dynamic Topic Subscription**: Automatically fetches topics from the database and creates Kafka listeners.
- **Debezium Integration**: Processes CDC (Change Data Capture) events from multiple databases.
- **Real-Time Data Synchronization**: Syncs data changes to target databases and tables dynamically.
- **Configurable Mappings**: Uses a database-driven configuration for mapping source topics, fields, and target tables/columns.
- **Scalable Design**: Supports multiple topics and listeners with real-time performance.
- **Retry and Dead Letter Queue Support**: Handles message processing errors effectively.

---

## Technologies Used

- **Spring Boot**: Backend framework.
- **Spring Kafka**: Kafka integration.
- **Debezium**: Change Data Capture (CDC) platform.
- **Hibernate (JPA)**: ORM for database interaction.
- **PostgreSQL**: Database system.
- **Jackson**: JSON processing.
- **Swagger**: API documentation.

---

## How It Works

1. **Debezium and Kafka Integration**:
   - Debezium captures changes in the source databases and writes them to Kafka topics in CDC format.

2. **Dynamic Topic Management**:
   - Topics are fetched dynamically from the `topics` table in the database.
   - A scheduled job checks for new topics and dynamically creates listeners for them.

3. **Real-Time Message Processing**:
   - Each message is parsed, and the operation type (`INSERT`, `UPDATE`, `DELETE`) is identified.
   - The data is then synced with the target database based on configurations in the `mappings` table.

4. **Database Interaction**:
   - Uses `EntityManager` to perform dynamic updates or deletions on the target database.

5. **Error Handling**:
   - Retry logic and Dead Letter Topics ensure no data is lost during processing errors.

---

## Database Schema

### **Topics Table**
Holds information about the topics to be dynamically subscribed.

| **Column**    | **Type**         | **Description**             |
|---------------|------------------|-----------------------------|
| `id`          | BIGINT (PK)      | Unique identifier.          |
| `name`        | VARCHAR(255)     | Kafka topic name.           |
| `description` | VARCHAR(500)     | Description of the topic.   |

### **Mappings Table**
Defines the relationship between source topics and target database tables/columns.

| **Column**       | **Type**         | **Description**                    |
|-------------------|------------------|------------------------------------|
| `id`             | BIGINT (PK)      | Unique identifier.                |
| `topic_id`       | BIGINT (FK)      | Foreign key to `topics.id`.        |
| `source_column`  | VARCHAR(255)     | Source column in the CDC message. |
| `target_column`  | VARCHAR(255)     | Target column name.               |
| `target_table`   | VARCHAR(255)     | Target database table name.       |

### **Databases Table**
Stores the configurations for source and target databases.

| **Column**         | **Type**         | **Description**             |
|---------------------|------------------|-----------------------------|
| `id`               | BIGINT (PK)      | Unique identifier.          |
| `name`             | VARCHAR(255)     | Database name.              |
| `connection_url`   | VARCHAR(500)     | Connection URL.             |
| `username`         | VARCHAR(255)     | Database username.          |
| `password`         | VARCHAR(255)     | Database password.          |

### **Columns Table**
Specifies the columns of the tables used in mappings.

| **Column**    | **Type**         | **Description**             |
|---------------|------------------|-----------------------------|
| `id`          | BIGINT (PK)      | Unique identifier.          |
| `name`        | VARCHAR(255)     | Column name.                |
| `table_id`    | BIGINT (FK)      | Foreign key to `tables.id`. |

### **Tables Table**
Defines the database tables involved in mappings.

| **Column**       | **Type**         | **Description**             |
|-------------------|------------------|-----------------------------|
| `id`             | BIGINT (PK)      | Unique identifier.          |
| `name`           | VARCHAR(255)     | Table name.                 |
| `database_id`    | BIGINT (FK)      | Foreign key to `databases.id`. |

---

## APIs

### **Topics APIs**
- `GET /api/topics`: Fetch all topics.
- `POST /api/topics`: Add a new topic.
- `PUT /api/topics/{id}`: Update a topic.
- `DELETE /api/topics/{id}`: Delete a topic.

### **Mappings APIs**
- `GET /api/mappings`: Fetch all mappings.
- `POST /api/mappings`: Add a new mapping.
- `PUT /api/mappings/{id}`: Update a mapping.
- `DELETE /api/mappings/{id}`: Delete a mapping.

### **Databases APIs**
- `GET /api/databases`: Fetch all database configurations.
- `POST /api/databases`: Add a new database configuration.
- `PUT /api/databases/{id}`: Update a database configuration.
- `DELETE /api/databases/{id}`: Delete a database configuration.

### **Columns APIs**
- `GET /api/columns`: Fetch all columns.
- `POST /api/columns`: Add a new column.
- `PUT /api/columns/{id}`: Update a column.
- `DELETE /api/columns/{id}`: Delete a column.

### **Tables APIs**
- `GET /api/tables`: Fetch all tables.
- `POST /api/tables`: Add a new table.
- `PUT /api/tables/{id}`: Update a table.
- `DELETE /api/tables/{id}`: Delete a table.

---

## How to Run

1. **Set Up the Environment**:
   - Install and configure Kafka and Debezium.
   - Configure PostgreSQL as the primary database.

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   java -jar target/kafka-integration.jar
   ```

4. **Access Swagger UI**:
   - Swagger UI is available at: `http://localhost:8080/swagger-ui.html`

---

## Future Enhancements

- **User Interface**: Develop a frontend to manage mappings and configurations visually.
- **Extended Error Handling**: Implement comprehensive retry and dead letter handling.
- **Custom Metrics**: Integrate additional metrics using Actuator endpoints.

