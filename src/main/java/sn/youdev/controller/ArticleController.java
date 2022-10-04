package sn.youdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleEditRequest;
import sn.youdev.dto.request.ArticleRequest;

import sn.youdev.dto.response.SectionString;
import sn.youdev.services.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController{
    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<?> addArticle(
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam Map<String,Object> req,
            HttpServletRequest request
    ) throws UserNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArticleRequest articleRequest = mapper.convertValue(req, ArticleRequest.class);
        articleRequest.setImage(image);
        return controllerResponse(service.addArticle(articleRequest,request));
    }
    @GetMapping
    public ResponseEntity<?> getArticle(){
        return controllerResponse(service.listArticle());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable final Long id) throws EntreeException, EntityNotFoundException {
        return controllerResponse(service.getArticleById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editArticle(@PathVariable("id") final Long id, @RequestBody final @Valid ArticleEditRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        return controllerResponse(service.editArticle(id,request,httpServletRequest));
    }
    @PutMapping("/section/{id}")
    public ResponseEntity<?> addOrEditSection(@PathVariable("id") final Long id, @RequestBody final SectionString sectionString) throws EntityNotFoundException {
        return controllerResponse(service.addOrEditSection(id,sectionString));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable final Long id) throws EntityNotFoundException {
        return controllerResponse(service.deleteArticle(id));
    }
    @PatchMapping("/image/{id}")
    public ResponseEntity<?> changeImage(@PathVariable("id") final Long id,@RequestParam(value = "image") MultipartFile image) throws EntityNotFoundException, IOException {
        return controllerResponse(service.changeImage(id,image));
    }
    @DeleteMapping("section/{id}/{position}")
    public ResponseEntity<?> deleteSection(@PathVariable("id") final Long id, @PathVariable("position") final byte position) throws EntityNotFoundException {
        return controllerResponse(service.deleteSection(id,position));
    }
}
