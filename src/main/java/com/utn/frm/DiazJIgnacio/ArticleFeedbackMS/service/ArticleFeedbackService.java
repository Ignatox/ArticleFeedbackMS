package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.exceptions.ResourceNotFoundException;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit.LikeUpdatePublisher;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleSummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleFeedbackService {

    @Autowired
    private final ArticleFeedbackRepository feedbackRepository;
    @Autowired
    private final ArticleSummaryRepository summaryRepository;

    private final LikeUpdatePublisher likeUpdatePublisher;



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
    public ArticleFeedback updateFeedback(String articleFeedbackId, ArticleFeedbackDTO feedbackDTO){
            // Buscar el feedback por ID
        Optional<ArticleFeedback> OptionalFeedback = feedbackRepository.findById(articleFeedbackId);

        //Verifico si existe
        ArticleFeedback feedback = OptionalFeedback.orElseThrow(() -> new ResourceNotFoundException("Feedback no encontrado con id:" + articleFeedbackId));

        String articleId = feedback.getArticleId();


        // Actualizar los campos del feedback
        feedback.setComment(feedbackDTO.getComment());
        feedback.setLiked(feedbackDTO.getLiked());
        feedback.setStatus("COMPLETADO");
        feedback.setUpdatedAt(new java.util.Date());

        // envio mensaje a rabbit para actualizar summary
        likeUpdatePublisher.publishLikeUpdate(articleId, feedbackDTO.getLiked());

        //Guardo feedback
        return feedbackRepository.save(feedback);
    }


    // Listar feedbacks por artículo
    public List<ArticleFeedback> getFeedbacksByArticle(String articleId) {
        //ACTUALIZAR CONTEO DE LIKES??
        return feedbackRepository.findByArticleId(articleId);
    }

    // Listar feedbacks por usuario
    //Falta autenticacion
    public List<ArticleFeedback> getFeedbacksByUser(String userId) {
        return feedbackRepository.findByUserId(userId);
    }

    //Actualizar conteo de likes
    public void updateLikes(String articleId, Boolean liked){
        //Contar likes y dislikes asociados al articulo
        int totalLikes = feedbackRepository.countByArticleIdAndLikedTrue(articleId);
        int totalDislikes = feedbackRepository.countByArticleIdAndLikedFalse(articleId);

        //Defino like y dislike como enteros para incrementar el total correspondiente
        int like =  0;
        int dislike = 0;
        if(liked){like = 1;}else{ dislike = 1;}

        // Buscar o crear un resumen para el artículo
        ArticleSummary summary = summaryRepository.findByArticleId(articleId).orElse(null);
        if(summary == null) {
        summary = ArticleSummary.builder()
                .articleId(articleId)
                .totalLikes(like)
                .totalDislikes(dislike)
                .build();

        }else {
            //ACTUALIZAR SUMMARY EXISTENTE
            summary.setTotalLikes(totalLikes + like);
            summary.setTotalDislikes(totalDislikes + dislike);
        }
        // Guardar el resumen actualizado
        summaryRepository.save(summary);
    }

    }


