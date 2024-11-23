package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.ResourceNotFoundException;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit.LikeUpdatePublisher;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleSummaryRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
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
    public List<ArticleFeedback> getFeedbacksByStatusAndUserId(String status, String userId) {
        if (status.equals("PENDIENTE")) {
            return feedbackRepository.findByStatusAndUserId("PENDIENTE", userId);
        }else
            return feedbackRepository.findByStatusAndUserId("COMPLETADO", userId);
    }

    // Crear o actualizar un feedback
    public ArticleFeedback saveFeedback(ArticleFeedback feedback) {
        feedback.setUpdatedAt(new java.util.Date());

        return feedbackRepository.save(feedback);
    }


    //Primera visión
    //HACER QUE GUARDE ANTES DE EMITIR EL MENSAJE
    public ArticleFeedback updateFeedback(String articleFeedbackId, ArticleFeedbackDTO feedbackDTO){

            // Buscar el feedback por ID
        Optional<ArticleFeedback> OptionalFeedback = feedbackRepository.findById(articleFeedbackId);

        //Verifico si existe
        ArticleFeedback feedback = OptionalFeedback.orElseThrow(() -> new ResourceNotFoundException("Feedback no encontrado con id:" + articleFeedbackId));


        // Actualizar los campos del feedback
        feedback.setComment(feedbackDTO.getComment());
        feedback.setLiked(feedbackDTO.getLiked());
        feedback.setStatus("COMPLETADO");
        feedback.setUpdatedAt(new java.util.Date());

        //Guardo el feedback primero
        ArticleFeedback savedFeedback = feedbackRepository.save(feedback);

        String articleId = savedFeedback.getArticleId();

        // envio mensaje a rabbit para actualizar summary
        likeUpdatePublisher.publishLikeUpdate(articleId, feedbackDTO.getLiked());

        //Guardo feedback
        return savedFeedback;
    }


    // Listar feedbacks por artículo
    public List<ArticleFeedback> getFeedbacksByArticle(String articleId) {
        //ACTUALIZAR CONTEO DE LIKES CON MENSAJE RABBIT
        //likeUpdatePublisher.publishLikeUpdate(articleId, null);

        //Actualizar conteo sincronicamente
        updateLikes(articleId, null);

        //Busco los feedbacks ya completados
        return feedbackRepository.findByStatusAndArticleId("COMPLETADO",articleId);
    }



    //Actualizar conteo de likes
    public void updateLikes(String articleId, Boolean liked){
        //Contar likes y dislikes asociados al articulo
        int totalLikes = feedbackRepository.countByArticleIdAndLikedTrue(articleId);
        int totalDislikes = feedbackRepository.countByArticleIdAndLikedFalse(articleId);

        //Defino like y dislike como enteros para incrementar el total correspondiente
        int like =  0;
        int dislike = 0;
        if(liked != null){
            if(liked){like = 1;}else{ dislike = 1;}
        }


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
        System.out.println("Actualizando conteo de likes");
        summaryRepository.save(summary);
    }
//    // Listar feedbacks por usuario
//    //Falta autenticacion
//    no hace falta este metodo
//    public List<ArticleFeedback> getFeedbacksByUser(String userId) {
//        return feedbackRepository.findByUserId(userId);
//    }

    }


