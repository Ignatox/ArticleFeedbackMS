package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleSummaryRepository extends MongoRepository<ArticleSummary, String> {

    //Buscar resumen por ID de articulo
    Optional<ArticleSummary> findByArticleId(String articleId);
    


}
