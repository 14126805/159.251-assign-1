# 159251 Assignment 1
## Task 1: Shell scripting
Markdown format todo list files are in the `./data/files` directory
You can save the scripts you write in this directory.

## Task 2: Development
### Running the web application
You will need to install [Apache Maven](https://maven.apache.org/) to manage the project.

Once you have Maven installed, you can run the development web server with the following commands:

```
cd <project_directory>
mvn clean; mvn test
mvn exec:java -Dexec.mainClass="nz.ac.massey.cs.Start" -Dexec.classpathScope=test
```

If the commands execute successfully, you can visit the application on your web browser at `http://localhost:8080`
