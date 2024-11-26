package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Builder
public class OrderPlacedListener {
    private final ArticleFeedbackRepository feedbackRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.ORDER_PLACED_QUEUE)
    //String message
    public void handleOrderPlaced(OrderPlacedEvent event){
        try {
            // Deserializar el mensaje JSON a un objeto OrderPlacedEvent

            // Crear un nuevo feedback
            ArticleFeedback feedback = new ArticleFeedback();
            feedback.setUserId(event.getUserId());
            feedback.setArticleId(event.getArticleId());
            feedback.setStatus("PENDIENTE");
            feedback.setCreatedAt(new java.util.Date());

            // Guardar el feedback en la base de datos
            feedbackRepository.save(feedback);

            System.out.println("Feedback creado para artículo: " + event.getArticleId());
        } catch (Exception e) {
            System.err.println("Error al procesar el evento order_placed: " + e.getMessage());
        }
    }


}
