package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.LocalisationRequest;
import sn.youdev.services.LocalisationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/position")
public class LocalisationController extends BaseController {
    private final LocalisationService service;

    @Autowired
    public LocalisationController(LocalisationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getLocalisationList(){
        return controllerResponse(service.getLocalisationList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLocalisation(@PathVariable("id") Long id) throws EntityNotFoundException {
        return controllerResponse(service.getLocalisation(id));
    }
    @PostMapping
    public ResponseEntity<?> addLocalisation(@RequestBody @Valid final LocalisationRequest request){
        return controllerResponse(service.addLocalisation(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>editLocalisation(@PathVariable("id") final Long id,@RequestBody @Valid final LocalisationRequest request) throws EntityNotFoundException {
        return controllerResponse(service.editLocalisation(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocalisation(@PathVariable("id") final Long id) throws EntityNotFoundException {
        return controllerResponse(service.deleteLocalisation(id));
    }
}
