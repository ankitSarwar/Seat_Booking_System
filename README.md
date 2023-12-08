<h1 align = "center"> Seat_Booking_System </h1>

<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.0.5-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.0.6-brightgreen.svg" />
</a>
  
<a >
    <img alt="MySQL" src="https://img.shields.io/badge/MySQL-blue.svg">
</a>
</p>


## Overview

This repository contains the implementation of a Seat Booking System. The system manages different seat classes with associated pricing based on the booking history. During the booking process, the pricing is determined by the occupancy rate of the chosen seat class.

## Pricing Rules

- Less than 40% of seats booked: Use `min_price`. If `min_price` is not available, use `normal_price`.
- 40% - 60% of seats booked: Use `normal_price`. If `normal_price` is not available, use `max_price`.
- More than 60% of seats booked: Use `max_price`. If `max_price` is not available, use `normal_price`.



## Dependencies
The following dependencies are required to run the project:

* Spring Boot Dev Tools
* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
* Swagger

<br>

## Database Configuration
To connect to a MySQL database, update the application.properties file with the appropriate database URL, username, and password. The following properties need to be updated:
```
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/<DatabaseName>
spring.datasource.username = <userName>
spring.datasource.password = <password>
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true

```
<br>

## API End Points 

The following endpoints are available in the API:

### Get All Seats

http://3.89.49.65:8088/api/seats

### Get Seat Pricing

http://3.89.49.65:8088/api/seats/2

#### Description
Returns seat details along with pricing based on the bookings previously made for that seat class.

#### Pricing Logic

- Less than 40% booked: min_price (or normal_price if min_price is not available)
- 40% - 60% booked: normal_price (or max_price if normal_price is not available)
- More than 60% booked: max_price (or normal_price if max_price is not available)
  
### Create Booking

http://3.89.49.65:8088/api/booking

```json
{
"seatIds": [1, 2, 3],
"sserName": "ankit sarwar",
"phoneNumber": "91-9370216596"
}
```
#### Description
Creates a booking for the selected seats. Returns a booking ID and the total amount of the booking upon success.


### Retrieve Booking
http://3.89.49.65:8088/api/bookings?userIdentifier=ankit

### Deployment
The application is hosted on an Amazon EC2 instance.

### Getting Started
To run the application locally, follow these steps:

- Clone the repository.
- Configure the database connection in the application.properties file.
- Build and run the application using Maven or your preferred IDE.



