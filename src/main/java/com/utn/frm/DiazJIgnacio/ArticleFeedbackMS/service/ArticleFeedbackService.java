package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.ResourceNotFoundException;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit.LikeUpdatePublisher;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleSummaryRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.UnauthorizedError;
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
    @Autowired
    private final LikeUpdatePublisher likeUpdatePublisher;



    //Obtener articleFeedbacks PENDIENTES/COMPLETADOS de un usuario
    public List<ArticleFeedback> getFeedbacksByStatusAndUserId(String status, String userId) {
        return feedbackRepository.findByStatusAndUserId(status, userId);

    }

    // Crear feedback
    public void saveFeedback(String userId, String articleId) {
        // Crear un nuevo feedback
        ArticleFeedback feedback = new ArticleFeedback();
        feedback.setUserId(userId);
        feedback.setArticleId(articleId);
        feedback.setStatus("PENDIENTE");
        feedback.setCreatedAt(new java.util.Date());

        feedbackRepository.save(feedback);
    }

    public ArticleFeedback updateFeedback(String userId,String articleFeedbackId, ArticleFeedbackDTO feedbackDTO){

        Optional<ArticleFeedback> OptionalFeedback = feedbackRepository.findById(articleFeedbackId);

        if(OptionalFeedback.isEmpty()){
            throw new ResourceNotFoundException("Feedback no encontrado con id:" + articleFeedbackId);
        }

        ArticleFeedback feedback = OptionalFeedback.get();

        //Verificar si el usuario que llena, es el propietaro del articleFeedback
        System.out.println("Verificando si el feedback corresponde a este usuario");
         if(!feedback.getUserId().equals(userId)){
            throw new UnauthorizedError("El usuario no tiene permiso para actualizar este feedback");
        }

         // Actualizar los campos del feedback
        feedback.setComment(feedbackDTO.getComment());
        feedback.setLiked(feedbackDTO.getLiked());
        feedback.setStatus("COMPLETADO");
        feedback.setUpdatedAt(new java.util.Date());

        System.out.println("Guardando");
        //Guardar el feedback antes del mensaje RabbitMQ
        ArticleFeedback savedFeedback = feedbackRepository.save(feedback);

        String articleId = savedFeedback.getArticleId();


        //Envio de mensaje a RabbitMQ para actualizar summary

        try {
            likeUpdatePublisher.publishLikeUpdate(articleId, feedbackDTO.getLiked());
            System.out.println("Publicando mensaje em RABBITMQ");
        }catch (Exception e){
            System.out.println("Error al publicar en rabbit MQ" + e.getMessage());
            e.printStackTrace();
        }


        return savedFeedback;
    }


    // Listar articleFeedbacks por artículo
    public List<ArticleFeedback> getFeedbacksByArticle(String articleId) {
       //Actualizar conteo sincronicamente
        updateLikes(articleId, null);
        System.out.println("Listando feedbacks COMPLETADOS para el article:" + articleId);
        //Buscar los feedbacks ya completados
        String status = "COMPLETADO";
        return feedbackRepository.findByStatusAndArticleId(status,articleId);
    }



    //Actualizar conteo de likes
    public void updateLikes(String articleId, Boolean liked){
        //Contar likes y dislikes asociados al artículo
        int totalLikes = feedbackRepository.countByArticleIdAndLikedTrue(articleId);
        int totalDislikes = feedbackRepository.countByArticleIdAndLikedFalse(articleId);

        //Definición de like y dislike como enteros iniciales en 0 para incrementar
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
            //Actualizar el summary existente con nuevo conteo
            summary.setTotalLikes(totalLikes + like);
            summary.setTotalDislikes(totalDislikes + dislike);
        }

        System.out.println("Actualizando conteo de likes");
        summaryRepository.save(summary);
    }

    }


