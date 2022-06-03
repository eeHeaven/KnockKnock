package jpaproject.knockknock.exception;

import com.google.api.Http;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"파라미터 값을 확인해주세요."),

    //404 NOT_FOUND 잘못된 리소스 접근
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저가 존재하지 않습니다."),
    LOGIN_FAIL(HttpStatus.NOT_FOUND,"해당 계정을 찾을 수 없습니다. 아이디와 비밀번호를 확인해주세요."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 댓글을 찾을 수 없습니다."),

    //409 CONFLICT 중복 리소스
    ID_ALREADY_EXIST(HttpStatus.CONFLICT,"이미 존재하는 아이디입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT,"이미 존재하는 닉네임입니다."),
    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final HttpStatus httpStatus;
    private String message;

    ExceptionEnum(HttpStatus status) {
        this.httpStatus = status;
    }

    ExceptionEnum(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }
}
