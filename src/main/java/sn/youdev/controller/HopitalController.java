package sn.youdev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.HopitalRequest;
import sn.youdev.services.HopitalService;

@RestController
@RequestMapping("/api/hopital")
@Slf4j
public class HopitalController extends BaseController{
    private final HopitalService service;

    @Autowired
    public HopitalController(HopitalService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        try{
            return controllerResponse(service.listHopital());
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) throws EntityNotFoundException {
        try{
            return controllerResponse(service.GetById(id));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody final HopitalRequest request) throws EntityNotFoundException, ArgumentValidationExption {
        try{
            return controllerResponse(service.saveHopital(request));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>edit(@PathVariable("id") Long id, @RequestBody final HopitalRequest request) throws EntityNotFoundException, ArgumentValidationExption {
        try{
            return controllerResponse(service.editHopital(id,request));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        try{
            return controllerResponse(service.deleteHopital(id));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<?>getEmployees(@PathVariable("id") Long id) throws EntityNotFoundException {
        try{
            return controllerResponse(service.getEmployees(id));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
    }
}
