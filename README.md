# SER516-Team-Louisville

## Taiga API Integration

This project is a Java application for interacting with the Taiga API to perform various task and calculating metrics.


## Setting up the application

### 1) Clone the repository


   ```bash
   git clone https://github.com/ser516asu/SER516-Team-Louisville.git
   cd SER516-Team-Louisville
   ```

### 2) Compile and Run the application

Go to the project root and compile the Maven project

```bash
   mvn compile
   ```

Now, run the project using following command

```bash
   mvn compile exec:java -Dexec.mainClass=Main
   ```
### 3) To Test

```bash
   mvn test
   ```

### 4) To create package and run the jar executable

```bash
   mvn clean package
   java -jar target/Louisville-release.jar
   ```

### NOTE

In case you don't have Maven installed, please refer to following tutorial

https://phoenixnap.com/kb/install-maven-windows


