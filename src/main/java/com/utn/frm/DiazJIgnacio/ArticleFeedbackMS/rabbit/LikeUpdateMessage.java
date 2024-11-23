package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LikeUpdateMessage {
    private String articleId;

    private Boolean liked;

    @Override
    public String toString(){

        return "LikeUpdateMessage{" +
                "articleId='"+ articleId + '\'' +
                ", liked=" + (liked!= null ? liked : "null") +
                '}';
    }


}
