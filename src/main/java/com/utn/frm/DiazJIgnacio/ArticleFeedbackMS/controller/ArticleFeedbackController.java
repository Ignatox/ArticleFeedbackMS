package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.controller;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedbackDTO;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleSummary;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security.TokenService;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security.User;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.ResourceNotFoundException;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleFeedbackService;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.service.ArticleSummaryService;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.SimpleError;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TokenService tokenService;

    // Endpoint para obtener feedbacks pendientes o completados por usuario
    @GetMapping("/user")
    public ResponseEntity<List<ArticleFeedback>> getFeedbacksByStatusAndUserId(
        @RequestParam String status,
        @RequestHeader("Authorization") String authHeader){
    try{
        tokenService.validate(authHeader);
        User user = tokenService.getUser(authHeader);
        String userId = user.getId();

        return ResponseEntity.ok(feedbackService.getFeedbacksByStatusAndUserId(status, userId));
    } catch (SimpleError e) {
        return ResponseEntity.status(e.getStatusCode()).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    }

    // Endpoint para listar feedbacks por artículo
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ArticleFeedback>> getFeedbacksByArticle(@PathVariable String articleId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByArticle(articleId));
    }

    // Endpoint para guardar un feedback llenado
    @PutMapping("/{articleFeedbackId}")

    public ResponseEntity<ArticleFeedback> saveFeedback(
            @PathVariable String articleFeedbackId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ArticleFeedbackDTO feedbackDTO){
    try{
        tokenService.validate(authHeader);

        ArticleFeedback updatedFeedback = feedbackService.updateFeedback(articleFeedbackId, feedbackDTO);
        return ResponseEntity.ok(updatedFeedback);

    } catch (SimpleError e) {
        return ResponseEntity.status(e.getStatusCode()).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    }

    //Endpoint para listar articleSummary de un articulo por ID
    @GetMapping("/{articleId}/summary")
    public ResponseEntity<ArticleSummary> getSummary(@PathVariable String articleId){
        return ResponseEntity.ok(summaryService.getSummaryByArticle(articleId));
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }






}
