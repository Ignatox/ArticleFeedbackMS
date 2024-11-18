package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArticleFeedbackDTO {
    private String comment;
    private Boolean liked;
}
