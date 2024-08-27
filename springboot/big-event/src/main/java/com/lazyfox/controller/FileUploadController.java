package com.lazyfox.controller;

import com.lazyfox.pojo.Result;
import com.lazyfox.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FileUploadController
 * @Description
 * @Author lazyFox
 * @Date 2024/7/31 12:52
 * @Version V0.1
 */

@RestController
public class FileUploadController {

	@PostMapping("/upload")
	public Result<String> upload(MultipartFile file) throws IOException {
		String originalFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
//		file.transferTo(new File("D:\\Data\\idea-workspace\\springboot-vue3\\big-event\\files\\" + fileName));
		return Result.success(AliOssUtil.uploadFile(fileName,file.getInputStream()));
	}
}

