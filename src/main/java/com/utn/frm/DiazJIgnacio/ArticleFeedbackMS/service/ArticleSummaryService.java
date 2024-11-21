package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleSummaryService {
    private final ArticleSummaryRepository summaryRepository;

    public ArticleSummaryService(ArticleSummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }


    // Crear o actualizar un resumen
    public ArticleSummary saveSummary(ArticleSummary summary) {
        return summaryRepository.save(summary);
    }

    // Obtener resumen por artículo
    public ArticleSummary getSummaryByArticle(String articleId) {
               return summaryRepository.findByArticleId(articleId)
                       .orElseGet(() -> ArticleSummary.builder()
                               .articleId(articleId)
                               .totalDislikes(0)
                               .totalLikes(0)
                               .build());

          }
}
