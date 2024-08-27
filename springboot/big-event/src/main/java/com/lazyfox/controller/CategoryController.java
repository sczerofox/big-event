package com.lazyfox.controller;

import com.lazyfox.pojo.Category;
import com.lazyfox.pojo.Result;
import com.lazyfox.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author lazyFox
 * @Date 2024/7/30 17:10
 * @Version V0.1
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
		categoryService.add(category);
		return Result.success();
	}

	@GetMapping
	public Result<List<Category>> list() {
		return Result.success(categoryService.list());
	}

	@GetMapping("/detail")
	public Result<Category> detail(@RequestParam Integer id){
		return Result.success(categoryService.findById(id));
	}

	@PutMapping
	public Result update(@RequestBody @Validated(Category.Update.class) Category category){
		categoryService.update(category);
		return Result.success();
	}

	@DeleteMapping
	public Result delete(@RequestParam Integer id){
		categoryService.delete(id);
		return Result.success();
	}
}