# PayPal Integration App

A **Spring Boot** web application that securely integrates **PayPal’s REST API** for real-time payment processing. This project demonstrates backend development skills, API integration, and secure transaction handling by instantly verifying user payments and crediting them to the developer's PayPal account.

---

## Features

- **Secure Payment Processing:** Integrates with PayPal’s REST API to handle transactions safely.
- **Instant Transaction Verification:** Confirms and logs transactions in real-time.
- **Transaction History:** Tracks all user payments for monitoring and reporting.
- **Backend-First Implementation:** Demonstrates API integration, request handling, and server-side security.
- **Scalable Architecture:** Spring Boot-based structure for easy feature extension.

---

## Tech Stack

- **Backend Framework:** Spring Boot  
- **API Integration:** PayPal REST API  
- **Build Tool:** Gradle or Maven  
- **Database:** H2 / MySQL (for storing transaction records)  
- **Security:** HTTPS, API credentials management, and server-side validation

---

## Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Headmaster-22/PayPal-Integration-App.git
cd PayPal-Integration-App
```
## Configure PayPal Credentials:

Obtain a PayPal REST API Client ID and Secret from PayPal Developer
.

Add credentials to application.properties:

```bash
paypal.client.id=YOUR_CLIENT_ID
paypal.client.secret=YOUR_CLIENT_SECRET
paypal.mode=sandbox
```

## Build the project
```bash
./gradlew build
```

#Run the project
```bash
./gradlew bootRun
```

###The app will run on http://localhost:8080.
