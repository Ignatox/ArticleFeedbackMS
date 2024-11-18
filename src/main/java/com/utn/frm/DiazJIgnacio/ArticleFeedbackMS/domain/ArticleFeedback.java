package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "article_feedback") //Nombre de colección en MongoDB
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleFeedback {

    @Id
    private Long ArticleFeedbackID;

    private Long articleId;
    private Long userId;

    private String comment;
    private Boolean liked;

    @LastModifiedDate
    private Date  updatedAt;
    @CreatedDate
    private Date createdAt;

    private String status; //PENDIENTE O COMPLETADO
}
