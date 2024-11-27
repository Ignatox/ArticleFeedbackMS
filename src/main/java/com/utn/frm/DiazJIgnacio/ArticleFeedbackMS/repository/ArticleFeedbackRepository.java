package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleFeedbackRepository extends MongoRepository<ArticleFeedback, String> {
    //Buscar feedbacks pendientes
    List<ArticleFeedback> findByStatusAndUserId(String status, String userId);

    //Listar feedbacks por articulo
    List<ArticleFeedback> findByStatusAndArticleId(String status,String articleId);

    //Listar feedbacks por usuario
    List<ArticleFeedback> findByUserId(String userId);

    //Contadores de likes y dislikes de un articulo
    int countByArticleIdAndLikedTrue(String articleId);
    int countByArticleIdAndLikedFalse(String articleId);
}
