# RabbitMQ Stress Test with Spring Boot

This project demonstrates how to integrate **Spring Boot** with **RabbitMQ** and perform a **stress test** by sending thousands of messages through an HTTP endpoint into a queue. It also includes a **dead-letter queue** example and basic consumers to simulate real-world message processing.

## 📌 Features

* REST endpoint (`/api/send`) to publish messages into a RabbitMQ queue.
* `email` queue for processing messages.
* `deadQueue` to handle failed/redirected messages.
* Consumers that randomly decide whether to process or dead-letter a message.
* Stress test (`emailSenderStressTest`) that benchmarks average and median latency when sending up to 100k messages.
* Health check endpoint (`/api/health`).

## 🛠️ Tech Stack

* **Java 21+**
* **Spring Boot**
* **Spring AMQP**
* **RabbitMQ**
* **JUnit 5** & **MockMvc** for testing

## 🚀 Getting Started

### Prerequisites

* [RabbitMQ](https://www.rabbitmq.com/download.html) running locally (default host: `localhost`, port: `5672`).
* Java 21+ installed.

### Run RabbitMQ (Docker Example)

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
```

RabbitMQ Management UI will be available at:
👉 [http://localhost:15672](http://localhost:15672) (default user/password: `guest` / `guest`).

### Run the Application

```bash
./mvnw spring-boot:run
```

### REST Endpoints

* **POST** `/api/send` → send a message to the `email` queue.
  Example:

  ```bash
  curl -X POST http://localhost:8080/api/send \
    -H "Content-Type: application/json" \
    -d '{"message": "Hello RabbitMQ!"}'
  ```

* **GET** `/api/health` → simple health check.

### Consumers

* `emailReceiver`: randomly processes a message or sends it to `deadQueue`.
* `deadQueueReceiver`: logs messages from the dead-letter queue.

## 📊 Stress Testing

The project includes a JUnit test (`emailSenderStressTest`) that sends **up to 100,000 messages** and measures average latency per request.

You can run it with:

```bash
./mvnw test
```

Example output:

```
average latency: 0.8 ms/request
```

> ⚠️ Note: `MockMvc` runs in-memory, so results do not include actual network latency. For real benchmarks, use a load-testing tool (e.g., JMeter, Gatling, k6) against the running application.

## 📈 Metrics and Monitoring

You can monitor queues and consumers via:

* RabbitMQ Management UI (`http://localhost:15672`)
* Metrics such as queue length, message rates, and consumer performance.

For deeper analysis, consider integrating:

* **Spring Actuator** for application metrics.
* **Prometheus + Grafana** for observability dashboards.

---

## ✅ Next Steps

* Add percentile metrics (p95, p99 latency) to the stress tests.
* Configure dead-letter exchange (DLX) instead of manually sending to `deadQueue`.
* Simulate multi-threaded or distributed load.
