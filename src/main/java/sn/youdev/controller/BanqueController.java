package sn.youdev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.BanqueRequete;
import sn.youdev.services.BanqueService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/banque")
@Slf4j
public class BanqueController extends BaseController{
    private final BanqueService banqueService;

    @Autowired
    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @GetMapping
    public ResponseEntity<?> getBanques(){
        return controllerResponse(banqueService.getBanqueList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBanque(@PathVariable("id") Long id) throws EntityNotFoundException {
        return controllerResponse(banqueService.getBanque(id));
    }
    @PostMapping
    public ResponseEntity<?> addBanque(@RequestBody final BanqueRequete requete) throws EntityNotFoundException, ArgumentValidationExption {
        try {



            return controllerResponse(banqueService.addBanque(requete));

        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw e;
        }
//        return controllerResponse(banqueService.addBanque(requete));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editBanque(@PathVariable("id") Long id,@RequestBody @Valid final BanqueRequete requete) throws EntityNotFoundException {
        return controllerResponse(banqueService.editBanque(id,requete));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBanque(@PathVariable("id") Long id) throws EntityNotFoundException {
        return controllerResponse(banqueService.deleteBanque(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReserve(@PathVariable("id") Long id,@RequestBody final Map<String,Integer> reserves) throws EntityNotFoundException {
        return controllerResponse(banqueService.editReserve(id,reserves));
    }
}
