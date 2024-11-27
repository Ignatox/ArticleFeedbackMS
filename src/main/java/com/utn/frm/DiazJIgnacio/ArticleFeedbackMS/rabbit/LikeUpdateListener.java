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
        String articleId = message.getArticleId();
        Boolean liked = message.getLiked();

        feedbackService.updateLikes(articleId, liked);
    }


}

