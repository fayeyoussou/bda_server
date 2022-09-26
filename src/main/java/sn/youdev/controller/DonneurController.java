package sn.youdev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.TestException;
import sn.youdev.dto.request.DonneurRequest;
import sn.youdev.dto.request.LoginRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.services.DonneurService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static sn.youdev.config.Constante.jsonResponse;


@RestController
@RequestMapping("/api/donneur")
public class DonneurController extends BaseController{
    private final DonneurService donneurService;
    @Autowired
    public DonneurController(DonneurService donneurService) {
        this.donneurService = donneurService;
    }

    @GetMapping
    public ResponseEntity<?> listDonneur(){
        return controllerResponse(donneurService.findAll(),null);
    }
    @GetMapping("/{numero}")
    public ResponseEntity<?> getDonneur(@PathVariable("numero") String numero) throws EntityNotFoundException {
        return controllerResponse(donneurService.findByNumero(numero),null);
    }
    @PostMapping
    public ResponseEntity<?> saveDonneur(@RequestBody @Valid final DonneurRequest request) throws EntreeException, EntityNotFoundException, TestException {
        return controllerResponse(donneurService.saveDonneur(request),"donneur ajoute");
    }
    @PutMapping("/{numero}")
    public ResponseEntity<?> editDonneur(@PathVariable("numero") String numero,@RequestBody @Valid final DonneurRequest request) throws EntityNotFoundException {
        return controllerResponse(donneurService.editDonneur(numero,request),"donneur edited");
    }
    @DeleteMapping("/{numero}")
    public ResponseEntity<?> delDonneur(@PathVariable("numero") String numero) throws EntityNotFoundException {
        return controllerResponse(donneurService.deleteDonneur(numero),"suppresion reussit");
    }
    @PostMapping("/exist-user/{idInfo}")
    public ResponseEntity<?> saveExistUser(@PathVariable("idInfo") Long idInfo,@RequestBody @Valid final StringRequest groupe) throws EntityNotFoundException, TestException {
        return controllerResponse(donneurService.saveWithExist(idInfo,groupe.getValue()),"changé");
    }
    @PostMapping("/exist-donneur/{numero}")
    public ResponseEntity<?> saveExistDonneur(@PathVariable("numero") String numero, @RequestBody @Valid final StringRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        return controllerResponse(donneurService.saveExistDonneur(numero,request,httpServletRequest),null);
    }
}
