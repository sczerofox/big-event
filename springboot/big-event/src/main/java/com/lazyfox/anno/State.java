package com.lazyfox.anno;

import com.lazyfox.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = { StateValidation.class })
public @interface State {

	String message() default "state 参数的值只能是已发布或者草稿";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
