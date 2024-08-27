package com.lazyfox.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lazyfox.mapper.ArticleMapper;
import com.lazyfox.pojo.Article;
import com.lazyfox.pojo.PageBean;
import com.lazyfox.service.ArticleService;
import com.lazyfox.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleServiceImpl
 * @Description
 * @Author lazyFox
 * @Date 2024/7/31 9:46
 * @Version V0.1
 */

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;


	@Override
	public void add(Article article) {
		article.setCreateTime(LocalDateTime.now());
		article.setUpdateTime(LocalDateTime.now());

		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer userId =(Integer) claims.get("id");
		article.setCreateUser(userId);

		articleMapper.add(article);
	}

	@Override
	public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
		//  1. 创建PageBean对象
		PageBean<Article> pb = new PageBean<>();

		//  2. 开启分页查询 PageHelper
		PageHelper.startPage(pageNum,pageSize);

		//  3.  调用Mapper
		Map<String, Object> claims = ThreadLocalUtil.get();
		Integer userId = (Integer) claims.get("id");
		List<Article> as = articleMapper.list(userId,categoryId,state);
		Page<Article> p = (Page<Article>) as;

		pb.setTotal(p.getTotal());
		pb.setItems(p.getResult());
		return pb;
	}


}

