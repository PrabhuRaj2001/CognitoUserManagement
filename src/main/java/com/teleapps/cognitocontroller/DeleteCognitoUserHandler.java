package com.teleapps.cognitocontroller;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

import java.util.HashMap;
import java.util.Map;

public class DeleteCognitoUserHandler {

	private static final String USER_POOL_ID = System.getenv("pool_id");


    public Map<String, Object> handleRequest(Object input, Context context) {
     

        try {
            if (input instanceof Map) {
                Map<String, Object> inputMap = (Map<String, Object>) input;
               
                Map<String, Object> body = (Map<String, Object>) inputMap.get("body");
                String requestId = (String) inputMap.get("requestId");
                String method = (String) inputMap.get("method");
                if ("deleteuser".equals(method)) {
                    String usernameToDelete = (String) body.get("username");

                    CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                            .region(Region.AP_SOUTH_1)
                            .build();

                    AdminDeleteUserRequest request = AdminDeleteUserRequest.builder()
                            .userPoolId(USER_POOL_ID)
                            .username(usernameToDelete)
                            .build();

                    AdminDeleteUserResponse response = cognitoClient.adminDeleteUser(request);

                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("username", usernameToDelete);

                    Map<String, Object> output = new HashMap<>();
                    output.put("requestId", requestId);
                    output.put("method", "delete");
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
            throw new RuntimeException("Error deleting user", e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing request", e);
        }
    }
}
