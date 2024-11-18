package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleFeedbackService {
    private final ArticleFeedbackRepository feedbackRepository;

    public ArticleFeedbackService(ArticleFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Obtener feedbacks por estado
    public List<ArticleFeedback> getFeedbacksByStatus(String status) {
        if (status.equals("PENDIENTE")) {
            return feedbackRepository.findByStatus("PENDIENTE");
        }else
            return feedbackRepository.findByStatus("COMPLETADO");
    }

    // Crear o actualizar un feedback
    public ArticleFeedback saveFeedback(ArticleFeedback feedback) {
        feedback.setUpdatedAt(new java.util.Date());

        return feedbackRepository.save(feedback);
    }


    //Falta autenticación?
    //Primera visión
    public ArticleFeedback updateFeedback(Long articleFeedbackId, ArticleFeedbackDTO feedbackDTO){
            // Buscar el feedback por ID
        ArticleFeedback feedback = feedbackRepository.findById(articleFeedbackId);

        // Actualizar los campos del feedback
        feedback.setComment(feedbackDTO.getComment());
        feedback.setLiked(feedbackDTO.getLiked());
        feedback.setStatus("COMPLETADO");
        feedback.setUpdatedAt(new java.util.Date());

        //Guardo feedback
        return feedbackRepository.save(feedback);
    }


    // Listar feedbacks por artículo
    public List<ArticleFeedback> getFeedbacksByArticle(Long articleId) {
        return feedbackRepository.findByArticleId(articleId);
    }

    // Listar feedbacks por usuario
    public List<ArticleFeedback> getFeedbacksByUser(Long userId) {
        return feedbackRepository.findByUserId(userId);
    }


}
