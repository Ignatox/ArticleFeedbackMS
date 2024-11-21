package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions;




public class SimpleError extends RuntimeException{
    private final int statusCode;

    public SimpleError(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }




}
