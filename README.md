# Athlete_DB
This project demonstrates basic object oriented concepts and interfacing with a mySQL database using java.
## setup
1. mySQL setup

In order to execute the source code, you must first setup a locally hosted mySQL server. Downloads for mySQL workbench can be found @ https://dev.mysql.com/downloads/

- Follow the setup wizard to configure the server. NOTE: avoid using '#' and '!' characters in your root password as this will interfere with the config file.
- Once your server is online and you have access to the workbench, open and execute the athleteDB.sql file found in the sql folder. This will build the necessary schema along with essential stored procedures.
- Your 'Schemas tab should look like this:
<img src="https://user-images.githubusercontent.com/71242338/131948832-af7ab6ee-bf7d-4e69-9090-9eb7860aa264.PNG" width="150">

2. Config
- Based on the port # that you chose to host your sql server on, you will need to find the localhost address by which your database is reachable. This can be found by right clicking your server under the 'MySQL connections' page and selecting 'copy jdbc connection string to clipboard'. You will then need to trim the '?user=root' suffix and extend the path with 'athletes' to indicate the schema.
- Now, navigate to the config folder and populate the fields within 'config.properties'.
- This program uses mySQL's ConnectorJ and the java.sql API in order to establish connections with its database. Ensure that the .jar file in the lib folder is recognized and/or registered by your execution environment.
3. Execution
- At this point all prerequisites should have been met and the program is ready to execute. 
- Compile all files within the source folder and execute Main.java. 
- A successful connection should look like this:
<img src="https://user-images.githubusercontent.com/71242338/131950544-1a816596-c9c1-4314-84cd-9d6daf662f36.PNG" width="250">


Thank you for taking the time to view my project!

## Takeaways

While working on this project I have learned about organizing object oriented code. I also familiarized myself with sql syntax, which was unique (and straightforward) in its declarative nature. I am beginning to understand the concept of encapsulation and why it is important on a larger scale. In future projects, I will look to incorporate better object structure through inheritance and interfaces. Consistent with previous projects, I strived for modularity in my code to make it both readable and modifiable.

## future additions

Some functionalities that could be added in the future are:
- Extensive record keeping for previous athlete lifts
- More constraints on the cache for extended sessions involving many queries
- A GUI
- A graphing function to display a visual representation of growth over time.
- More abstraction within codebase (separation of data layer and middleware)

Ben Jordan - Summer of 2021
