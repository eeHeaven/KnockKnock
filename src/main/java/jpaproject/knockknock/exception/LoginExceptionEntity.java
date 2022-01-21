package jpaproject.knockknock.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class LoginExceptionEntity {

    private String errorCode;
    private String errorMessage;

    @Builder
    public LoginExceptionEntity(HttpStatus status, String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
