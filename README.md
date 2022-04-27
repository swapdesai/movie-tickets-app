# Sportsbet Movie Tickets Application

The Sportsbet movie tickets Application console application has been built using SpringBoot framework which accelerates application development using Spring starter projects. The application has been primarily written in JAVA as the programming language.

The app has been configured to read the config properties from the `application.yml` file maintained under `src/main/resources` folder. These properties can be overridden during run time as well.
<br />
<br />
The sample below shows the config for costs and discounts

```
cost:
  adult: 25
  teen: 12
  children: 5

discount:
  children-percentage: 25
  children-quantity: 3
  senior-percentage: 30
```

## Build
Build the application using the following command: 
<br />
`./gradlew clean build`


## Run
Run the application using the following command: 
<br />
`./gradlew bootRun`

## Improvements
1. Enable code coverage tool like Jacoco
2. Integration with source code analyzer tools such as `findbugs`, `pmd`, etc.
3. Need more Unit and Integration tests
4. More detailed logging for Troubleshooting
5. Running inside a Docker container

## Assumption
* Response transactionId is to be set one from the request
