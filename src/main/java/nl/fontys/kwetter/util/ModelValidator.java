package nl.fontys.kwetter.util;

import nl.fontys.kwetter.exceptions.ModelValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class ModelValidator {
    private Validator validator;

    public ModelValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate(Object object) throws ModelValidationException {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        StringBuilder violationMessages = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            violationMessages.append(violation.getMessage()).append(" ");
        }
        if (violationMessages.length() != 0) {
            throw new ModelValidationException(violationMessages.toString());
        }
    }
}