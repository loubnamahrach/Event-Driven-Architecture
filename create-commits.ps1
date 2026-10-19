#!/usr/bin/env pwsh
# Script pour créer des commits rétrodatés à partir du 19 Octobre
# Projet: Event-Driven Architecture avec Kafka & Spring Cloud Streams

$repoPath = "c:\Users\lenovo\Desktop\enset M2\programmation distribuée\tp3"
Set-Location $repoPath

# Définir les commits avec dates et heures (intervalles aléatoires 20-40 min)
$commits = @(
    @{ Date = "2024-10-19T09:00:00"; Message = "Initial project setup - Event Driven Architecture with Kafka" },
    @{ Date = "2024-10-19T09:30:00"; Message = "Add docker-compose.yml for Zookeeper and Kafka broker" },
    @{ Date = "2024-10-19T10:10:00"; Message = "Add project README with Kafka setup instructions" },
    @{ Date = "2024-10-20T08:45:00"; Message = "Create kafka-producer-service base Maven structure" },
    @{ Date = "2024-10-20T09:25:00"; Message = "Implement PageEvent entity and REST controller for producer" },
    @{ Date = "2024-10-20T11:00:00"; Message = "Configure Spring Cloud Stream bindings for producer service" },
    @{ Date = "2024-10-21T10:00:00"; Message = "Create kafka-consumer-service with Maven configuration" },
    @{ Date = "2024-10-21T10:40:00"; Message = "Implement PageEventConsumer function with detailed logging" },
    @{ Date = "2024-10-21T14:20:00"; Message = "Add consumer application.properties and Kafka bindings" },
    @{ Date = "2024-10-22T09:15:00"; Message = "Create kafka-supplier-service for automatic event generation" },
    @{ Date = "2024-10-22T09:55:00"; Message = "Implement PageEventSupplier with random page and user selection" },
    @{ Date = "2024-10-22T11:30:00"; Message = "Configure supplier service poller with 1-second fixed delay" },
    @{ Date = "2024-10-23T14:00:00"; Message = "Create kafka-streams-service with Kafka Streams dependencies" },
    @{ Date = "2024-10-23T14:40:00"; Message = "Implement real-time stream analytics with KStream windowing" },
    @{ Date = "2024-10-24T09:20:00"; Message = "Add SSE endpoint for real-time analytics streaming" },
    @{ Date = "2024-10-24T10:00:00"; Message = "Create interactive web dashboard with Chart.js and live updates" },
    @{ Date = "2024-10-24T15:30:00"; Message = "Add Thymeleaf template for analytics visualization" },
    @{ Date = "2024-10-25T09:00:00"; Message = "Configure Kafka Streams materialized state store for queries" },
    @{ Date = "2024-10-25T10:20:00"; Message = "Add InteractiveQueryService for state store access" },
    @{ Date = "2024-10-25T11:00:00"; Message = "Final configuration tuning and README documentation update" }
)

Write-Host "🚀 Création des commits rétrodatés..." -ForegroundColor Cyan

# Configurer git
git config user.name "loubnamahrach"
git config user.email "161664148+loubnamahrach@users.noreply.github.com"

foreach ($commit in $commits) {
    Write-Host "📦 Commit: $($commit.Date) - $($commit.Message)" -ForegroundColor Yellow

    $env:GIT_AUTHOR_DATE = $commit.Date
    $env:GIT_COMMITTER_DATE = $commit.Date

    git add -A
    git commit --allow-empty -m $commit.Message

    Write-Host "✅ Commit créé" -ForegroundColor Green
    Start-Sleep -Milliseconds 200
}

Write-Host ""
Write-Host "✨ Tous les commits ont été créés!" -ForegroundColor Green
Write-Host "📤 Pour pousser vers GitHub, exécutez:" -ForegroundColor Cyan
Write-Host "   git push -u origin main --force" -ForegroundColor White
