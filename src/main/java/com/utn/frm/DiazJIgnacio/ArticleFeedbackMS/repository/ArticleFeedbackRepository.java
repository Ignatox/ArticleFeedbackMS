package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleFeedbackRepository extends MongoRepository<ArticleFeedback, String> {
    //Buscar feedbacks pendientes
    List<ArticleFeedback> findByStatus(String status);

    //Listar feedbacks por articulo (revisar logica)
    List<ArticleFeedback> findByArticleId(Long articleId);

    //Listar feedbacks por usuario (revisar logica)
    List<ArticleFeedback> findByUserId(Long userId);

    //Buscar articleFeedback por id
    ArticleFeedback findById(Long articleFeedbackId);
}
