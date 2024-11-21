package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.controller;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.exceptions.ResourceNotFoundException;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleFeedbackService;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleSummaryService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/article-feedback")
public class ArticleFeedbackController {

    private final ArticleFeedbackService feedbackService;
    private final ArticleSummaryService summaryService;

    // Endpoint para obtener feedbacks pendientes o completados por usuario
    // Revisar query param
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
    public ResponseEntity<List<ArticleFeedback>> getFeedbacksByArticle(@PathVariable String articleId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByArticle(articleId));
    }

    // Endpoint para guardar un feedback llenado
    @PutMapping("/{articleFeedbackId}")
    //Falta autorización
    public ResponseEntity<ArticleFeedback> saveFeedback(@RequestParam String articleFeedbackId, @RequestBody ArticleFeedbackDTO feedbackDTO) {
        ArticleFeedback updatedFeedback = feedbackService.updateFeedback(articleFeedbackId, feedbackDTO);


        return ResponseEntity.ok(updatedFeedback);
    }
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //Endpoint para listar articleSummary de un articulo por ID
    //Hace falta autenticacion??

    @GetMapping("/{articleId}/summary")
    public ResponseEntity<ArticleSummary> getSummary(@PathVariable String articleId){
        return ResponseEntity.ok(summaryService.getSummaryByArticle(articleId));
    }




}
