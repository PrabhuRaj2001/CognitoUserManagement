package com.teleapps.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.teleapps.cognitocontroller.CognitoConfirmHandler;
import com.teleapps.cognitocontroller.CognitoSignUpHandler;
import com.teleapps.cognitocontroller.DeleteCognitoUserHandler;

import java.util.Map;

public class Handler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
        if (input instanceof Map) {
            Map<String, Object> inputMap = (Map<String, Object>) input;
            String method = (String) inputMap.get("method");

            if ("signup".equals(method)) {
                CognitoSignUpHandler signUpLambda = new CognitoSignUpHandler();
                return signUpLambda.handleRequest(inputMap, context); // Passing the entire input object
            } else if ("deleteuser".equals(method)) {
                DeleteCognitoUserHandler deleteCognitoUser = new DeleteCognitoUserHandler();
                return deleteCognitoUser.handleRequest(inputMap, context); // Passing the entire input object
            } else if ("confirm".equals(method)) { // Handle the "confirm" method
                CognitoConfirmHandler confirmHandler = new CognitoConfirmHandler();
                return confirmHandler.handleConfirmRequest(inputMap, context); // Passing the entire input object
            } else {
                throw new RuntimeException("Invalid method specified");
            }
        } else {
            throw new RuntimeException("Input is not of type Map");
        }
    }
}
