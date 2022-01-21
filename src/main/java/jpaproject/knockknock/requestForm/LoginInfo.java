package jpaproject.knockknock.requestForm;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginInfo {

    String id;
    String password;

    public LoginInfo(String id, String password){
        this.id = id;
        this.password = password;
    }
}
