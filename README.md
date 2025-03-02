# Employee Management System

## 📌 Project Overview
This project is a simple **Employee Management System** using **MySQL** as the database. It consists of three tables:
- **departments** (Stores department details)
- **employees** (Stores employee details)
- **employees_contacts** (Stores employee contact information)

---

## 🛠️ Setup Instructions

### 1️⃣ **Download & Install MySQL Connector J**
To connect your Java application with MySQL, you need to download the **MySQL Connector/J**.
- Download from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
- Add the JAR file to your project's dependencies.

### 2️⃣ **Database Configuration**
- **Database Name:** `employee`
- Run the following SQL script to create the database and tables.

```sql
-- Create Database
CREATE DATABASE employee;
USE employee;

-- Create Departments Table
CREATE TABLE departments (
    d_id INT PRIMARY KEY AUTO_INCREMENT,
    d_name VARCHAR(255) NOT NULL,
    manager INT,
    FOREIGN KEY (manager) REFERENCES employees(id)
);

-- Create Employees Table
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    salary INT NOT NULL,
    hire_date DATE NOT NULL,
    depart_id INT,
    FOREIGN KEY (depart_id) REFERENCES departments(d_id)
);

-- Create Employees Contacts Table
CREATE TABLE employees_contacts (
    eid INT PRIMARY KEY AUTO_INCREMENT,
    phone VARCHAR(20) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    blood_grp VARCHAR(10) NOT NULL,
    emp_id INT,
    FOREIGN KEY (emp_id) REFERENCES employees(id)
);
```

---

## 📌 Features
✔️ Create, Add, update, delete employees
✔️ Manage department details
✔️ Store employee contact details
✔️ Relational database with **foreign keys**

---

## 💡 Notes
- Ensure **MySQL service** is running before connecting.
- Make sure you have the correct **JDBC URL, username, and password** configured in your Java application.

---

## 📜 License
This project is open-source. Feel free to use and modify it as needed.

---

## 🛠️ Contribution
Want to improve this project? Follow these steps:
1. Fork the repository.
2. Create a new branch (`feature-xyz`).
3. Commit your changes.
4. Push to the branch and create a pull request.

🚀 Happy coding! 🎯
