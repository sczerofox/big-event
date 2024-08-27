package com.lazyfox.service.impl;

import com.lazyfox.mapper.CategoryMapper;
import com.lazyfox.pojo.Category;
import com.lazyfox.service.CategoryService;
import com.lazyfox.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author lazyFox
 * @Date 2024/7/30 17:12
 * @Version V0.1
 */

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public void add(Category category) {
		category.setCreateTime(LocalDateTime.now());
		category.setUpdateTime(LocalDateTime.now());

		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer userId =(Integer) claims.get("id");
		category.setCreateUser(userId);
		categoryMapper.add(category);
	}

	@Override
	public List<Category> list() {
		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer userId =(Integer) claims.get("id");
		return categoryMapper.list(userId);
	}

	@Override
	public Category findById(Integer id) {
		return categoryMapper.findById(id);
	}

	@Override
	public void update(Category category) {
		category.setUpdateTime(LocalDateTime.now());
		categoryMapper.update(category);
	}

	@Override
	public void delete(Integer id) {
		categoryMapper.delete(id);
	}
}

