package com.teleapps.domain;



public class DeleteCognitoUserResponse {
    private String requestId;
    private String method;
    private DeleteCognitoUserRequest body;

  
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public DeleteCognitoUserRequest getBody() {
        return body;
    }

    public void setBody(DeleteCognitoUserRequest body) {
        this.body = body;
    }
}