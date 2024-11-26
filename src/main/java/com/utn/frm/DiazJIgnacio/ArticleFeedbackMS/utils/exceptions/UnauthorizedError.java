package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions;

public class UnauthorizedError extends SimpleError {

    public UnauthorizedError() {
        super(401, "Unauthorized");
    }

    public UnauthorizedError(String message){
        super(401, message);
         }
}