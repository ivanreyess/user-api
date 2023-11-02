package com.sv.userapi.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String message() default "Password does not match minimum requirements. It has to be 8 character minimum. It needs to have 1 upper case letter, 1 lower case letter and a special character.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
