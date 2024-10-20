# Rule Engine with AST

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-green.svg)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
  - [Create Rule](#create-rule)
  - [Combine Rules](#combine-rules)
  - [Evaluate Rule](#evaluate-rule)
- [Example Usage](#example-usage)
- [Contributing](#contributing)
- [License](#license)

## Overview

**Rule Engine with AST** is a robust, 3-tier application designed to determine user eligibility based on dynamic rules. Utilizing an Abstract Syntax Tree (AST) for rule representation, it allows for the creation, combination, and evaluation of complex conditional rules. This system is ideal for scenarios requiring flexible and scalable rule management, such as eligibility checks, recommendation systems, and more.

## Features

- **Dynamic Rule Creation:** Define rules using simple strings that are parsed into ASTs.
- **Rule Combination:** Merge multiple rules efficiently to form complex logic.
- **Rule Evaluation:** Assess data against defined rules to determine eligibility.
- **RESTful API:** Interact with the rule engine through well-defined API endpoints.
- **User-Friendly UI:** Simple interface to create, combine, and evaluate rules.
- **Persistent Storage:** Store rules and their metadata in a PostgreSQL database.

## Architecture

The application follows a 3-tier architecture:

1. **Frontend:** A simple web interface for interacting with the rule engine.
2. **Backend API:** Spring Boot application handling business logic and API endpoints.
3. **Database:** PostgreSQL for storing rules and related data.

## Technologies Used

- **Backend:**
  - Java 17
  - Spring Boot 3.3.4
  - Spring Data JPA
  - PostgreSQL
  - Jackson for JSON processing
  - Lombok
- **Frontend:**
  - HTML, CSS, JavaScript
- **Build & Deployment:**
  - Maven
  - Docker

## Getting Started

### Prerequisites

- **Java 17** installed on your machine.
- **Maven** for building the project.
- **Docker** (optional) for containerization.
- **PostgreSQL** database instance.

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/rule-engine-with-ast.git
   cd rule-engine-with-ast
   
2. **Configure Database**

Update the application.properties file with your PostgreSQL credentials:

```bash
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

3. **Build the Project**

bash
./mvnw clean package

## Running the Application
Using Maven:
```bash
./mvnw spring-boot:run

The application will start on http://localhost:8080.

Using Docker:

1. ***Build the Docker Image***
```bash
docker build -t rule-engine .

2. ***Run the Docker Container***
```bash
docker run -d -p 8080:8080 --name rule-engine-container rule-engine

## API Documentation

### Create Rule
Endpoint: POST /rules/create_rule
Description: Creates a new rule from a rule string.

Request Body:
{
  "ruleString": "(age > 30 AND income < 60000)"
}

Response:
{
  "id": "UUID",
  "name": "Rule_<UUID>",
  "ruleString": "(age > 30 AND income < 60000)",
  "astJson": "{...}",
  "createdAt": "2024-10-20T12:34:56",
  "updatedAt": "2024-10-20T12:34:56"
}

### Combine Rules
Endpoint: POST /rules/combine_rules
Description: Combines multiple existing rules into a single rule.
Request Body:
{
  "ruleIds": ["UUID1", "UUID2"]
}
Response:
{
  "id": "UUID",
  "name": "CombinedRule_<UUID>",
  "ruleString": "Combined Rule",
  "astJson": "{...}",
  "createdAt": "2024-10-20T12:45:00",
  "updatedAt": "2024-10-20T12:45:00"
}

### Evaluate Rule
Endpoint: POST /rules/{id}/evaluate_rule
Description: Evaluates a rule against provided data.

Path Parameter: id - UUID of the rule to evaluate.
Request Body:
{
  "age": 35,
  "income": 50000,
  "experience": 8,
  "department": "Engineering"
}
Response:
{
  "result": true
}

## Example Usage
1. ***Create Individual Rules***

- Rule 1:
{
  "ruleString": "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)"
}

- Rule 2:
{
  "ruleString": "((age > 30 AND department = 'Marketing')) AND (salary > 20000 OR experience > 5)"
}

2. ***Combine Rules***

Combine Rule 1 and Rule 2 using their UUIDs.

3. ***Evaluate Combined Rule***

Enter RuleID - [combinedRuleID] or [RuleID]
{
  "age": 35,
  "department": "Sales",
  "salary": 60000,
  "experience": 3
}

Response:
{
  "result": true
}

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## Fork the Project
Create your Feature Branch

git checkout -b feature/YourFeature
Commit your Changes

git commit -m "Add some feature"
Push to the Branch

git push origin feature/YourFeature
Open a Pull Request

## License
This project is licensed under the MIT License.
