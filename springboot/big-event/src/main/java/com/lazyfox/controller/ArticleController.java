package com.lazyfox.controller;

import com.lazyfox.pojo.Article;
import com.lazyfox.pojo.PageBean;
import com.lazyfox.pojo.Result;
import com.lazyfox.service.ArticleService;
import com.lazyfox.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName ArticleController
 * @Description
 * @Author lazyFox
 * @Date 2024/7/25 22:25
 * @Version V0.1
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping
	public Result add(@RequestBody @Validated Article article){
		articleService.add(article);
		return Result.success();
	}

	@GetMapping
	public Result<PageBean<Article>> list(Integer pageNum,
										  Integer pageSize,
										  @RequestParam(required = false) Integer categoryId,
										  @RequestParam(required = false) String state){
		PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
		return Result.success(pb);
	}


}

