package jpaproject.knockknock.api.request;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequest{
    @NotBlank
    private String nickname;
    @NotBlank
    private String memberId;
    @NotBlank
    private String memberPassword;

}