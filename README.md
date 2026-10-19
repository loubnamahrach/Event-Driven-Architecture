# Event-Driven Architecture with Apache Kafka & Spring Cloud Streams

## 📋 Description
Projet de Programmation Distribuée - M2 ENSET  
Architecture événementielle complète avec Apache Kafka et Spring Cloud Streams.

---

## 🚀 Partie 1 : Installation manuelle de Kafka

### 1. Télécharger Kafka
```bash
# Télécharger depuis https://kafka.apache.org/downloads
# Version recommandée : kafka_2.13-3.4.0
```

### 2. Démarrer Zookeeper
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
# Windows :
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

### 3. Démarrer Kafka Server
```bash
bin/kafka-server-start.sh config/server.properties
# Windows :
bin\windows\kafka-server-start.bat config\server.properties
```

### 4. Test avec kafka-console-producer et kafka-console-consumer
```bash
# Créer un topic
bin/kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092

# Lancer le producer
bin/kafka-console-producer.sh --topic test-topic --bootstrap-server localhost:9092

# Dans un autre terminal, lancer le consumer
bin/kafka-console-consumer.sh --topic test-topic --from-beginning --bootstrap-server localhost:9092
```

---

## 🐳 Partie 2 : Kafka avec Docker

### Prérequis
- Docker Desktop installé et en cours d'exécution

### Démarrer les conteneurs
```bash
docker-compose up -d
```

### Test avec kafka-console-producer et kafka-console-consumer
```bash
# Lancer le producer
docker exec -it kafka-broker kafka-console-producer --topic R1 --bootstrap-server kafka-broker:29092

# Lancer le consumer (dans un autre terminal)
docker exec -it kafka-broker kafka-console-consumer --topic R1 --from-beginning --bootstrap-server kafka-broker:29092
```

---

## ⚙️ Partie 3 : Spring Cloud Streams

### Architecture des services

```
┌─────────────────────────────────────────────────────────────┐
│                     Apache Kafka Broker                      │
│  Topics: R1 (events), R2 (analytics)                        │
└──────────┬──────────────┬────────────────┬──────────────────┘
           │              │                │
    ┌──────▼─────┐  ┌─────▼──────┐  ┌─────▼──────────────────┐
    │  Producer  │  │  Consumer  │  │   Streams Service       │
    │  Service   │  │  Service   │  │   (Analytics + Web UI)  │
    │  (REST API)│  │            │  │                         │
    └────────────┘  └────────────┘  └─────────────────────────┘
           │
    ┌──────▼──────┐
    │  Supplier   │
    │  Service    │
    │ (Auto-gen)  │
    └─────────────┘
```

### Services

| Service | Port | Description |
|---------|------|-------------|
| kafka-producer-service | 8080 | Publie des événements via REST |
| kafka-consumer-service | 8081 | Consomme les événements |
| kafka-supplier-service | 8082 | Génère automatiquement des événements |
| kafka-streams-service  | 8083 | Analytics temps réel + Dashboard Web |

### Démarrage des services
```bash
# 1. Démarrer Kafka (Docker)
docker-compose up -d

# 2. Démarrer chaque service Spring Boot
cd kafka-producer-service && mvn spring-boot:run
cd kafka-consumer-service && mvn spring-boot:run
cd kafka-supplier-service && mvn spring-boot:run
cd kafka-streams-service  && mvn spring-boot:run
```

### Endpoints

#### Producer Service (port 8080)
```
GET /publish/{topic}/{name}  → Publier un événement avec un nom de page
```

#### Streams Service (port 8083)
```
GET /         → Dashboard temps réel
GET /analytics → SSE stream des données analytics
```

---

## 🛠️ Technologies
- Java 17
- Spring Boot 3.1.x
- Spring Cloud Stream 4.x
- Apache Kafka 3.4
- Kafka Streams API
- Thymeleaf + Chart.js
- Docker & Docker Compose

<!-- Updated with Kafka test commands -->

## Statut : Projet complet - Architecture evenementielle Kafka
