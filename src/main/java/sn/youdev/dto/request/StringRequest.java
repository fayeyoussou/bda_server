package sn.youdev.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class StringRequest {
    @NotNull @NotEmpty
    private String value;
}
