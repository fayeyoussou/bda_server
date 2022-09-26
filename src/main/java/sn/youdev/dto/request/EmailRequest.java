package sn.youdev.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class EmailRequest {
    @Email
    @NotBlank
    @NotNull
    private String email;
}
