package com.yoo.lms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@Slf4j
public class FileController {

    @ResponseBody
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String filePath,
                                             @RequestParam String fileName){

        File target = new File(filePath+fileName);
        String originalFileName = new String(fileName.substring(fileName.indexOf("_")+1)
                .getBytes(StandardCharsets.UTF_8));

        HttpHeaders header = new HttpHeaders();
        Resource resource = null;

        if(target.exists()) {
            try {
                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
                if(mimeType == null) {
                    mimeType = "octet-stream";
                }
                resource = new UrlResource(target.toURI());
                header.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+ originalFileName +"\"");

                header.setCacheControl("no-cache");
                header.setContentType(MediaType.parseMediaType(mimeType));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
