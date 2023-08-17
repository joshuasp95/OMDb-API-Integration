# OMDB API Integration Project

Welcome to the OMDB API Integration Project! This endeavor serves as an illustrative example of seamlessly acquiring and
storing movie data from the illustrious OMDB (Open Movie Database) API into your local database. We invite you to join
this coding journey and witness the conjuration of magic within your code!

## Preview 

### You can check it here http://www.joshuasainzweb.com:8090/myapp

![PROJECT IMAGE CRUD PREVIEW](https://i.imgur.com/iuGdsK6.png)

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Work in Progress](#work-in-progress)

## Getting Started

### Prerequisites

Before embarking on this transformative odyssey, ensure you possess the following instruments in your toolkit:

Technologies Used

![Java](https://img.icons8.com/color/48/000000/java-coffee-cup-logo--v1.png) Java 8+
![Spring Boot](https://img.icons8.com/color/48/000000/spring-logo.png) Spring Boot
![MySQL](https://img.icons8.com/color/48/000000/mysql-logo.png) MySQL
![Git](https://img.icons8.com/color/48/000000/git.png) Git

### Installation

1. **Clone the Repository:**

   ```sh
   git clone https://github.com/joshuasp95/omdb-api-integration.git
   ```

2. **Navigate to the Project Directory:**

   ```sh
   cd omdb-api-integration
   ```

3. **Build and Execute the Project:**

   ```sh
   ./mvnw spring-boot:run
   ```
Also you must verify application properties match the proper configuration. 


    Example of application.properties file
    
    # Port number for the server to listen on
    server.port=port (e.g 8090)

    '''
    # Context path for the application (myapp) endpoints
    server.servlet.context-path=/myapp
    '''
    #DB credentials
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    
    # If you want set the active profile for the application (dev or pdn)
    spring.profiles.active=dev
    
    #API KEY for omdb
    omdb.api.key=api_key
    omdb.api.url=http://www.omdbapi.com
    
    #Live reload for view changes
    spring.devtools.restart.enabled=true
    spring.devtools.restart.additional-paths=src/main/resources


## Usage

1. **Home Base:**
   Check the available Harry Potter movies

2. **Filter:**
   Use filters to reduce the movies based on your requirements

3. **Personal Rating:**
   Add your own personal rating to each movie

## Contributing

Embark on a collaborative journey by contributing your own enchantments to this project! Take the opportunity to fork
the repository, introduce your captivating features, and initiate a pull request.

## Future Enhancements

- Integrate user authentication for personalized movie ratings.

