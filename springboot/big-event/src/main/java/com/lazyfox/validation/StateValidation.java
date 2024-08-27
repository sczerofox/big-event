package com.lazyfox.validation;

import com.lazyfox.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @ClassName StateValidation
 * @Description
 * @Author lazyFox
 * @Date 2024/7/31 10:30
 * @Version V0.1
 */

public class StateValidation implements ConstraintValidator<State, String> {


	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

		if(value == null) {
			return false;
		}
		if(value.equals("已发布") || value.equals("草稿")){
			return true;
		}

		return false;
	}
}

