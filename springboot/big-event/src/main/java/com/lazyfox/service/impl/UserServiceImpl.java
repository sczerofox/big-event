package com.lazyfox.service.impl;

import com.lazyfox.mapper.UserMapper;
import com.lazyfox.pojo.User;
import com.lazyfox.service.UserService;
import com.lazyfox.utils.Md5Util;
import com.lazyfox.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author lazyFox
 * @Date 2024/7/25 16:03
 * @Version V0.1
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public void register(String username, String password) {
		// 密码加密
		String md5String = Md5Util.getMD5String(password);
		// 添加
		userMapper.add(username, md5String);
	}

	@Override
	public void update(User user) {
		user.setUpdateTime(LocalDateTime.now());
		userMapper.update(user);
	}

	@Override
	public void updateAvatar(String avatarUrl) {
		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer id =(Integer) claims.get("id");
		userMapper.updateAvatar(avatarUrl, id);
	}

	@Override
	public void updatePwd(String newPwd) {
		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer id =(Integer) claims.get("id");
		userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
	}

}

