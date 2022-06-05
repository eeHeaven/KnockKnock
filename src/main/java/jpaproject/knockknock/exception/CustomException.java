package jpaproject.knockknock.exception;

import jpaproject.knockknock.exception.ExceptionEnum;
import lombok.Getter;

@Getter
public class CustomException extends IllegalArgumentException {
    private final ExceptionEnum error;

    public CustomException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
