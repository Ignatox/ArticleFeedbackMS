package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@Builder
@AllArgsConstructor
public class LikeUpdatePublisher {

    private final RabbitTemplate rabbitTemplate;



    public void publishLikeUpdate(String articleId, Boolean liked) {
        LikeUpdateMessage message = new LikeUpdateMessage(articleId, liked);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.UPDATE_LIKES_EXCHANGE,
                RabbitMQConfig.UPDATE_LIKES_QUEUE,
                message,

                m -> {
                    m.getMessageProperties().setContentType("application/json");
                    return m;
                }
        );
        System.out.println("Mensaje enviado a update_likes_queue: " + message);

    }
}




