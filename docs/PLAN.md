# FreshHire — Plan

## What
A job aggregation platform where freshness is the core USP. Only listings from the last 7 days, sorted newest first. Stale listings auto-purged daily.

## Stack
- Java 21 + Spring Boot 3.x
- Elasticsearch 8.x (Java API Client) — sole datastore + search
- Adzuna API — data source (US + India)
- Docker Compose — local infra
- HTML + Vanilla JS — minimal frontend

## Core Behaviour
- Poll Adzuna every 6 hours across US and India
- Idempotent ingestion — Adzuna job `id` used as ES document `_id`
- `postedAt` from Adzuna preserved on first insert, never overwritten
- Jobs older than 7 days deleted daily via ES `deleteByQuery`
- Search results sorted by `postedAt` descending

## Search API — `GET /api/jobs/search`
Filters: keyword, location, country (`us`/`in`), job type, salary range, pagination

## Post-MVP
- Multi-source aggregation (The Muse, Greenhouse, Lever)
- Cross-source deduplication
- Profile-based ranked search
- Trend analytics (in-demand skills, salary benchmarks, top hiring companies)
- Job alerts
