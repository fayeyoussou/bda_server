package sn.youdev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class CustomValidator {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    public Map<String, String> validate(Object object){
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        Map<String, String> errors = new HashMap<>();
        if(violations.size()==0) return errors;
        for (ConstraintViolation<Object> constraint: violations
             ) {
            errors.put(constraint.getPropertyPath().toString(), constraint.getMessageTemplate());
        }
        return errors;
    }
}
