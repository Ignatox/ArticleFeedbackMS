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

      Optional<ArticleSummary> optionalSummary = summaryRepository.findByArticleId(articleId);
      //Si no encuentra un summary, crea uno nuevo inicializado en 0
      if(optionalSummary.isEmpty()){
          ArticleSummary newSummary = ArticleSummary.builder()
                  .articleId(articleId)
                  .totalLikes(0)
                  .totalDislikes(0)
                  .build();
          summaryRepository.save(newSummary);
          return newSummary;
      }
      //Si se encuentra un summary lo devuelve
      return optionalSummary.get();
          }
}
