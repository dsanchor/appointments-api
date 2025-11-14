# Appointments API

A Spring Boot REST API for managing customer appointments.

## Features

- Create, read, update, and delete appointments
- Retrieve appointments by customer ID
- Delete appointments by customer ID
- Full CRUD operations with validation
- In-memory H2 database for development

## Technology Stack

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build and Run

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run Tests

```bash
mvn test
```

## API Endpoints

### Create Appointment
```
POST /api/appointments
Content-Type: application/json

{
  "title": "Doctor Appointment",
  "notes": "Annual checkup",
  "category": "Medical",
  "startDate": "2025-11-15T10:00:00",
  "done": false,
  "customerId": 100
}
```

### Get All Appointments
```
GET /api/appointments
```

### Get Appointment by ID
```
GET /api/appointments/{id}
```

### Get Appointments by Customer ID
```
GET /api/appointments/customer/{customerId}
```

### Update Appointment
```
PUT /api/appointments/{id}
Content-Type: application/json

{
  "title": "Updated Appointment",
  "notes": "Updated notes",
  "category": "Medical",
  "startDate": "2025-11-15T11:00:00",
  "done": true,
  "customerId": 100
}
```

### Update Appointment by Customer ID and Appointment ID
```
PUT /api/appointments/customer/{customerId}/appointment/{appointmentId}
Content-Type: application/json

{
  "title": "Updated Appointment",
  "notes": "Updated notes",
  "category": "Medical",
  "startDate": "2025-11-15T11:00:00",
  "done": true,
  "customerId": 100
}
```

### Delete Appointment
```
DELETE /api/appointments/{id}
```

### Delete All Appointments for a Customer
```
DELETE /api/appointments/customer/{customerId}
```

### Delete Appointment by Customer ID and Appointment ID
```
DELETE /api/appointments/customer/{customerId}/appointment/{appointmentId}
```

## Appointment Model

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | Auto-generated | Internal appointment ID |
| title | String | Yes | Appointment title |
| notes | String | No | Additional notes |
| category | String | Yes | Appointment category |
| startDate | LocalDateTime | Yes | Appointment start date and time |
| done | Boolean | No (default: false) | Completion status |
| customerId | Long | Yes | Customer identifier |

## H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:appointmentsdb`
- Username: `sa`
- Password: (leave blank)

## Error Handling

The API includes global exception handling for:
- Validation errors (400 Bad Request)
- Not found errors (404 Not Found)
- Internal server errors (500 Internal Server Error)