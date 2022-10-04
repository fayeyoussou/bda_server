package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.DemandeRequest;
import sn.youdev.services.DemandeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/demande")
public class DemandeController extends BaseController {
    private final DemandeService service;

    @Autowired
    public DemandeController(DemandeService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable("id") final Long id) throws EntityNotFoundException, EntreeException{
        return controllerResponse(service.getDemandeById(id));
    }
    @GetMapping
    public ResponseEntity<?> getDemandeList(){
        return controllerResponse(service.getDemandeList());
    }
    @PostMapping
    public ResponseEntity<?> addDemande(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid final DemandeRequest request
    ) throws UserNotFoundException{
        return controllerResponse(service.addDemande(httpServletRequest,request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editDemande(
            @PathVariable("id") final Long id,
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid final DemandeRequest request)
            throws
                UserNotFoundException,
                EntityNotFoundException,
                EntreeException{
        return controllerResponse(service.editDemande(id,httpServletRequest,request));
    }
    public ResponseEntity<?> deleteDemande(
            @PathVariable("id") final Long id,
            HttpServletRequest request
    ) throws
            EntityNotFoundException,
            UserNotFoundException,
            EntreeException {
        return controllerResponse(service.deleteDemande(id,request));
    }
}
