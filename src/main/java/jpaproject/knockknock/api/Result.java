package jpaproject.knockknock.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private SuccessCode successCode;
    private T data;

}
