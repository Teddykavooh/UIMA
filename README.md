# 🩺 UIMA

**UIMA** is an Android application designed to simplify the process of **patient registration, 
vitals recording, and health assessments** in healthcare facilities. The app provides an 
**offline-first workflow**, allowing health workers to register patients, collect vital signs, 
conduct medical assessments, and later **sync the data** to a remote server when an internet 
connection is available.

---

## 🔍 Overview

UIMA was developed as part of a mobile developer practical to demonstrate the application of 
**Object-Oriented Programming (OOP)**, **Test-Driven Development (TDD)**, and **clean architecture**
principles using **Java** and **SQLite (Room ORM)**.

The application consists of five key pages:

1. 🧾 **Patient Registration** – Capture unique patient details (ID, name, gender, date of birth, registration date).
2. 📏 **Vitals Form** – Record vital signs such as height, weight, and automatically compute BMI.
3. 💬 **General Assessment** – Conduct general health assessments for patients with normal BMI values (BMI < 25).
4. ⚖️ **Overweight Assessment** – Collect additional data for patients with BMI ≥ 25.
5. 📋 **Patient Listing** – Display all registered patients with filtering and BMI classification (Underweight, Normal, Overweight).

---

## ⚙️ Key Features

- 🗂️ **Offline-first data handling** — Patient data is stored locally using Room (SQLite ORM).
- 🔄 **Sync-on-demand** — Locally stored data can be pushed to the backend API when requested.
- 🧮 **Automated BMI calculation** — Automatically calculates BMI based on height and weight inputs.
- 🧱 **Clean architecture** — Clear separation of layers: Models, DAOs, Repositories, and UI.
- 🧪 **TDD-friendly design** — Unit-tested business logic components such as BMI calculation and validation.
- 🔐 **Input validation** — Ensures all required fields are completed before saving or syncing.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| **Language** | Java |
| **Database** | SQLite (Room ORM) |
| **Architecture** | MVVM / Repository Pattern |
| **Networking** | Retrofit |
| **Testing** | JUnit, Mockito |
| **Design** | XML Layouts, Material Components |

---

## 🌐 API Integration

UIMA connects to the following backend API:
`https://patientvisitapis.intellisoftkenya.com/api/`


### Example Endpoints

- `POST user/signup` – Register new user
- `POST user/login` – Authenticate user and retrieve token
- `POST patients/register` – Register new patient
- `POST vitals/add` – Submit patient vitals
- `GET patients/list` – Retrieve list of patients

---

## 🧩 Architecture Overview

    com.teddykavooh.uima/
    │
    ├── model/ # Data models (Entities)
    │ ├── Patient.java
    │ ├── Vitals.java
    │ └── Assessment.java
    │
    ├── data/
    │ ├── local/ # Room DAOs & Database
    │ ├── remote/ # Retrofit API interfaces
    │ └── repository/ # Combines local & remote data handling
    │
    ├── domain/ # Business logic (Managers, Calculators)
    │ ├── PatientManager.java
    │ └── BmiCalculator.java
    │
    └── ui/ # Activities and Fragments
    ├── registration/
    ├── vitals/
    ├── assessments/
    └── listing/

---

## 🚀 How to Run the App

### Prerequisites

- Android Studio (latest version)
- Android SDK 24 or higher
- Gradle (bundled with Android Studio)
- A device or emulator running Android 6.0 (API 23) or later

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/teddykavooh/UIMA.git
   cd UIMA

2. **Open in Android Studio**
   - Launch Android Studio.
   - Select File → Open, and choose the cloned UIMA folder.

3. **Configure the API**
   - Open `ApiClient.java` and ensure the base URL matches:
       ```java
       private static final String BASE_URL = "https://patientvisitapis.intellisoftkenya.com/api/";

4. **Build the Project**
   - Allow Gradle to sync.

5. **Run the App**
   - Connect an Android device or start an emulator.
   - Click Run ▶️ in Android Studio.

6. (Remember) Use the “Sync” button in the app to push locally saved patient data to the backend when online.

---

## 🧪 Testing

- Run unit tests for local logic using:
    ```bash
    ./gradlew test
- Tests cover:
    - BMI calculation
    - Form validation
    - Data persistence in Room (in-memory tests)
    - Repository data synchronization

---

## 📜 License

This project is provided for learning and demonstration purposes.
All rights reserved © 2025 Antony Kavoo.

---