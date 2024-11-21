package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleSummaryRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleFeedbackService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Builder
@AllArgsConstructor
public class LikeUpdateListener {
    private final ArticleSummaryRepository summaryRepository;
    private final ArticleFeedbackService feedbackService;
    @RabbitListener(queues = RabbitMQConfig.UPDATE_LIKES_QUEUE)
    public void handleLikeUpdate(LikeUpdateMessage message) {
        // Actualizar el resumen del artículo
        feedbackService.updateLikes(message.getArticleId(), message.getLiked());
    }


        /*Optional<ArticleSummary> optionalSummary = summaryRepository.findByArticleId(message.getArticleId());
        ArticleSummary summary;*/



       /* if (optionalSummary.isPresent()) {
            summary = optionalSummary.get();
            summary.setTotalLikes(message.getLikes());
            summary.setTotalDislikes(message.getDislikes());
        } else {
            summary = new ArticleSummary();
            summary.setArticleId(message.getArticleId());
            summary.setTotalLikes(message.getLikes());
            summary.setTotalLikes(message.getDislikes());
        }

        summaryRepository.save(summary);
        System.out.println("Resumen actualizado para artículo: " + message.getArticleId());*/

}

