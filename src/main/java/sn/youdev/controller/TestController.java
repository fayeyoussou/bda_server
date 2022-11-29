package sn.youdev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.services.TestService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sn.youdev.config.Constante.getExtension;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam Map<String, Object> allRequest
    ) throws IOException {
        try {
            if (file ==null) {
                Map<String, String>map =new HashMap<>();
                map.put("file","file not found");
            }
            System.out.println(allRequest.get("prenom"));
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", file.getOriginalFilename());
            map.put("name", file.getName());
            map.put("content-type", file.getContentType());
            map.put("size", file.getSize());
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            map.put("result", testService.uploadFile(file));
            return ResponseEntity.ok(map);
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> download(@PathVariable final String fileCode) throws IOException {
        Resource resource = null;
        try {
            resource = (Resource) testService.downloadFile(fileCode);
        }catch (IOException e){
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("file not found", HttpStatus.NOT_FOUND);
        }
        String[] list = {
                String.valueOf(MediaType.IMAGE_PNG),
                String.valueOf(MediaType.IMAGE_JPEG)
        };
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\""+resource.getFilename()+"\"";
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION,headerValue)
                .body(resource);
    }
}
