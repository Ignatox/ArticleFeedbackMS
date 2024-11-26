package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.SimpleError;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.UnauthorizedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Validations {
    @Autowired
    private TokenService tokenService; // Servicio que maneja comunicaciones con Auth

    @Autowired
    private ArticleFeedbackRepository feedbackRepository;


    //Valida si le usuario tiene autorizacion, throws unauthorized error si el token no es valido
    public void validateUser(String authHeader) throws SimpleError {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            //linea para probar donde arroja unauthorized
            System.out.println("Error en validate User");
            throw new UnauthorizedError();
            }
        tokenService.validate(authHeader.substring(7)); //valida el token sin el prefijo Bearer
    }


    //Obtiene la informacion del usuario actual desde el token
    //throws simpleError si no se peude obtener el usuario
    public User currentUser(String authHeader) throws SimpleError{
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new UnauthorizedError();
        }
        return tokenService.getUser(authHeader.substring(7)); //Obtiene el usuario desde auth
    }

//    public User validateUserFeedback(String authHeader, String articleFeedbackId){
//         validateUser(authHeader);
//         User userValidated = tokenService.getUser(authHeader.substring(7));
//        Optional<ArticleFeedback> articleFeedback = feedbackRepository.findById(articleFeedbackId);
//
//         if(userValidated)
//
//
//
//    }

}
