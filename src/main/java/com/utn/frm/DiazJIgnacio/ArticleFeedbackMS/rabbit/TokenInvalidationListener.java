package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security.TokenService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenInvalidationListener {
    @Autowired
    private TokenService tokenService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleInvalidation(String message){
        System.out.println("Mensaje recibido para invalidar token" + message);
        tokenService.invalidate(message); //Invalida el token en el cache local
    }


   /*  Estructura del mensaje en JSON
   {
        "correlation_id": "123123",
            "message": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }*/

}
