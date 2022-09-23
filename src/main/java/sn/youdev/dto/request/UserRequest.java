package sn.youdev.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class UserRequest {
    @Length(min = 3)
    private String login;
    @Length(min = 6)
    private String password;

}
