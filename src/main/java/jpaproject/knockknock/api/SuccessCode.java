package jpaproject.knockknock.api;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum SuccessCode {

    LIST_SUCCESSFULLY_RETURNED(HttpStatus.OK, "조회 결과 리스트가 성공적으로 반환되었습니다.");

    private final HttpStatus httpStatus;
    private String message;

    SuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
