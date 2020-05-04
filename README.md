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
Now run the application
(There will be several warnings due to Xlint:unchecked in the pom.xml; see bottom of README.md for details)
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

### Why are there compilation warnings?
The simple-json jar we have utilized for this project has caused it and there is no workaround.
In the documentation for the jar (linked below) it says:

NOTE:
With respect to the screen output shown on this page, ignore the possibility of output or lack of
output similar to the following:
Note: Code99.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

[Link to Documentation](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwi4wYOjkZnpAhVpgXIEHYb8BUQQFjAAegQIARAB&url=https%3A%2F%2Fcnx.org%2Fexports%2Fe6c441f6-0a46-44c6-9f3c-b48759faac95%4014.1.pdf%2Fthe-json-simple-java-library-14.1.pdf&usg=AOvVaw0dJPP2N-8h8uH5K4WZSTYl)

We have left the Xlint:unchecked compiler warning in our pom.xml so you are able to see this is coming from the simple-json jar file's ".put" commands.