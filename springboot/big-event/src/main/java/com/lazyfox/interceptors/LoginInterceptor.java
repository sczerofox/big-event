package com.lazyfox.interceptors;

import com.lazyfox.utils.JwtUtil;
import com.lazyfox.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @ClassName LoginInterceptor
 * @Description
 * @Author lazyFox
 * @Date 2024/7/27 9:33
 * @Version V0.1
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		try {
			//  从 Redis 中获取相同的 token
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
			String redisToken = operations.get(token);
			if(redisToken == null){
				//  token 失效
				throw new RuntimeException();
			}
			Map<String, Object> claims = JwtUtil.parseToken(token);
			//  把业务数据存储在ThreadLocal中
			ThreadLocalUtil.set(claims);
			return true;
		} catch (Exception e) {
			response.setStatus(401);
			return false;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//  清除ThreadLocal中的数据
		ThreadLocalUtil.remove();
	}
}

