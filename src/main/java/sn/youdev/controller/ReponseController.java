package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ReponseRequest;
import sn.youdev.services.ReponseService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@RestController
@RequestMapping("/api/reponse")
public class ReponseController extends BaseController{
    private final ReponseService service;

    @Autowired
    public ReponseController(ReponseService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> repondre(@PathVariable("id") final Long id, @RequestBody @Valid final ReponseRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException, UserNotFoundException {
        return controllerResponse(service.repondre(id,request,httpServletRequest));
    }
}
