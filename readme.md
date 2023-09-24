# Technical Test Readme

This README provides instructions for running the technical test application

## Application Setup

To run the application, you have two options:

### Option 1: Manual Setup

1. **Download the Code**: Start by downloading the source code for the Spring Boot application.

2. **Install Requirements**:
   - Make sure you have **Java 17** installed on your system.
   - Install **Maven** for managing project dependencies.

3. **Build the Application**:
   - Open a terminal/command prompt in the project root directory.
   - Run the following command to build the application:

     ```shell
     mvn clean install
     ```

4. **Run the Application**:
   - After a successful build, start the Spring Boot application:

     ```shell
     mvn spring-boot:run
     ```

   The application will start, and you can access it at the configured port (usually 8080) on your local machine.

### Option 2: Docker Image

Alternatively, you can run the application using a Docker image available on Docker Hub:

1. **Pull the Docker Image**:
   - Open a terminal/command prompt.
   - Run the following command to pull the Docker image:

     ```shell
     docker pull ivannavas/pruebatecnica:1.0.0
     ```

2. **Run the Docker Container**:
   - Start a Docker container from the pulled image:

     ```shell
     docker run -p 8080:8080 ivannavas/pruebatecnica:1.0.0
     ```

   The application will be accessible on your local machine at port 8080.

## Accessing the Endpoint

Once the application is running, you can access the endpoint using a web browser or a tool like curl:

- **Endpoint URL**: `http://localhost:8080/api/price/get-brand-product-price`

- **HTTP Method**: GET

- **Parameters**:
   - `brandId`: The ID of the brand (e.g., 1).
   - `productId`: The ID of the product (e.g., 35455).
   - `applicationDate`: The application date in ISO 8601 format (e.g., 2020-06-14T06:30:00).