package com.lazyfox.service;

import com.lazyfox.pojo.Article;
import com.lazyfox.pojo.PageBean;

public interface ArticleService {

	//  添加文章
	void add(Article article);

	PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
