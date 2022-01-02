package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class SignInRequest {

    private String id;
    private String password;
    private String nickname;

}
