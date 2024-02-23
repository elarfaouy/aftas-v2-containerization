# AFTAS Competition Management App - Backend

### Overview

The backend of the AFTAS Competition Management App is developed using Spring Boot, providing a robust and scalable server-side architecture for managing spearfishing competitions for Aflas, a sports club in Tiznit, Morocco.

### Backend Features

#### 1. Competition Management

- Implements API endpoints for adding and listing competitions.

#### 2. Participant Management

- Implements API endpoints for participant registration.

#### 3. Hunting Management

- Implements API endpoints for adding and listing hunting-related information.

#### 4. Results Calculation

- Implements business logic for calculating competition results.
- Implements API endpoints for fetching and displaying the podium.
- Provides unit tests for different result scenarios.

#### 5. Exception Handling

- Centralized exception handling for REST controllers.
- Ensures consistent error responses.

#### 6. Pagination

- Provides pagination functionality for presenting data.

### Technical Requirements

- **Input Data Validation:**
    - Validation implemented across controllers and services.

- **Unit Testing:**
    - Comprehensive unit tests for the results calculation service.

- **Exception Handling:**
    - Centralized exception handling using RestControllerAdvice.