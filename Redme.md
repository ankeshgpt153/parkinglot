#Parking Lot
Parking Lot Problem is java software to handle realtime automated ticketing system that allows my
customers to use my parking lot without human intervention.


##Requirements

* JDK 1.8
* Maven 3.*
* Junit 4.12

## Build and Execute
* Before build the project move to the folder/directory where pom file reside and open cmd/terminal
* Follow the steps defined in a sequence manner

####Build Using Maven
```
mvn clean install -DskipTests=true 
```
#### Run program
The project can be run as follows in one of the three ways :
```
1. ./parking_lot.sh <input_filepath>  The inputs commands are expected and taken from the file specified
2. ./parking_lot.sh This will start the program in interactive mode.
3. java -jar <jarfilename_withpath> <input_filepath>
```

#### Run Unit Test
```
mvn test
```

## License
Restricted