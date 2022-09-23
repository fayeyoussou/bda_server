package sn.youdev.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.RoleNotFoundException;
import sn.youdev.config.error.TokenNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.RegisterRequest;
import sn.youdev.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static sn.youdev.config.Constante.jsonResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid final RegisterRequest registerRequest, final HttpServletRequest request) throws UserNotFoundException, EntreeException, RoleNotFoundException{
             return jsonResponse(true,userService.saveUser( registerRequest,request),200,"user saved");
    }
    @GetMapping("/enable/{token}")
    public ResponseEntity<?> enableUser(@PathVariable("token") @Valid @Length(min = 60,max = 60) final String token) throws EntreeException, TokenNotFoundException {
        return jsonResponse(true,userService.enableUser(token),200,"utilisateur valide");
    }
}