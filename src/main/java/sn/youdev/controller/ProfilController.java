package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.services.UserInfoService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profil")
public class ProfilController extends BaseController{
    private final UserInfoService service;

    @Autowired
    public ProfilController(UserInfoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?>getConnected(HttpServletRequest request) throws UserNotFoundException {
        return controllerResponse(service.VoirProfilUser(request));
    }
    @GetMapping("/donneur")
    public ResponseEntity<?>getDonneur(HttpServletRequest request) throws UserNotFoundException {
        return controllerResponse(service.VoirProfilDonneur(request));
    }
    @GetMapping("/logout")
    public ResponseEntity<?>logout(HttpServletRequest request) {
        return controllerResponse(service.logout(request));
    }
    @GetMapping("/dons")
    public ResponseEntity<?> voirMesDons(HttpServletRequest request) throws UserNotFoundException{
        return controllerResponse(service.voirMesDons(request));
    }
}
