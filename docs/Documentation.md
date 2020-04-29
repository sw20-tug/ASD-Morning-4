# Configuring the Project 

## Setup Instructions

### Requirements

* Java (obviously)
* JDK 8 or higher
* MySQL 5.6 or higher
* JetBrains IntelliJ Ultimate IDE (optional but recommended)
    * alternatively, you can skip this and just build and start the application in a terminal, typing the following:
        
        ```
        $ cd $PROJECT_ROOT_DIR/
        $ mvn package
        $ cd target/
        $ java -jar target/project-0.0.1-SNAPSHOT.jar
        ```
        
    * <b>Be sure</b> that you edited your [MySQL configurations](#adding-mysql-to-our-project) beforehand.

### Building and Running in IntelliJ

* (If you don't use IntelliJ, see under [Requirements](#requirements) how you can build/run this application)
* Clone/Download this repository 
* Start IntelliJ and click 'Open'
* Navigate in your cloned repo folder, choose the `pom.xml` file and click 'ok'
* After prompted 'pom.xml is a project file' click 'Open as Project'
* Before you run the project, configure [MySQL](#adding-mysql-to-our-project).
* To start the application hit the green 'play button' (VocabularyTrainerApplication selected), the spring boot application will start (see Run)
  * Alternatively, you can also right-click on `VocabularyTrainerApplication.java` and click 'Run'  
* Navigate in your Browser to the home url: [http://localhost:8080](http://localhost:8080)

#### Adding MySQL to our Project

* Download and Install MySQL, setup everything 
in the workbench (create user `student` and password `student` for simplicity)
 and start the server (default port is 3306).

* Login into MySQL: `mysql -u student -p`

* Create the database: `CREATE DATABASE db_vocabularytrainer;`

* Grant access: `GRANT ALL PRIVILEGES ON * . * TO 'student'@'localhost';`

* In the project, add the following to `src/recources/application.properties`:

    ```
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_vocabularies
    spring.datasource.username=student
    spring.datasource.password=student
    ```

* (Replace `username` and `password` with your MySQL configuration if your credentials differ).

# Project Overview

## Project Skeleton

__Note__: user specific files (.idea, project.iml) and directories (target/) are not included and ignored (.gitignore).
```
src
 +- main
 |   +- resources
 |   |   +- templates
 |   |   |   +- index.html
 |   |   |   |
 |   |   |   +- login.html
 |   |   |   |
 |   |   |   +- access-denied-page.html
 |   |   |   |
 |   |   |   +- user
 |   |   |       +- index.html
 |   |   |       |
 |   |   |       +- addvoc_form.html
 |   |   |
 |   |   +- static
 |   |   |   +- style.css 
 |   |   |
 |   |   +- application.properties
 |   +- java
 |       +-com
 |          +- vocabularytrainer
 |              +- project
 |                  +- VocabularyTrainerApplication.java
 |                  |
 |                  +- db
 |                  |   +- VocabularyEntries.java
 |                  |   +- VocabularyRepository.java
 |                  |
 |                  +- controller
 |                  |   +- MVCController.java
 |                  |
 |                  +- security
 |                      +- SpringSecurityLogin.java 
 +- test     
 |   +- java
 |       +- com
 |           +- vocabularytrainer
 |               +- project
 |                   +- VocabularyTrainerApplicationTests.java
 |
 +- docs
 |   +- Documentation.md
 |   |
 |   +- fix_cannot_build.jpeg
 |
 +- .gitignore
 |
 +- mvnw
 |
 +- mvnw.cmd
 |
 +- pom.xml
 |
 +- README.md
 
```

## Frameworks, Templates, Technologies etc. used

* Maven
* Spring Boot
* Thymeleaf
* MySQL
* Hibernate
* Bootstrap

## `pom.xml` configuration and dependencis

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.5.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.vocabularytrainer</groupId>
	<artifactId>project</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>vocabulary-trainer</name>
	<description>A Vocabulary Trainer Web Application.</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>


        <!--
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity4</artifactId>
        </dependency>
        -->

    </dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

# Troubleshooting

### Fix, if you can't build the project (after pulling)

![img_fix_cannot_build](fix_cannot_build.jpeg "picture")

### MySQL related errors

If you get a timezone error, replace your application.entries with the following:

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_vocabularytrainer?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=student
spring.datasource.password=student
```

### Useful Spring Boot, Thymeleaf, Bootstrap Get Started-Links

* [Setup IntelliJ with Spring Boot using Spring Initializr](https://medium.com/danielpadua/java-spring-boot-intellij-idea-b919b0097a0) (small correction: don't import, just open the project)
* [Basic Tutorial using Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Some Interesting IntelliJ IDE Features](https://www.jetbrains.com/help/idea/spring-boot.html)
* [Using Spring Boot Annotations and explaining how they work](https://www.javatpoint.com/spring-boot-annotations)
* [Spring Boot MySQL example](https://spring.io/guides/gs/accessing-data-mysql/)
* [Great step by step tutorial using Spring Boot, Database and Thymeleaf](https://www.vogella.com/tutorials/SpringBoot/article.html) <b style="color: red">(!!! Highly recommend !!!)</b>
* [Thymeleaf Getting Started Refernces](https://www.javatpoint.com/spring-boot-thymeleaf-view)
* [Explaination, what a Template (like Thymeleaf) is for](https://hackernoon.com/java-template-engines-ef84cb1025a4)
* [How to use @Queries in Spring Boot](https://www.baeldung.com/spring-data-jpa-query)
* [Using Spring Boot Securityto create a Login System](https://memorynotfound.com/spring-boot-spring-security-thymeleaf-form-login-example/)
* [Bootstrap Tutorial Reference](https://www.w3schools.com/bootstrap4/)
