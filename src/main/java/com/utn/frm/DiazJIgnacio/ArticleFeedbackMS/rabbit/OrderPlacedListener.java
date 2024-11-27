package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleFeedbackService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Builder
public class OrderPlacedListener {

    private final ObjectMapper objectMapper;
    private final ArticleFeedbackService feedbackService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_PLACED_QUEUE)

    public void handleOrderPlaced(OrderPlacedEvent event){
        try {
            String userId = event.getUserId();
            String articleId = event.getArticleId();
          feedbackService.saveFeedback(userId, articleId);
            System.out.println("Feedback creado para artículo: " + event.getArticleId());
        } catch (Exception e) {
            System.err.println("Error al procesar el evento order_placed: " + e.getMessage());
        }
    }


}
