# BlogIt API
A blog API written in Java Spring Boot.

## Application Setup
### Prerequisites
* Java Development Kit(JDK) 17 installed
* Apache Maven installed
* MariaDB (or database of choice) installed

### Database Setup
* Create a database named blogit
    ```sql
    CREATE DATABASE blogit;
  
### Clone, Build, and Run the Application 
#### Clone the Repository
```shell
git clone https://github.com/bradley-mwangangi/blogit-api.git
```

```shell
cd blogit-api
```
<br>

#### Edit Application.yaml
   * Go to application.yaml and edit the datasource section to reflect your credentials <br>
   * Replace url and driver class-name to reflect your database of choice (if not MariaDB)
     ```yaml
       datasource:
       url: jdbc:mariadb://localhost:3306/blogit
       username: your-database-username
       password: your-database-password
       driver-class-name: org.mariadb.jdbc.Driver
#### Build the Project
   `mvn clean install` <br>
<br>

#### Run the Application
   `mvn spring-boot:run` <br>
This will start the API at: http://localhost:8080


## Application Usage
Below is a list of endpoints and the JSON structure in which to submit data.

### User Signup
**POST:** <br>
`http://localhost:8080/api/v1/auth/signup`
```json
{
  "first_name": "jane",
  "last_name": "doe",
  "email": "janedoe@example.com",
  "password": "jdoe"
}
```
This registers the user with roles USER and AUTHOR by default

### User Authentication
**POST:** <br>
`http://localhost:8080/api/v1/auth/authenticate`
```json
{
  "email": "janedoe@example.com",
  "password": "jdoe"
}
```
Will set Authorization and Refresh-Token headers

### User Profile
**GET:** <br>
`http://localhost:8080/api/v1/users/profile` <br>
Pass Authorization header as Bearer token alongside the request

### Create Article
**POST:** <br>
`http://localhost:8080/api/v1/articles/create-article`
```json
{
  "title": "article title",
  "content": "article content",
  "tags": ["tag 1", "tag 2"]
}
```
Pass Authorization header as Bearer token alongside the request

### Get Article by Article ID
**GET:** <br>
`http://localhost:8080/api/v1/articles/articleId` <br>
Replace articleId with actual article id (number). For example, 1 for article with id 1


### Get Article Highlights
**GET:** <br>
`http://localhost:8080/api/v1/articles/highlights` <br>
This loads the first 10 articles by date createdAt
