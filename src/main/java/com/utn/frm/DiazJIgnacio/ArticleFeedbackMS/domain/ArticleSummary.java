package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "article_summary") //Coleccion de mongoDB
public class ArticleSummary {
    @Id
    private Long articlSummaryId;

    private Long articleId;

    private int totalDislikes;
    private int totalLikes;
}
