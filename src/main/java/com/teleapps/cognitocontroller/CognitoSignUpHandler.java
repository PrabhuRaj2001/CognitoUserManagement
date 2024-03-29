package com.teleapps.cognitocontroller;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

import java.util.*;
import java.util.regex.Pattern;

public class CognitoSignUpHandler {

    private final String clientId = System.getenv("client_id");

    public Map<String, Object> handleRequest(Object input, Context context) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (input instanceof Map) {
                Map<String, Object> inputMap = (Map<String, Object>) input;
                String method = (String) inputMap.get("method");
                Map<String, Object> body = (Map<String, Object>) inputMap.get("body");
                String requestId = (String) inputMap.get("requestId");

                if ("signup".equals(method)) {
                    String userName = (String) body.get("userName");
                    String password = (String) body.get("password");
                    String email = (String) body.get("email");

                    // Validate password
                    if (!isValidPassword(password)) {
                        throw new RuntimeException("Password must be at least 8 characters long, including at least one symbol, one number, one uppercase letter, and one lowercase letter.");
                    }

                    CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.create();

                    AttributeType userAttrs = AttributeType.builder()
                            .name("email")
                            .value(email)
                            .build();

                    List<AttributeType> userAttrsList = new ArrayList<>();
                    userAttrsList.add(userAttrs);

                    SignUpRequest signUpRequest = SignUpRequest.builder()
                            .userAttributes(userAttrsList)
                            .username(userName)
                            .clientId(clientId)
                            .password(password)
                            .build();

                    SignUpResponse signUpResponse = identityProviderClient.signUp(signUpRequest);

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userName", userName);
                    responseBody.put("email", email);

                    Map<String, Object> output = new HashMap<>();
                    output.put("requestId", requestId);
                    output.put("method", "signup");
                    output.put("body", responseBody);

                    return output;
                } else {
                    throw new RuntimeException("Invalid method specified");
                }
            } else {
                throw new RuntimeException("Input is not of type Map");
            }
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error signing up user", e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        return pattern.matcher(password).matches();
    }
}
