# Ocean View Resort – Reservation System

A web-based Hotel Reservation System developed for the Ocean View Resort case study.  
The system is built using **HTML/CSS/JavaScript** for the UI, **Java Servlets (Tomcat)** for server-side processing, and **MySQL** for database storage.

## Features
- User Login (DB authentication) with session management
- Secure access control using `AuthFilter` (blocks direct URL access)
- Add Reservation (stored in MySQL)
- View Reservation (search by reservation number)
- Generate Bill (nights × room rate) + Print bill
- Reservation Report (date range) + Print report
- Revenue Report (date range) + Print report
- Help page

## Tech Stack
- Frontend: HTML, CSS, JavaScript
- Backend: Java, Servlets, Apache Tomcat
- Database: MySQL (JDBC)
- Version Control: Git, GitHub

## Database Setup (MySQL)
Create the database and tables (example):
- `users`
- `reservations`
- `room_rates`

Also includes:
- Trigger `trg_validate_reservation_dates` (prevents invalid date ranges)

## How to Run (NetBeans)
1. Open the project in NetBeans.
2. Ensure Apache Tomcat is configured in NetBeans.
3. Start MySQL server.
4. Update database credentials in `DBConnection.java`.
5. Run the project.
6. Open:
   - `http://localhost:8080/HotelReservationSystem/login.html`

## Test Login
- Username: `admin`
- Password: `admin123`

 ## Documentation / Report
Report Link: **(https://docs.google.com/document/d/1Jo82CV9K68O33ShJ9E_7hN1IsET09sdo/edit?usp=drive_link&ouid=102051637344667308266&rtpof=true&sd=true)**


