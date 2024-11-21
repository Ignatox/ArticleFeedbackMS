package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security.TokenService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenInvalidationListener {
   /* @Autowired
    private TokenService tokenService;

    @RabbitListener(queues = "auth.invalidate")
    public void handleInvalidation(String token){
        tokenService.invalidate(token);
    }*/
}
