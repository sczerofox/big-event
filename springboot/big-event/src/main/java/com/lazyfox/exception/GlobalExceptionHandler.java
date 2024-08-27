package com.lazyfox.exception;

import com.lazyfox.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author lazyFox
 * @Date 2024/7/25 20:24
 * @Version V0.1
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		e.printStackTrace();
		return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");
	}

}

