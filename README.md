# JavaFit
This repository is based on a Fitness application written in Java.

## Table of Contents
1. [Installation](#Installation)
2. [Dependencies](#Dependencies)
3. [File Structure](#Project-File-Structure)
4. [Features](#Features)
5. [Project Showcase](#Project-Showcase)
6. [Tasks](#Tasks)


### Compatibility
**Requirements**
* *Maven 3.6.3*
* *JDK 14*


### **Installation** 
Clone the repository
```bash
$ git clone https://github.com/collinkleest/JavaFit.git
```
Change directory into JavaFit
```bash
$ cd JavaFit/
```
Install dependencies with maven.
```bash
$ mvn clean install
```
Now run the applicaiton
```bash
$ mvn clean javafx:run
```

### **Dependencies**
All dependencies are placed in the `pom.xml` file. When the Maven project is build dependencies should compile and be added into the project, therefore allowing it to run.

*JavaFX*
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>14</version>
</dependency>    
```

*JavaFXML*
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>15-ea+4</version>
</dependency>
```

*Bootstrap Fx (Design Framework)*
```xml
<dependency>
    <groupId>org.kordamp.bootstrapfx</groupId>
    <artifactId>bootstrapfx-core</artifactId>
    <version>0.2.4</version>
</dependency>
```

*JFOENIX (Design Framework)*
```xml
<dependency>
    <groupId>com.jfoenix</groupId>
    <artifactId>jfoenix</artifactId>
    <version>9.0.8</version>
</dependency>
```

*JSON-Simple*
```xml
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1.1</version>
</dependency>
```
*MongoDB Client*
```xml
<dependency>    
    <groupId>org.mongodb</groupId>
    <artifactId>mongo-java-driver</artifactId>
    <version>3.12.2</version>
</dependency>
```

### **Project File Structure**

```
JavaFit
│   README.md
│   LICENSE    
│   pom.xml
|
└── img/project-snapshots
|   └─ *.png
|
└───src/main/java
│   │   
│   │   
│   │
│   └───com.javafit.View
│   │   │   LoginView.java
│   │   │   Register.java
│   │   |   RegistrationView.java
|   |   |   NewPassView.java
|   |   |   ResetPassView.java
|   |   |   ReportView.java
|   |   └── BMICalculatorView.java
│   |
|   └─── com.javafit.Model
|   |   |   Person.java
|   |   |   Routine.java
|   |   └── User.java
|   |  
|   └───com.javafit.Controller
|       |   LoginController.java
|       |   NewUserController.java
|       |   CustomRoutineController.java
|       |   SettingsController.java
|       |   RoutineController.java
|       |   DashController.java
|       |   ReportController.java
|       |   BMICalculatorController.java
|       |   ResetController.java
|       └── SendResetController.java
└───src/main/resources
|    |
|    |   dash.fxml
|    |   routinemaker.fxml
|    |   routinePane.fxml
|    |   routines.fxml
|    └── settings.fxml
└────────────────────────────────
```


### **Features**

* GUI Interface
* Account Login
    * Account Settings Modification
* Reporting
    * Report progresss
    * New weights
    * Pie charts
* Weight Tracking
* Calorie Tracking
* Personalized Suggestions
    * Suggested Calories
    * Suggested Macros
    * Suggested Routines
* Exercises
* Custom Routines
    * Routine Viewer
* Cross Platform 
    * Mac, Windows, Linux
* BMI Calculator


### Project Showcase

**Registration**

!["create_account"](img/project-snapshots/create_account.png)

**DashBoard**

!["dashboard view"](img/project-snapshots/dash.png)

**Routines**

!["routines view"](img/project-snapshots/routines.png)

**Custom Routines**

!["custome-routines"](img/project-snapshots/customroutine.png)

**Custom Reporting**

!["custom-reporting"](img/project-snapshots/report.png)

**Account Management**

!["account management"](img/project-snapshots/account_manager.png)

**BMI Calculator**

!["bmi-calc"](img/project-snapshots/bmi_calc.png)

**Password Reset**

!["pass-reset"](img/project-snapshots/pw_reset.png)

### Tasks
- [x] UI / UX
    - [x] Registration View
        - [x] Fields
        - [x] Checkboxes
        - [x] Accept terms and conditions
        - [x] Improve user experience
        - [x] Improve astetics
            - [x] Grid Layout, component placement
            - [x] Colors, UI overall looks better
    - [x] Login View
        - [x] Fields
        - [x] Login, Create Account button
        - [x] Improve user experience
        - [x] Improve astetics
            - [x] Colors, UI overall looks better
        - [x] Forgot Password
    - [x] Routines View
    - [x] Dashboard View
    - [x] Password Reset View
- [x] Backend
    - [x] DB
        - [x] MongoDB external DB setup
        - [x] MongoDB DB connection
        - [x] Test users in database
        - [x] routine databse
    - [x] User
        - [x] Password Hashing
        - [x] JSON
    - [x] Reset password functionality
