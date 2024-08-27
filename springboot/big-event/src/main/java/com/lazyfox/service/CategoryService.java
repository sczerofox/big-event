package com.lazyfox.service;

import com.lazyfox.pojo.Category;

import java.util.List;

public interface CategoryService {

	// 添加分类信息
	void add(Category category);
	// 查询
	List<Category> list();
	//  根据id查询
	Category findById(Integer id);
	//  修改文章分类信息
	void update(Category category);
	//  删除文章分类信息
	void delete(Integer id);
}
