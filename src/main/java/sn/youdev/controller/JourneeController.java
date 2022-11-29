package sn.youdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleRequest;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.JourneeRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.services.JourneeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/journee")
public class JourneeController extends BaseController{
    private final JourneeService service;

    @Autowired
    public JourneeController(JourneeService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> getJournees(){
        return controllerResponse(service.listJournee());
    }
    @GetMapping("/dons/{id}")
    public ResponseEntity<?> getDons(@PathVariable("id") Long id) throws EntityNotFoundException {
        return controllerResponse(service.listDonJournee(id));
    }
    @GetMapping("/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Long id) throws EntityNotFoundException{
        return controllerResponse(service.articleJournee(id));
    }
    @PostMapping
    public ResponseEntity<?> addJourne(@RequestBody final @Valid JourneeRequest request) throws UserNotFoundException {
        return controllerResponse(service.addJournee(request));
    }
    @PostMapping("/don/{id}")
    public ResponseEntity<?> addDonJournee(@PathVariable("id") final Long id,@RequestBody @Valid final DonRequest request) throws CustomArgumentValidationException, EntityNotFoundException {
        return controllerResponse(service.addDonJournee(id,request));
    }
    @PostMapping("/article/{id}")
    public ResponseEntity<?> addArticleToJournee(
            @PathVariable("id") final Long id,
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam Map<String,Object> req,
            HttpServletRequest httpServletRequest) throws UserNotFoundException, IOException, EntityNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        ArticleRequest articleRequest = mapper.convertValue(req, ArticleRequest.class);
//        articleRequest.setImage(image);
        return controllerResponse(service.addArticleToJournee(id,articleRequest,httpServletRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getJourneeById(@PathVariable("id") final Long id) throws EntityNotFoundException{
        return controllerResponse(service.getJourneeById(id));
    }
    @PutMapping("/cnts/{id}")
    public ResponseEntity<?> addCntsComment(@PathVariable("id") final Long id,@RequestBody final @Valid StringRequest request) throws EntityNotFoundException{
        return controllerResponse(service.addCntsComment(id,request));
    }
    @PutMapping("/organisateur/{id}")
    public ResponseEntity<?> addOrgaComment(@PathVariable("id") final Long id, HttpServletRequest httpServletRequest,@RequestBody final @Valid StringRequest request) throws EntityNotFoundException, UserNotFoundException, EntreeException{
        return controllerResponse(service.addOrgaComment(id,httpServletRequest,request));
    }
}
