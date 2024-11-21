package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.security;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.SimpleError;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils.exceptions.UnauthorizedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Validations {
    @Autowired
    private TokenService tokenService; // Servicio que maneja comunicaciones con Auth


    //Valida si le usuario tiene autorizacion, throws unauthorized error si el token no es valido
    public void validateUser(String authHeader) throws SimpleError {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
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

}
