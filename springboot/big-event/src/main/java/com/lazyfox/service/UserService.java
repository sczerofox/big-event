package com.lazyfox.service;

import com.lazyfox.pojo.User;

public interface UserService {
	// 根据用户名查询用户
	User findByUsername(String username);
	// 注册
	void register(String username, String password);
	//  修改个人数据
	void update(User user);
	//  修改用户头像
	void updateAvatar(String avatarUrl);
	//  修改用户密码
	void updatePwd(String newPwd);

}
