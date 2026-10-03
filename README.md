# quack

This project is a hands-on learning laboratory for exploring Java and the Quarkus framework by managing thread-safe duck transactions and counting quacks.

## Overview

- **`DuckService`**: A lock-free, concurrent duck marketplace powered by `AtomicInteger` and CAS loops. Buy them, sell them, keep your inventory in check without race conditions.
- **`GreetingService`**: A stateful greeter that keeps track of every time someone gets quacked at.

## Quick Start

```bash
./gradlew quarkusDev

# Example buying ducks
curl -X POST http://localhost:8080/duck/buy/5
```
