# mtd-learning-mysql
A small demo project using Spring Data JPA, MySQL, Docker and Swagger.

To run, make sure you have Docker Desktop installed.

After cloning the project, in the project root directory run the following commands:
- mvn clean package -DskipTests
- docker build --tag=mtd-sql:latest .
- docker-compose up

To stop, hit control+'C'

Currect endpoints you can test:
- GET - returns all foo
- GET - returns the first foo with a given name
- POST - add your own foo to the collection
- PUT - update your foo by name
- DELETE - your foo by name

Note: the easiest way to test this app is by using the Swagger UI: http://localhost:8080/swagger-ui.html 

| URL              | Request Type | Description                          |
| -------------    |--------------|--------------------------------------|
| .../foo          | GET          | Gets all the foos from the foo table |
| .../foo/{name}   | GET          | Gets a specific foo by name          |
| .../foo          | POST         | Let's you add a foo                  |
| .../foo/{name}   | PUT          | Update foo by name                   |
| .../foo/{name}   | DELETE       | Delete foo by name                   |

Example foos:
```
  {
    "name": "dragon",
    "legs": 2,
    "canFly": true
  }
  ```
  ```
  {
    "name": "doggo",
    "legs": 4,
    "canFly": false
  }
  ```