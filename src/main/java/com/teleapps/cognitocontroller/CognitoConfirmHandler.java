package com.teleapps.cognitocontroller;


import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import java.util.*;

public class CognitoConfirmHandler {

    private final String clientId = System.getenv("client_id");

    public Map<String, Object> handleConfirmRequest(Object input, Context context) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (input instanceof Map) {
                Map<String, Object> inputMap = (Map<String, Object>) input;
                Map<String, Object> body = (Map<String, Object>) inputMap.get("body");
                String requestId = (String) inputMap.get("requestId");

                String userName = (String) body.get("userName");
                String confirmationCode = (String) body.get("confirmationCode");

                if (isValidInputForConfirmation(userName, confirmationCode)) {
                    CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.create();

                    ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                            .username(userName)
                            .clientId(clientId)
                            .confirmationCode(confirmationCode)
                            .build();

                    ConfirmSignUpResponse confirmSignUpResponse = identityProviderClient.confirmSignUp(confirmSignUpRequest);

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userName", userName);
                    responseBody.put("confirmationStatus", "confirmed");

                    Map<String, Object> output = new HashMap<>();
                    output.put("requestId", requestId);
                    output.put("method", "confirm");
                    output.put("body", responseBody);

                    return output;
                } else {
                    throw new RuntimeException("Invalid input parameters for confirmation");
                }
            } else {
                throw new RuntimeException("Input is not of type Map");
            }
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error confirming user", e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }
    }

    private boolean isValidInputForConfirmation(String userName, String confirmationCode) {
        return userName != null && !userName.isEmpty() &&
               confirmationCode != null && !confirmationCode.isEmpty();
    }
}
