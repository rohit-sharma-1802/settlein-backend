# Settlein Backend

A Spring Boot backend for the Settlein platform, providing APIs for authentication, user management, properties, products, feeds, comments, chat (with WebSocket), and notifications. Built with Java 17, Gradle, PostgreSQL, JWT, Cloudinary, and Firebase.

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Setup & Configuration](#setup--configuration)
- [Build & Run](#build--run)
- [API Flow & Usage](#api-flow--usage)
- [Authentication](#authentication)
- [WebSocket & Real-Time](#websocket--real-time)
- [Error Handling](#error-handling)
- [Sample Data](#sample-data)
- [Environment Variables](#environment-variables)
- [References](#references)

---

## Overview
Settlein Backend is a RESTful API server for a real estate and marketplace platform. It supports:
- User signup/login with OTP and JWT
- Property and product listings (with image upload)
- Feeds and comments
- Real-time chat (WebSocket)
- Notifications (REST & FCM)

## Architecture
- **Framework:** Spring Boot 3.5.3
- **Language:** Java 17
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Authentication:** JWT
- **File Storage:** Cloudinary
- **Real-Time:** WebSocket (STOMP/SockJS), Firebase FCM
- **Migration:** Flyway

## Setup & Configuration
1. **Clone the repository:**
   ```sh
   git clone <repo-url>
   cd settleinbackend/settlein
   ```
2. **Configure environment:**
   - Edit `src/main/resources/application.properties` for DB, Cloudinary, and mail credentials.
   - Ensure PostgreSQL is running and accessible.
   - Place your Firebase service account JSON at `src/main/resources/firebase/settlein-notification.json`.
3. **Install Java 17 and Gradle.**

## Build & Run
- **Build:**
  ```sh
  ./gradlew build
  ```
- **Run:**
  ```sh
  ./gradlew bootRun
  ```
- The server runs at `http://localhost:8080` by default.

## API Flow & Usage
- **Base URL:** `http://localhost:8080/api`
- **Authentication:** Most endpoints require JWT in the `Authorization` header.
- **Main Modules:**
  - `/api/auth` – Signup, OTP, login, profile
  - `/api/user` – User profile, FCM token
  - `/api/properties` – CRUD/search properties
  - `/api/products` – CRUD/search products
  - `/api/feeds` – Create/search feeds
  - `/api/comments` – Add/get comments
  - `/api/chat` – Start chat, send/get messages
  - `/api/notifications` – Get/mark notifications

**For full API details, see [`API_Documentation.md`](API_Documentation.md).**

## Authentication
- **Signup Flow:**
  1. Request OTP (`/api/auth/request-otp`)
  2. Verify OTP (`/api/auth/verify-otp`)
  3. Complete profile (`/api/auth/complete-profile`)
  4. Login (`/api/auth/login`)
- **JWT:** Returned on login/verify OTP. Include in `Authorization: Bearer <token>` for all protected endpoints.

## WebSocket & Real-Time
- **WebSocket URL:** `ws://localhost:8080/ws-chat`
- **STOMP endpoints:**
  - Send: `/app/chat/send`
  - Subscribe: `/topic/chat/{chatId}`
- **Usage:** For real-time chat messaging. See API documentation for message formats.
- **Notifications:** Uses Firebase Cloud Messaging (FCM) for push notifications.

## Error Handling
- Standard HTTP status codes (`400`, `401`, `403`, `404`)
- Error responses are JSON with an `error` field.
- See [`API_Documentation.md`](API_Documentation.md#error-responses) for examples.

## Sample Data
- Example user, property, product, feed, comment, and chat data are provided in [`API_Documentation.md`](API_Documentation.md#test-data-summary).

## Environment Variables
Edit `src/main/resources/application.properties`:
- `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
- `cloudinary.cloud-name`, `cloudinary.api-key`, `cloudinary.api-secret`
- `spring.mail.*`
- Place Firebase credentials at `src/main/resources/firebase/settlein-notification.json`

## References
- [API_Documentation.md](API_Documentation.md) – Full API details
- [HELP.md](settlein/HELP.md) – Spring Boot/Gradle usage
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Gradle Docs](https://docs.gradle.org)

---

## Contribution & Support
- PRs and issues welcome!
- For questions, contact the maintainer or open an issue. 