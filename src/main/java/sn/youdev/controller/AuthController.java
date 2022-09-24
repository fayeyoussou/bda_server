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
import sn.youdev.dto.request.ResetPasswordRequest;
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
    public ResponseEntity<?> enableUser(@PathVariable("token") @Valid final @Length(min = 60,max = 60) String token) throws EntreeException, TokenNotFoundException {
        return jsonResponse(true,userService.enableUser(token),200,"utilisateur valide");
    }
    @PostMapping("/reset/password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody final String email,HttpServletRequest request) throws UserNotFoundException {
        return jsonResponse(true,userService.passwordResetRequest(email,request),200,"mail sended to you for reset");
    }
    @PostMapping("/reset/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") final String token, @RequestBody @Valid final ResetPasswordRequest request) throws EntreeException, TokenNotFoundException {
        return jsonResponse(true,userService.passwordReset(token,request),200,"password reseted");
    }
}
