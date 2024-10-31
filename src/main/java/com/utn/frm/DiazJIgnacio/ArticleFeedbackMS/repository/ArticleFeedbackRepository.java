package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleFeedbackRepository extends MongoRepository<ArticleFeedback, String> {
}
