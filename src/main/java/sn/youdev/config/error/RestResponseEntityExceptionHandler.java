package sn.youdev.config.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sn.youdev.config.Constante;

import java.util.HashMap;
import java.util.Map;

/**
 * this class will handle the response of Exception
 * created by me
 */
@ControllerAdvice @ResponseStatus
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler
{
    @ExceptionHandler({EntityNotFoundException.class,BanqueNotFoundException.class,RoleNotFoundException.class,TokenNotFoundException.class,UsernameNotFoundException.class,UserNotFoundException.class})
    public ResponseEntity<?> NotFoundException(Exception e){
        return Constante.jsonResponse(false,e.getClass().getSimpleName(),404,e.getMessage());
    }
    @ExceptionHandler(EntreeException.class)
    public ResponseEntity<?> entreeException(EntreeException e){
        return Constante.jsonResponse(false,e.getClass().getSimpleName(),400,e.getMessage());
    }

    @Override
//    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Constante.jsonResponse(false,errors,400,"Erreur validation");
    }
    public RestResponseEntityExceptionHandler() {
        super();
    }
}
