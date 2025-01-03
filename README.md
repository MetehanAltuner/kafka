# Dynamic Kafka Consumer with Debezium Integration

This project implements a dynamic Kafka consumer using Spring Boot, which integrates with Debezium to listen for changes in multiple databases and syncs them dynamically with target databases based on configurable mappings. The architecture supports dynamic topic subscription and real-time data synchronization.

---

## Features

- **Dynamic Topic Subscription**: Automatically fetches topics from the database and creates Kafka listeners.
- **Debezium Integration**: Processes CDC (Change Data Capture) events from multiple databases.
- **Real-Time Data Synchronization**: Syncs data changes to target databases and tables dynamically.
- **Configurable Mappings**: Uses a database-driven configuration for mapping source topics, fields, and target tables/columns.
- **Scalable Design**: Supports multiple topics and listeners with real-time performance.

---

## Technologies Used

- **Spring Boot**: Backend framework.
- **Spring Kafka**: Kafka integration.
- **Debezium**: Change Data Capture (CDC) platform.
- **Hibernate (JPA)**: ORM for database interaction.
- **PostgreSQL / MSSQL**: Database systems.
- **Jackson**: JSON processing.

---

## How It Works

1. **Debezium and Kafka Integration**:
   - Debezium captures changes in the source databases and writes them to Kafka topics in CDC format.

2. **Dynamic Topic Management**:
   - Topics are fetched dynamically from the `topics` table in the database.
   - A `Scheduled` job checks for new topics and dynamically creates listeners for them.

3. **Real-Time Message Processing**:
   - Each message is parsed, and the operation type (`INSERT`, `UPDATE`, `DELETE`) is identified.
   - The data is then synced with the target database based on configurations in the `mappings` table.

4. **Database Interaction**:
   - Uses `EntityManager` to perform dynamic updates or deletions on the target database.

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
| `sink_table`     | VARCHAR(255)     | Target database table name.       |
| `sink_column`    | VARCHAR(255)     | Target column name.               |

---

## How to Run

1. **Set Up the Environment**:
   - Install and configure Kafka and Debezium.
   - Configure the source and target databases.

2. **Build the Project**:
   ```bash
   mvn clean install
