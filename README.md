# ⚙️ qa-microservice-test-harness - Simple Java Test Setup

[![Download](https://img.shields.io/badge/Download-Here-brightgreen)](https://github.com/Ethic-Coder/qa-microservice-test-harness)

---

A reproducible test setup for Java microservices using common tools like Spring Boot and PostgreSQL. This tool helps you run integration tests reliably on your Windows system.

## 📝 About This Application

This software creates a controlled environment to test Java microservices. It uses a combination of popular tools such as Spring Boot, PostgreSQL database, Flyway for database migrations, and Testcontainers to launch temporary environments for tests. RestAssured checks the web services.

If you are not familiar with Java or microservices, you do not need to worry. This app sets up everything behind the scenes to run tests smoothly.

The app uses automatic checks to ensure code quality. It has continuous integration systems that check formatting and how much code is tested.

## 💻 System Requirements

Before downloading, make sure your Windows PC meets these requirements:

- Windows 10 or later (64-bit recommended)
- At least 4 GB of free RAM
- 10 GB free disk space
- Internet connection for setup and downloads
- Installed Java Runtime Environment (JRE) version 11 or higher
- Docker Desktop installed and running (needed for Testcontainers)

### Installing Java Runtime Environment

If you do not have Java installed:

1. Visit https://adoptium.net/
2. Download and install the latest version of JRE 11 or above.
3. Restart your computer to complete setup.

### Installing Docker Desktop

This app requires Docker for creating temporary test environments.

1. Visit https://www.docker.com/products/docker-desktop/
2. Download the Windows version.
3. Install and launch Docker Desktop.
4. Confirm Docker is running by opening a command prompt and typing `docker version`.

## 📥 Download the Software

You can access the software on GitHub to get started:

[![Download](https://img.shields.io/badge/Download-Here-blue)](https://github.com/Ethic-Coder/qa-microservice-test-harness)

Click the badge above or visit the link below now:

**https://github.com/Ethic-Coder/qa-microservice-test-harness**

This link takes you to the project page where you can download the package files.

## 🚀 Installing and Running on Windows

Follow these steps carefully to install and start the application.

### 1. Download the Package

- Go to the GitHub link above.
- Click on the “Code” button.
- Select “Download ZIP”.
- Save the ZIP file to a folder on your PC, such as `Downloads`.

### 2. Extract the Files

- Right-click the ZIP file.
- Choose “Extract All...”.
- Select a destination folder, e.g., `Documents\qa-microservice-test-harness`.
- Click “Extract” and wait for the process to finish.

### 3. Open the Installation Folder

- Use File Explorer to navigate to the folder where you extracted files.
- Confirm files like `pom.xml` and `README.md` are present.

### 4. Check Java and Docker Setup

- Make sure Java and Docker are installed and running (see System Requirements).
- Open Command Prompt and type:
  
  ```
  java -version
  docker version
  ```

- These commands must show version info without errors.

### 5. Launch the Application

- The app uses Maven to build and run tests.
- For ease, a simple script file is included.

#### Run Using the Batch Script

- In the extracted folder, find `run-tests.bat`.
- Double-click the file to start the test harness.
- A console window will open showing progress.
- The app will start the needed services automatically.

If you prefer to run manually:

- Open Command Prompt in the folder.
- Type this command to build and run tests:

  ```
  mvn clean test
  ```

### 6. Wait for Tests to Complete

- The console will show status messages.
- Tests will take a few minutes to complete.
- You will see a report summary when tests finish.

### 7. Review Test Results

- Test reports are stored in the `target` folder.
- Open the `target/site/jacoco/index.html` file in a browser to see code coverage.
- Logs appear in the `logs` folder if you need to troubleshoot.

## 🛠 How This App Works

- The app uses Testcontainers to create a temporary PostgreSQL server on your PC.
- Flyway manages database changes automatically.
- Spring Boot runs the microservice API for testing.
- RestAssured sends test requests to the service.
- Spotless ensures code formatting.
- JaCoCo reports how much code the tests cover.

## 📂 Project Contents

- `src/main/java` — Application source code
- `src/test/java` — Test cases
- `pom.xml` — Maven build file
- `flyway` — Database migration scripts
- `run-tests.bat` — Quick start script for Windows
- `logs` — Logs generated during tests
- `target` — Build output

## 🔧 Troubleshooting

- If tests fail, confirm Docker is running.
- Make sure Java 11+ is installed and in your system PATH.
- Check if port 5432 is free; PostgreSQL runs there.
- Run the batch file again after correcting any errors.
- Inspect logs in the `logs` folder for error details.

## 🧰 Additional Tools Explained

- **Spring Boot**: Framework to run Java web services.
- **PostgreSQL**: Database used to store data.
- **Flyway**: Tool that updates database changes.
- **Testcontainers**: Runs temporary databases and services.
- **RestAssured**: Tests web APIs by sending requests.
- **Spotless**: Checks if code is properly formatted.
- **JaCoCo**: Measures how much of your code runs during tests.

## 🔗 More Information

Visit the project page regularly for updates or changes:

https://github.com/Ethic-Coder/qa-microservice-test-harness