package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.controller;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleFeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/article-feedback")
public class ArticleFeedbackController {

    private final ArticleFeedbackService feedbackService;

    public ArticleFeedbackController(ArticleFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Endpoint para obtener feedbacks pendientes o completados por usuario
    //PRIMERA REVISIÓN, FALTA AUTORIZACIÓN
    @GetMapping("/user")
    public ResponseEntity<List<ArticleFeedback>> getFeedbacksByStatus(
        //Faltaria autorización
        @RequestParam String status) {

        return ResponseEntity.ok(feedbackService.getFeedbacksByStatus(status));
    }

    // Endpoint para listar feedbacks por artículo
    //PRIMERA REVISIÓN, NO HACE FALTA AUTORIZACIÓN, FALTARIA ANALIZAR ARTICLE ID
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ArticleFeedback>> getFeedbacksByArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByArticle(articleId));
    }

    // Endpoint para guardar un feedback llenado
    @PutMapping("/{articleFeedbackId}")
    //Falta autorización
    public ResponseEntity<ArticleFeedback> saveFeedback(@RequestParam Long articleFeedbackId, @RequestBody ArticleFeedbackDTO feedbackDTO) {
        ArticleFeedback updatedFeedback = feedbackService.updateFeedback(articleFeedbackId, feedbackDTO);


        return ResponseEntity.ok(updatedFeedback);
    }


    // Endpoint para listar feedbacks por usuario
    //BORRADO
    //@GetMapping("/user/{userId}")
    //public ResponseEntity<List<ArticleFeedback>> getFeedbacksByUser(@PathVariable Long userId) {
      //  return ResponseEntity.ok(feedbackService.getFeedbacksByUser(userId));
    //}



}
