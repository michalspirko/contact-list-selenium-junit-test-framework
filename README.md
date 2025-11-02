# Selenium Test Automation Framework

A comprehensive Java-based test automation framework for GUI functional testing with data-driven testing capabilities, built using Selenium WebDriver, JUnit 5, and Maven.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Suites](#test-suites)
- [Reporting](#reporting)
- [Data Sources](#data-sources)
- [Page Object Model](#page-object-model)

## 🎯 Overview

This framework is designed for automated testing of web applications, specifically targeting the Contact List application. It implements the Page Object Model design pattern and supports multiple data sources for data-driven testing.

## ✨ Features

- **Page Object Model (POM)** architecture for maintainable test code
- **Data-Driven Testing** with support for:
  - CSV files
  - MySQL databases
  - PostgreSQL databases
- **Parallel Test Execution** capabilities
- **Multi-Browser Support** (Chrome, Firefox, Edge)
- **Headless Mode** for CI/CD pipelines
- **Custom HTML Reports** with screenshots on failure
- **Screenshot Capture** for failed tests
- **API Integration** for test setup and cleanup
- **Flexible Configuration** via properties file and Maven arguments
- **JUnit 5** parameterized tests

## 🛠 Technology Stack

- **Java 17**
- **Selenium WebDriver 4.11.0**
- **JUnit Jupiter 5.10.0**
- **Maven** - Build and dependency management
- **WebDriverManager 6.3.2** - Automatic driver management
- **Apache HttpClient 4.5.14** - API testing support
- **PostgreSQL & MySQL Drivers** - Database connectivity
- **Maven Surefire Plugin 3.1.2** - Test execution and reporting

## 📁 Project Structure

```
selenium-taf/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/              # Page Object classes
│   │           ├── AddContactPage.java
│   │           ├── BasePage.java
│   │           ├── ContactDetailsPage.java
│   │           ├── EditContactPage.java
│   │           ├── LoginPage.java
│   │           ├── MyContactsPage.java
│   │           └── SignUpPage.java
│   └── test/
│       ├── java/
│       │   ├── base/               # Base test configuration
│       │   │   └── BaseTest.java
│       │   ├── listeners/          # Test listeners
│       │   │   ├── CustomTestExecutionExceptionHandler.java
│       │   │   ├── CustomTestExecutionListener.java
│       │   │   └── CustomTestWatcher.java
│       │   ├── utils/              # Utility classes
│       │   │   ├── APICalls.java
│       │   │   ├── ConfigLoader.java
│       │   │   ├── CSVReader.java
│       │   │   ├── MySQLReader.java
│       │   │   ├── PostgreSQLReader.java
│       │   │   ├── HTMLReportUtil.java
│       │   │   ├── ScreenshotUtil.java
│       │   │   └── WebDriverFactory.java
│       │   └── TS[X]_*/            # Test suites organized by feature
│       │       ├── TS1_registration/
│       │       ├── TS2_login/
│       │       ├── TS3_create_new_contact/
│       │       ├── TS4_display_contact_details/
│       │       ├── TS5_edit_contact/
│       │       ├── TS6_delete_contact/
│       │       └── TS7_logout/
│       └── resources/
│           ├── test-config.properties
│           ├── META-INF/services/
│           └── TS[X]_*/            # Test data CSV files
├── pom.xml
└── NOTES.txt
```

## 📋 Prerequisites

- Java JDK 17 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser installed
- (Optional) MySQL or PostgreSQL database for data-driven tests

## 🚀 Installation

1. Clone the repository:
```bash
git clone https://github.com/michalspirko/selenium-taf.git
cd selenium-taf
```

2. Install dependencies:
```bash
mvn clean install
```

3. Set up environment variables (required for certain tests):
```bash
export EMAIL=your_test_email@example.com
export PASSWORD=your_test_password
export DB_PASSWORD=your_database_password
```

## ⚙️ Configuration

Configure the framework by editing `src/test/resources/test-config.properties`:

```properties
# Browser Configuration
browser=chrome                    # Options: chrome, firefox, edge
headless=false                    # Run in headless mode: true, false

# Screenshot Settings
capture_screenshot=true           # Capture screenshots on failure
screenshot_directory=src/test/reports/screenshots/

# HTML Report Settings
generate_html_report=true
report_directory=src/test/reports/

# Test Environment
test.env.url=https://thinking-tester-contact-list.herokuapp.com/

# Data Source Configuration
data.source=csv                   # Options: csv, mysql, postgresql
csv.file.path=src/test/resources/TS3_create_new_contact/CNC_009_AddMultipleContactsCreateContacts_data.csv

# Database Configuration (if using mysql or postgresql)
db.name=test_data
db.table=contact_data_2023_08_26T12_05_37
db.user=mysql
db.host=localhost
db.port=3306
```

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=REG_001_ValidSignUpTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=REG_001_ValidSignUpTest#validSignUpTest
```

### Run Tests in a Package
```bash
mvn test -Dtest=TS3_create_new_contact.*Test
```

### Run with Custom Configuration
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

### Run with Parallel Execution
```bash
mvn test -Djunit.jupiter.execution.parallel.enabled=true -Djunit.jupiter.execution.parallel.mode.default=concurrent
```

### Generate Reports
```bash
mvn clean test           # Run tests and generate XML reports
mvn site                 # Generate HTML report from XML
mvn site -DskipTests=true # Generate HTML report without running tests
```

## 📊 Test Suites

### TS1 - Registration Tests
- Valid registration with all required fields
- Missing password validation
- First name with special characters
- Existing email validation

### TS2 - Login Tests
- Valid login
- Wrong password
- Wrong email

### TS3 - Create New Contact Tests
- Create contact with required fields only
- Missing first name validation
- Wrong email format validation
- Duplicate contact validation
- Multiple contacts creation (data-driven)

### TS4 - Display Contact Details Tests
- Display all contact fields
- Display contact with some empty fields

### TS5 - Edit Contact Tests
- Successful first name edit
- Email already in use validation

### TS6 - Delete Contact Tests
- Successful contact deletion
- Cancel contact deletion

### TS7 - Logout Tests
- Successful logout
- Session validation after logout

## 📈 Reporting

The framework generates two types of reports:

### 1. Maven Surefire Reports
Located in `target/surefire-reports/`
- XML format reports
- HTML format (after running `mvn site`)

### 2. Custom HTML Reports
Located in `src/test/reports/`
- Detailed test execution reports
- Screenshots attached for failed tests
- Test execution summary with success rate
- Start/end times and duration for each test

## 💾 Data Sources

### CSV Files
Store test data in CSV format with semicolon delimiter:
```csv
firstName;lastName;email;password
John;Doe;john.doe@example.com;password123
```

### MySQL Database
Configure connection in `test-config.properties` and set password via environment variable:
```bash
export DB_PASSWORD=your_mysql_password
```

### PostgreSQL Database
Similar to MySQL, configure in properties file and set password:
```bash
export DB_PASSWORD=your_postgres_password
```

## 🏗 Page Object Model

The framework implements POM architecture with the following base structure:

- **BasePage**: Common methods for all pages (URL verification, title, error handling)
- **Page-specific classes**: Encapsulate page elements and actions
- **Method chaining**: Return page objects for fluent test writing

Example:
```java
loginPage.login(email, password)
    .openAddContact()
    .addContact(firstName, lastName)
    .logout();
```

## 📝 Notes

- Ensure environment variables are set before running tests
- Database tests require a properly configured database connection
- Screenshots are only captured when `capture_screenshot=true` in config
- Parallel execution may require adjustments based on system resources
- API calls are used for efficient test setup and cleanup
