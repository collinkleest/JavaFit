# JavaFit
This repository is based on a Fitness application written in Java.

## Table of Contents
1. [Installation](#Installation)
2. [Dependencies](#Dependencies)
3. [File Structure](#Project-File-Structure)
4. [Features](#Features)
5. [Tasks](#Tasks)


### **Installation**
Maven and JDK version 8 are required for this.
First install and compile the project. 
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

*JSON-Simple*
```xml
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1.1</version>
</dependency>
```
*MongoDB*
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
└───src/main/java
│   │   file011.txt
│   │   file012.txt
│   │
│   └───com.javafit.View
│   │   │   LoginView.java
│   │   │   Register.java
│   │   └── RegistrationView.java
│   |
|   └─── com.javafit.Model
|   |   |   Person.java
|   |   └── User.java
|   |  
|   └───com.javafit.Controller
|   |   LoginController.java
└───└── NewUserController.java
    

```


### **Features**

* GUI Interface
* Weight Tracking
* Calorie Trackig
* Exercises


### Tasks
- [ ] UI / UX
    - [x] Registration View
        - [x] Fields
        - [x] Checkboxes
        - [x] Accept terms and conditions
        - [ ] Improve user experience
        - [ ] Improve astetics
            - [ ] Grid Layout, component placement
            - [ ] Colors, UI overall looks better
    - [x] Login View
        - [x] Fields
        - [x] Login, Create Account button
        - [ ] Improve user experience
        - [ ] Improve astetics
            - [ ] Colors, UI overall looks better
        - [ ] Forgot Password
    - [ ] Routines View
    - [ ] Dashboard View
- [ ] Backend
    - [ ] DB
        - [x] MongoDB external DB setup
        - [x] MongoDB DB connection
        - [x] Test users in database
        - [ ] routine databse
        - [ ] foods databse
    - [x] User
        - [x]Password Hashing
        - [x] JSON
    