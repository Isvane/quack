# quack

This project is a hands-on learning laboratory for exploring Java and the Quarkus framework by managing thread-safe duck transactions and counting quacks.

## Overview

- **`DuckService`**: A persistent duck marketplace powered by **Hibernate ORM with Panache** and an H2 database. Uses pessimistic row locking (`PESSIMISTIC_WRITE`) and `@Transactional` boundaries to handle concurrent buys and sells without race conditions.
- **`GreetingService`**: A stateful greeter that keeps track of every time someone gets quacked at.

## Persistence
- **ORM**: Hibernate ORM with Panache (`DuckInventory` entity).
- **Storage**: Local file-based H2 database (`./data/duckdb`).
- **Seeding**: Automatically seeds stock on first startup (`ducks.number=100`).

## Quick Start

```bash
./gradlew quarkusDev

# Check current stock
curl -i http://localhost:8080/duck/status

# Example buying ducks
curl -i -X POST http://localhost:8080/duck/buy \
        -H "Content-Type: application/json" \
        -d '{"quantity": 3}'

# Example selling ducks
curl -i -X POST http://localhost:8080/duck/sell \
        -H "Content-Type: application/json" \
        -d '{"quantity": 2}'
```
