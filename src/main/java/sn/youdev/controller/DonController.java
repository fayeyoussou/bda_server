package sn.youdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.ResultatRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.services.DonService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/don")
@Slf4j
public class DonController extends BaseController{
    private final DonService donService;
    @Autowired
    public DonController(DonService donService) {
        this.donService = donService;
    }
    @GetMapping
    public ResponseEntity<?> getAllDon(){
        try {
            return controllerResponse(donService.listAllDon());
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @GetMapping("/dispo")
    public ResponseEntity<?> getAllDispo(){
        try {
            return controllerResponse(donService.listDonDispo());
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @GetMapping("/donneur/{numero}")
    public ResponseEntity<?>getAllByDonneur(@PathVariable("numero") final String numero) throws EntityNotFoundException {
        try {
            return controllerResponse(donService.listByDonneur(numero));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @GetMapping("/{numero}")
    public ResponseEntity<?>getDon(@PathVariable("numero") final String numero) throws EntityNotFoundException{
        try {
            return controllerResponse(donService.getDon(numero));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @GetMapping("resultat/{numero}")
    public ResponseEntity<?> getResultat(@PathVariable("numero") final String numero) throws EntityNotFoundException {
        try {
            return controllerResponse(donService.seeResultat(numero));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @PostMapping
    public ResponseEntity<?> saveDon(@RequestBody DonRequest donRequest) throws CustomArgumentValidationException, EntityNotFoundException {
        try {
            return controllerResponse(donService.saveDon(donRequest));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @PostMapping("/result")
    public ResponseEntity<?>saveResult(@RequestBody final ResultatRequest request) throws CustomArgumentValidationException, EntityNotFoundException {
        try {
            return controllerResponse(donService.saveResult(request));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @PutMapping("/{numero}")
    public ResponseEntity<?> editDon(@PathVariable final String numero,@RequestParam Map<String,Object> req) throws CustomArgumentValidationException, EntityNotFoundException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DonRequest donRequest = mapper.convertValue(req, DonRequest.class);
            return controllerResponse(donService.editDon(numero,donRequest));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @PatchMapping("/etat/{numero}")
    public ResponseEntity<?> changeEtat(@PathVariable("numero") final String numero, @RequestBody final @Valid StringRequest request ) throws EntityNotFoundException {
        try {

            return controllerResponse(donService.changeEtat(numero,request.getValue()));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
    @PutMapping("/resultat")
    public ResponseEntity<?> changeResultat(@RequestParam Map<String,Object> req) throws CustomArgumentValidationException, EntityNotFoundException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResultatRequest request = mapper.convertValue(req, ResultatRequest.class);
            return controllerResponse(donService.saveResult(request));
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw  e;
        }
    }
}
