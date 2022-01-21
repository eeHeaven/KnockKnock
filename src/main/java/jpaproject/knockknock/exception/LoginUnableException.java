package jpaproject.knockknock.exception;

import lombok.Getter;

@Getter
public class LoginUnableException extends IllegalStateException{
    private ExceptionEnum error;

    public LoginUnableException(ExceptionEnum e){
        super(e.getMessage());
        this.error = e;
    }
}
