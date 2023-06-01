package ru.netology.cloudstorage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {
   @JsonProperty("auth-token")
    private String authToken;

    public AuthToken(String authToken){
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String toString(){
        return authToken;
    }

}
