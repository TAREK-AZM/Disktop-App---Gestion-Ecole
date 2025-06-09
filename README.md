# 🎓 School Management System

A comprehensive desktop application for managing educational institutions, built with Java and JavaFX. This system provides role-based access control for Administrators, Teachers (Professeurs), Secretaries (Secrétaires), and Students (Étudiants).

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [User Roles & Permissions](#user-roles--permissions)
- [Database Schema](#database-schema)
- [Screenshots](#screenshots)
- [Development Team](#development-team)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The School Management System is a desktop application designed to streamline administrative processes in educational institutions. It provides a centralized platform for managing students, teachers, modules, and administrative tasks with an intuitive graphical user interface.

### Key Objectives
- **Centralized Management**: Unified system for all school operations
- **Role-Based Access**: Different interfaces for different user types
- **Data Integrity**: Secure data management with proper constraints
- **User-Friendly Interface**: Intuitive JavaFX-based GUI
- **Reporting & Analytics**: Dashboard with graphs and export capabilities

## ✨ Features

### 👨‍💼 Administrator Features
- **Student Management**: Add, update, delete, and view student records
- **Teacher Management**: Manage teacher profiles and assignments
- **Module Management**: Create and manage academic modules
- **Dashboard Analytics**: Visual graphs and statistics
- **Data Export**: Export data to CSV and Excel formats
- **User Account Management**: Manage system users and permissions

### 👨‍🏫 Teacher (Professeur) Features
- **Personalized Dashboard**: Custom view for assigned modules
- **Student Grades**: Manage student evaluations
- **Module Information**: View assigned modules and schedules
- **Student Lists**: Access student enrollment data

### 👩‍💼 Secretary (Secrétaire) Features
- **Student Registration**: Handle new student inscriptions
- **Administrative Tasks**: Manage administrative processes
- **Document Management**: Handle official documents
- **Communication**: Interface for school communications

### 🎓 Student (Étudiant) Features
- **Personal Dashboard**: View personal academic information
- **Module Enrollment**: View enrolled modules
- **Grade Tracking**: Monitor academic progress
- **Profile Management**: Update personal information

## 🛠 Technologies Used

### Core Technologies
- **Java 17+** - Primary programming language
- **JavaFX** - GUI framework for desktop application
- **PostgreSQL** - Database management system
- **JDBC** - Database connectivity

### Development Tools
- **Git** - Version control system
- **Maven/Gradle** - Build automation (inferred from project structure)
- **Scene Builder** - JavaFX visual layout tool

### Libraries & Frameworks
- **Apache POI** - Excel file processing
- **OpenCSV** - CSV file handling
- **JavaFX Charts** - Data visualization
- **FXML** - UI layout definition

## 🏗 System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Admin     │  │  Teacher    │  │  Secretary  │         │
│  │  Dashboard  │  │  Dashboard  │  │  Dashboard  │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Business Logic Layer                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Student    │  │  Teacher    │  │   Module    │         │
│  │  Service    │  │  Service    │  │  Service    │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Data Access Layer                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Student    │  │  Teacher    │  │   Module    │         │
│  │    DAO      │  │    DAO      │  │    DAO      │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                      │
│     ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐     │
│     │Students │  │Teachers │  │ Modules │  │  Users  │     │
│     └─────────┘  └─────────┘  └─────────┘  └─────────┘     │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17+**
- **PostgreSQL 12+**
- **JavaFX SDK** (if not included with JDK)
- **Git**

### Environment Setup

1. **Install Java JDK 17+**
   ```bash
   # Verify Java installation
   java -version
   javac -version
   ```

2. **Install PostgreSQL**
   ```bash
   # Ubuntu/Debian
   sudo apt-get install postgresql postgresql-contrib
   
   # macOS
   brew install postgresql
   
   # Windows: Download from postgresql.org
   ```

3. **Install JavaFX** (if needed)
   ```bash
   # Download JavaFX SDK from openjfx.io
   # Extract and note the path for later use
   ```

## 📦 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/TAREK-AZM/hadah2.git
cd hadah2
```

### 2. Database Setup

```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database
CREATE DATABASE school_management;

-- Create user
CREATE USER school_user WITH PASSWORD 'your_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE school_management TO school_user;

-- Connect to the new database
\c school_management;

-- Create tables (example schema)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    registration_date DATE DEFAULT CURRENT_DATE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE teachers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    specialization VARCHAR(100),
    hire_date DATE DEFAULT CURRENT_DATE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE modules (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) UNIQUE,
    description TEXT,
    credits INTEGER,
    teacher_id INTEGER REFERENCES teachers(id)
);

CREATE TABLE enrollments (
    id SERIAL PRIMARY KEY,
    student_id INTEGER REFERENCES students(id),
    module_id INTEGER REFERENCES modules(id),
    enrollment_date DATE DEFAULT CURRENT_DATE,
    grade DECIMAL(4,2),
    UNIQUE(student_id, module_id)
);
```

### 3. Configure Database Connection

Create or update the database configuration file:

```java
// DatabaseConnection.java
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/school_management";
    private static final String USERNAME = "school_user";
    private static final String PASSWORD = "your_password";
    
    // ... rest of the connection logic
}
```


## 👥 User Roles & Permissions

### 🔐 Administrator
- **Full System Access**: Complete CRUD operations on all entities
- **User Management**: Create and manage user accounts
- **System Configuration**: Configure system settings
- **Reports & Analytics**: Access to all system reports and dashboards
- **Data Export**: Export data in various formats (CSV, Excel)

### 👨‍🏫 Teacher (Professeur)
- **Module Management**: Manage assigned modules
- **Student Grades**: Input and update student grades
- **Student Lists**: View enrolled students
- **Personal Dashboard**: Customized view of teaching responsibilities

### 👩‍💼 Secretary (Secrétaire)
- **Student Registration**: Handle new student enrollments
- **Administrative Tasks**: Manage school administrative processes
- **Document Management**: Handle official school documents
- **Communication**: Manage school communications

### 🎓 Student (Étudiant)
- **Personal Information**: View and update personal details
- **Academic Records**: View grades and academic progress
- **Module Information**: View enrolled modules and schedules
- **Dashboard**: Personalized academic dashboard



### Key Constraints
- **Foreign Key Constraints**: Ensure referential integrity
- **Unique Constraints**: Prevent duplicate emails and usernames
- **Check Constraints**: Validate data integrity (grades, dates, etc.)

## 📊 Key Features Implementation

### Dashboard Analytics
- **Student Statistics**: Visual graphs showing enrollment trends
- **Teacher Workload**: Distribution of modules per teacher
- **Module Popularity**: Most enrolled modules
- **Performance Metrics**: Grade distributions and analytics

### Data Export
- **CSV Export**: Export student and teacher data to CSV format
- **Excel Export**: Generate detailed Excel reports
- **Custom Reports**: Generate reports based on specific criteria

### User Interface
- **Responsive Design**: Adaptive layout for different screen sizes
- **Intuitive Navigation**: Easy-to-use menu system
- **Confirmation Dialogs**: Prevent accidental data deletion
- **Form Validation**: Client-side validation for data integrity

## 🔧 Development Workflow

Based on the commit history, the development process followed these stages:

1. **Project Setup** (Jan 1, 2025)
   - Initial project structure
   - Database connection setup
   - Entity classes creation

2. **Core Development** (Jan 1-4, 2025)
   - DAO and Service layer implementation
   - CRUD operations for all entities
   - Dashboard development

3. **UI Enhancement** (Jan 4-8, 2025)
   - Attractive dashboard design
   - Graph implementation
   - Export functionality (CSV/Excel)

4. **Feature Completion** (Jan 8-10, 2025)
   - Professor dashboard customization
   - Delete confirmation dialogs
   - Logic fixes and optimizations

## 👥 Development Team

| Developer | GitHub | Contributions |
|-----------|--------|---------------|
| **TAREK-AZM** | [@TAREK-AZM](https://github.com/TAREK-AZM) | Project Lead, Infrastructure, Admin Features, Dashboards |
| **anasbenmguirida** | [@anasbenmguirida](https://github.com/anasbenmguirida) | Entities, UI Development, Logic Implementation |
| **MohammedLouah** | [@MohammedLouah](https://github.com/MohammedLouah) | DAO/Service Layer, Professor Features, Database Constraints |

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

### Development Setup
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Follow the existing code style and architecture
4. Test your changes thoroughly
5. Commit with clear messages (`git commit -m 'Add some AmazingFeature'`)
6. Push to the branch (`git push origin feature/AmazingFeature`)
7. Open a Pull Request

### Code Standards
- Follow Java naming conventions
- Comment your code appropriately
- Maintain consistent FXML structure
- Ensure database constraints are respected
- Test UI changes across different screen sizes

### Reporting Issues
- Use the GitHub issue tracker
- Provide detailed descriptions
- Include steps to reproduce
- Attach screenshots if applicable

## 🚀 Future Enhancements

- **Online Features**: Web-based access portal
- **Mobile App**: Companion mobile application
- **Advanced Analytics**: More detailed reporting features
- **Communication System**: Internal messaging system
- **Calendar Integration**: Academic calendar and scheduling
- **Document Management**: File upload and storage system

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Contact the development team
- Check the [Wiki](https://github.com/TAREK-AZM/hadah2/wiki) for documentation

## 🙏 Acknowledgments

- **JavaFX Community** - For the excellent GUI framework
- **PostgreSQL Team** - For the robust database system
- **Apache POI Team** - For Excel processing capabilities
- **Open Source Community** - For various libraries and tools used

---

**Built with ❤️ by the School Management System Team**

#JavaFX #Java #PostgreSQL #DesktopApp #SchoolManagement #Education
