package sn.youdev.config.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter @Setter
public class ArgumentValidationExption extends Exception{
    private final Map<String, String> errors;
    public ArgumentValidationExption(Map<String,String> errors) {
        super();
        this.errors = errors;
    }



}
