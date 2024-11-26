package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPlacedEvent {
    private String userId;
    private String articleId;

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "userId='" + userId + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
