package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.SimpleError;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.UnauthorizedError;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.ExpiringMap;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@Service
public class TokenService {
    final ExpiringMap<String, User> cache = new ExpiringMap<>(60 * 60, 60 * 5); // 1 hora de vida, limpieza cada 5 min.

    private Environment env;


    public TokenService(Environment env){
        this.env = env;
    }

    public void validate(String token) throws SimpleError{
        if(token == null || token.isEmpty()){
            //Test
            System.out.println("Fallo en la request a authMS: Token nulo");
            throw new SimpleError(401, "Unauthorized");
        }
        User cachedUser = cache.get(token);
        if (cachedUser != null) {
            return; // Usuario en cache, válido
        }

        User user = retrieveUser(token);
        if (user == null) {
            //Test
            System.out.println("Fallo en la request a authMS: No se pudo recuperar el user");
            throw new SimpleError(401, "Unauthorized");
        }
        cache.put(token, user);
    }

    public User getUser(String token) throws SimpleError{
        if(token == null || token.isEmpty()){
        throw new UnauthorizedError();
        }
        User cachedUser = cache.get(token);
        if(cachedUser != null){
            System.out.println("User recuperado del cache");
            return cachedUser;
        }

        User user = retrieveUser(token);
        if(user == null){
            throw new UnauthorizedError();
        }
        cache.put(token, user);
            return user;
        }

        //Metodo para remover del cache al usuario
    public void invalidate(String token){
        cache.remove(token);
    }

    private User retrieveUser(String token){
        String authUrl = env.getProperty("security.securityServerUrl") + "/v1/users/current";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(authUrl);
        request.addHeader("Authorization", token);

        try{
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode()!= 200){
                System.out.println("Respuesta de AuthMS:" + (response.getStatusLine().getStatusCode()));
                return null;
            }
            HttpEntity responseEntity = response.getEntity();
            if(responseEntity == null){
                return null;
            }

            String body = EntityUtils.toString(responseEntity);
            return User.fromJson(body);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



}
