package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class User {

    @JsonProperty("id")
    private String id;
    private String username;
    private String[] permissions;

    @JsonProperty("name")
    private String name;

    @JsonProperty("login")
    private String login;

    @JsonProperty("enabled")
    private boolean enabled;


    //metodo para parsear la respuesta JSON
    public static User fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
