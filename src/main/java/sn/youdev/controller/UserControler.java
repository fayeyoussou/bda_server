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
public class UserControler {
    private final UserService userService;
    @Autowired
    public UserControler(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() throws UserNotFoundException {
        return jsonResponse(true,userService.findAllUser(),200,"liste des utilisateurs");
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") Long id, @RequestBody @Valid EditUserRequest request) throws UserNotFoundException, EntreeException {
        return jsonResponse(true,userService.editUser(id,request),200,"edit successful");
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
        return jsonResponse(true,userService.addDoctorRole(id,idHopital),200,"role added");
    }
    @GetMapping("/role/banque/{id}/{idBanque}")
    public ResponseEntity<?> addBanqueRole(@PathVariable("id") final Long id,@PathVariable("idBanque") final Long idBanque) throws UserNotFoundException, EntreeException, RoleNotFoundException, BanqueNotFoundException {
        return jsonResponse(true,userService.addBanqueRole(id,idBanque),200,"role added");
    }
    @GetMapping("/role/cnts/{id}")
    public ResponseEntity<?> addCntsRole(@PathVariable("id") final Long id) throws UserNotFoundException, EntreeException, RoleNotFoundException {
        return jsonResponse(true,userService.addCntsRole(id),200,"role added");
    }
    @PostMapping("/password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") final Long id, @RequestBody @Valid ChangePasswordRequest request) throws UserNotFoundException, EntreeException {
        return jsonResponse(true,userService.changePassword(id,request),200,"password change");
    }
    @GetMapping("/get_credential")
    public ResponseEntity<?> getCredential (HttpServletRequest request) throws NoSuchFieldException {


        return jsonResponse(true,userService.getConnected(request),200,"cred");
    }
}
