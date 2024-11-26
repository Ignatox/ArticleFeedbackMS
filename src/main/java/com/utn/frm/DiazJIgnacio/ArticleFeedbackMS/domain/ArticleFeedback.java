package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "article_feedback") //Nombre de colección en MongoDB
public class ArticleFeedback {

    @Id
    private String articleFeedbackId;

    private String articleId;
    private String userId;

    private String comment;
    private Boolean liked;

    @LastModifiedDate
    private Date  updatedAt;
    @CreatedDate
    private Date createdAt;

    private String status; //PENDIENTE O COMPLETADO
}
