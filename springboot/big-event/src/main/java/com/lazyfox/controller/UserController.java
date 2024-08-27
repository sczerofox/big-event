package com.lazyfox.controller;

import com.lazyfox.pojo.Result;
import com.lazyfox.pojo.User;
import com.lazyfox.service.UserService;
import com.lazyfox.utils.JwtUtil;
import com.lazyfox.utils.Md5Util;
import com.lazyfox.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @Description
 * @Author lazyFox
 * @Date 2024/7/25 16:03
 * @Version V0.1
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@PostMapping("/register")    //  注册
	public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
		// 查询用户 为空就表示被占用  不为空表示没有被占用
		if (userService.findByUsername(username) != null) {
			return Result.error("用户名已被占用");
		}
		// 没有占用
		// 注册
		userService.register(username, password);
		return Result.success();
	}


	@PostMapping("/login")    //  登录
	public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
		// 根据用户名 查询用户
		User loginUser = userService.findByUsername(username);
		// 用户名是否存在  为空表示不存在 不为空表示用户名存在
		if (loginUser == null) {
			return Result.error("用户名错误");
		}

		// 判断密码是否正确  loginUser 对象的password是密文
		if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
			// 登陆成功
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", loginUser.getId());
			claims.put("username", loginUser.getUsername());
			String token = JwtUtil.genToken(claims);

			// 将生成的 token 存储在 Redis 容器中
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
			operations.set(token,token, 1, TimeUnit.HOURS);
			return Result.success(token);
		}

		return Result.error("密码错误");
	}

	@GetMapping("/userInfo") // 获取用户信息
	public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
		//  获取用户名
//		Map<String, Object> claims = JwtUtil.parseToken(token);
		Map<String, Object> claims = ThreadLocalUtil.get();
		String username = (String) claims.get("username");
		//  根据用户名获取信息
		User user = userService.findByUsername(username);
		return Result.success(user);
	}

	@PutMapping("/update")    //  修改基本信息
	public Result update(@RequestBody @Validated User user) {
		userService.update(user);
		return Result.success();
	}

	@PatchMapping("/updateAvatar")    //  修改用户头像
	public Result updateAvatar(@RequestParam @URL String avatarUrl) {
		userService.updateAvatar(avatarUrl);
		return Result.success();
	}

	@PatchMapping("/updatePwd")    //  修改用户密码
	public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
		String oldPwd = params.get("password");
		String newPwd = params.get("rePassword");
		String rePwd = params.get("confirmPassword");
		if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
			return Result.error("缺少必要的参数");
		}
		// 原密码是否正确
		Map<String, Object> claims = ThreadLocalUtil.get();
		String username =(String) claims.get("username");
		User loginUser = userService.findByUsername(username);
		if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
			return Result.error("原密码填写不正确");
		}

		// 确认两次密码是否一致
		if(!rePwd.equals(newPwd)){
			return Result.error("两次密码填写不一致");
		}

		// 在调用更新密码
		userService.updatePwd(newPwd);
		//  获取原来的 老token
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		operations.getOperations().delete(token);
		return Result.success();
	}

}

