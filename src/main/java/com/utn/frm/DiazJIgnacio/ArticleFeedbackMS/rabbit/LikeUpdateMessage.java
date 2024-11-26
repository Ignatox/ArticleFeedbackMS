package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LikeUpdateMessage implements Serializable {
    private static final long serialVersionUID = 1l; //Identificador unico para la serializacion
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
