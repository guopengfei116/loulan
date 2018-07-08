package com.loulan.content.controller;

import com.loulan.vo.HttpResult;
import loulan.com.util.FastDFSClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public HttpResult upload(MultipartFile file) {
        try {
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            FastDFSClient client = new FastDFSClient("classpath:fastDFS/tracker.conf");
            String url = client.uploadFile(file.getBytes(), fileExtension);

            return HttpResult.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.ok("上传失败");
    }
}
