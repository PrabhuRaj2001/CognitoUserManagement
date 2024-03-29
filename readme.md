# Cognito User Management

This task provides AWS Lambda functions to manage user sign-up, confirmation, and deletion in Amazon Cognito. It uses the AWS SDK for Java v2 and is built using Maven.

## Table of Contents

- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Environment Variables](#environment-variables)
- [Setup](#setup)
- [Building the Project](#building-the-project)
- [Deploying to AWS Lambda](#deploying-to-aws-lambda)
- [Usage](#usage)
  - [Sign Up User](#sign-up-user)
  - [Confirm User](#confirm-user)
  - [Delete User](#delete-user)
- [Error Handling](#error-handling)
- [License](#license)
- [Creator](#creator)

## Project Structure

The project has the following directory structure:

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── teleapps/
│   │   │   │   │   ├── lambda/
│   │   │   │   │   │   ├── Handler.java       # Main Lambda handler to route requests
│   │   │   │   │   ├── cognitocontroller/
│   │   │   │   │   │   ├── CognitoSignUpHandler.java   # Handles user sign-up requests
│   │   │   │   │   │   ├── CognitoConfirmHandler.java  # Handles user confirmation requests
│   │   │   │   │   │   ├── DeleteCognitoUserHandler.java # Handles user deletion requests
│   │   │   │   │   ├── domain/
│   │   │   │   │   │   ├── DeleteCognitoUserRequest.java # Represents request body for user deletion
│   │   │   │   │   │   ├── DeleteCognitoUserResponse.java # Represents response body for user deletion
│   │   │   │   │   │   ├── SignUpRequestData.java  # Represents request body for user sign-up
├── pom.xml  
└── readme.md  
```

### Directory and File Descriptions:

- `src/main/java/com/teleapps/lambda`: Contains the main Lambda handler (`Handler.java`) responsible for routing requests to specific handler classes.
  
- `src/main/java/com/teleapps/cognitocontroller`: Contains handler classes for Cognito operations:
  - `CognitoSignUpHandler.java`: Handles user sign-up requests.
  - `CognitoConfirmHandler.java`: Handles user confirmation requests.
  - `DeleteCognitoUserHandler.java`: Handles user deletion requests.
  
- `src/main/java/com/teleapps/domain`: Contains POJOs (Plain Old Java Objects) representing request and response bodies:
  - `DeleteCognitoUserRequest.java`: Represents the request body for user deletion.
  - `DeleteCognitoUserResponse.java`: Represents the response body for user deletion.
  - `SignUpRequestData.java`: Represents the request body for user sign-up.

- `pom.xml`: Maven configuration file specifying project dependencies and build settings.

## Dependencies

The project uses the following dependencies:

- **Jackson Databind**: For JSON handling.
- **AWS SDK v2**: 
  - `cognitoidentityprovider`: For Cognito operations.
  - `sdk-core`: Core AWS SDK v2 dependency.
  - `lambda`: For AWS Lambda functions.
- **AWS Lambda Java Events**: For AWS Lambda Java events.
- **AWS Lambda Java Core**: For AWS Lambda Java core functionalities.

## Environment Variables

To run the Lambda functions, the following environment variables must be set:

- `client_id`: The client ID of the Cognito user pool.
- `pool_id`: The user pool ID of the Cognito user pool.

## Setup

1. Ensure you have Maven installed.
2. Set up your AWS credentials.
3. Set the required environment variables (`client_id` and `pool_id`).

## Building the Project

Run the following Maven command to build the project:

```bash
mvn clean package
```

This will generate a shaded JAR in the `target` directory.

## Deploying to AWS Lambda

You can deploy the generated shaded JAR in the `target` directory to AWS Lambda using the AWS Management Console, AWS CLI, or any CI/CD pipeline.

## Usage

### Sign Up User

To sign up a user, send a POST request with the following JSON body:

```json
{
  "method": "signup",
  "requestId": "example request id",
  "body": {
    "userName": "exampleUser",
    "password": "examplePassword",     # Password must contain at least one uppercase letter, one lowercase letter, one number, and one symbol.
    "email": "example@example.com"
  }
}
```

### Confirm User

To confirm a user, send a POST request with the following JSON body:

```json
{
  "method": "confirm",
  "requestId": "123",
  "body": {
    "userName": "exampleUser", 
    "confirmationCode": "confirmation mail OTP"  # enter your email OTP
  }
}
```

### Delete User

To delete a user, send a POST request with the following JSON body:

```json
{
  "method": "deleteuser",
  "requestId": "123",
  "body": {
    "username": "exampleUser"
  }
}
```

## Error Handling

The Lambda functions handle various error scenarios and throw `RuntimeException` with appropriate error messages.


## Creator

This Task was created by Prabhu Raj M
