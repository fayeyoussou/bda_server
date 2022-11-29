package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.*;
import sn.youdev.dto.request.ChangePasswordRequest;
import sn.youdev.dto.request.EditUserRequest;
import sn.youdev.services.UserService;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sn.youdev.config.Constante.jsonResponse;

@RestController
@RequestMapping("/api/user")
public class UserControler extends BaseController {
    private final UserService userService;
    @Autowired
    public UserControler(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() throws UserNotFoundException {
        return controllerResponse(userService.findAllUser(),"liste des utilisateurs");
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") Long id, @RequestBody @Valid EditUserRequest request) throws UserNotFoundException, EntreeException {
        return controllerResponse(userService.editUser(id,request),"edit successful");
    }
    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") final Long id) throws UserNotFoundException {
        userService.blockUser(id);
        Map<String,Boolean> map = new HashMap<>();
        map.put("status",true);
        return ResponseEntity.ok(map);
    }
    @GetMapping("/role/medecin/{id}/{idHopital}")
    public ResponseEntity<?> addMedecinRole(@PathVariable("id") final Long id,@PathVariable("idHopital") final Long idHopital) throws UserNotFoundException, EntreeException, RoleNotFoundException, EntityNotFoundException {
        return controllerResponse(userService.addDoctorRole(id,idHopital),"role added");
    }
    @GetMapping("/role/banque/{id}/{idBanque}")
    public ResponseEntity<?> addBanqueRole(@PathVariable("id") final Long id,@PathVariable("idBanque") final Long idBanque) throws UserNotFoundException, EntreeException, RoleNotFoundException, BanqueNotFoundException {
        System.out.println("here");
        return controllerResponse(userService.addBanqueRole(id,idBanque),"role added");
    }
    @GetMapping("/role/cnts/{id}")
    public ResponseEntity<?> addCntsRole(@PathVariable("id") final Long id) throws UserNotFoundException, EntreeException, RoleNotFoundException {
        return controllerResponse(userService.addCntsRole(id),"role added");
    }
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request,HttpServletRequest httpRequest) throws UserNotFoundException, EntreeException {
        Long id = userService.getUserByRequest(httpRequest).getId();
        return controllerResponse(userService.changePassword(id,request),"password change");
    }
    @GetMapping("/get_credential")
    public ResponseEntity<?> getCredential (HttpServletRequest request) throws UserNotFoundException {
        return controllerResponse(userService.getUserByRequest(request).getResponse(),"cred");
    }
    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable final Long id,HttpServletRequest request) throws UserNotFoundException {
        boolean isAdmin = userService.getUserByRequest(request).getRoles().contains("admin");
        if(!isAdmin) return jsonResponse(false,"seul l'admin a le droit de bloquer",404,"Unauthorized");
        return controllerResponse(userService.blockUser(id),"utilisateur bloqué");
    }
}
