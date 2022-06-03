package jpaproject.knockknock.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<CustomExceptionEntity> exceptionHandler(final CustomException e){
        return ResponseEntity.status(e.getError().getHttpStatus())
                .body(CustomExceptionEntity.builder()
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

}