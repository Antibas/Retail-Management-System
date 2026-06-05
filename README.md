# Retail Management System

A REST API for managing retail operations — products, customers, and purchases — with a built-in loyalty points system.

## Tech Stack

- **Java 26** / **Spring Boot 4**
- **PostgreSQL** (via Docker)
- **Flyway** for schema migrations
- **Hibernate Envers** for entity auditing
- **Lombok**

## Getting Started

### Prerequisites

- Java 26+
- Maven (or use the included `./mvnw`)
- Docker

### Run

```bash
# Start the database
docker compose up -d

# Start the application
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080/api`.

> When running via `spring-boot:run`, Spring Boot will automatically start the Docker Compose database if it isn't already running.

### Build & Test

```bash
./mvnw clean package
./mvnw test
```

## API Reference

All endpoints are prefixed with `/api`. Error responses have the shape `{"error": "<message>"}`.

### Customers

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/customers` | Create a customer |
| `GET` | `/customers` | List customers (optional `?query=` for name/email/phone search) |
| `GET` | `/customers/{id}` | Get a customer |
| `PUT` | `/customers/{id}` | Update a customer |
| `DELETE` | `/customers/{id}` | Soft-delete a customer |
| `GET` | `/customers/{id}/purchases` | List purchases for a customer |

**Customer body:**
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com",
  "phone": "+30 210 0000000"
}
```

**Customer response** includes `points` (current redeemable balance) and `tier` (`SILVER` / `GOLD` / `PLATINUM`).

### Products

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/products` | Create a product |
| `GET` | `/products` | List products (optional `?query=` search) |
| `GET` | `/products/{id}` | Get a product |
| `PUT` | `/products/{id}` | Update a product |
| `DELETE` | `/products/{id}` | Soft-delete a product |

**Product body:**
```json
{
  "name": "Widget",
  "description": "A fine widget",
  "price": 19.99,
  "sku": "WGT-001",
  "stockQuantity": 100
}
```

### Purchases

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/purchases/customer/{customerId}` | Create a purchase |
| `GET` | `/purchases/{id}` | Get a purchase |
| `GET` | `/purchases` | List all purchases |

**Purchase body:**
```json
{
  "redeemedPoints": 0,
  "items": [
    { "productId": "<uuid>", "quantity": 2 }
  ]
}
```

Set `redeemedPoints` to a positive multiple of 100 to apply a discount. Stock is decremented on purchase.

## Loyalty Points System

Points are awarded per purchase and tracked per customer as time-stamped batches.

### Earning Points

Points are only earned on purchases where no points are redeemed.

| Tier | Lifetime Points Required | Earn Rate |
|------|--------------------------|-----------|
| SILVER | 0 – 499 | 1 pt per €10 spent |
| GOLD | 500 – 1,999 | 1.5 pts per €10 spent |
| PLATINUM | 2,000+ | 2 pts per €10 spent |

Formula: `floor(totalAmount / 10) × tierMultiplier`

### Redeeming Points

- **100 points = €10 discount** applied to the purchase total
- Minimum balance of **20 points** required to redeem
- Redeemed amount must be a **multiple of 100**
- Points are consumed oldest-batch-first (FIFO)

### Expiry

Points batches expire **1 year** after they were earned. A scheduled job runs daily at midnight to mark expired batches.
