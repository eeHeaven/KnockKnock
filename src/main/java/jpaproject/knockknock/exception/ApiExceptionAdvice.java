package jpaproject.knockknock.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({LoginUnableException.class})
    public ResponseEntity<LoginExceptionEntity> exceptionHandler(HttpServletRequest request, final LoginUnableException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(LoginExceptionEntity.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<LoginExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(LoginExceptionEntity.builder()
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<LoginExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(LoginExceptionEntity.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<LoginExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(LoginExceptionEntity.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }
}