package sn.youdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.*;
import sn.youdev.dto.request.EmailRequest;
import sn.youdev.dto.request.RegisterRequest;
import sn.youdev.dto.request.ResetPasswordRequest;
import sn.youdev.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Map;

import static sn.youdev.config.Constante.jsonResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(
            //@Valid final RegisterRequest registerRequest,
            //final HttpServletRequest request
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam Map<String,Object> req,
            HttpServletRequest request
     ) throws UserNotFoundException, EntreeException, RoleNotFoundException, IOException, ArgumentValidationExption {
//             return controllerResponse(userService.saveUser( registerRequest,request),"user saved");
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest registerRequest = mapper.convertValue(req,RegisterRequest.class);
        CustomValidator validator = new CustomValidator();
        Map<String,String> errors = validator.validate(registerRequest);
        if (errors.size()>0) throw new ArgumentValidationExption(errors);
        return controllerResponse(userService.saveUser(registerRequest,image,request),"");
    }
    @GetMapping("/enable/{token}")
    public ResponseEntity<?> enableUser(@PathVariable("token") @Valid final @Length(min = 60,max = 60) String token) throws EntreeException, TokenNotFoundException {
        return controllerResponse(userService.enableUser(token),"utilisateur valide");
    }
    @PostMapping("/reset/password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody final EmailRequest emailRequest, HttpServletRequest request) throws UserNotFoundException {
        return controllerResponse(userService.passwordResetRequest(emailRequest.getEmail(),request),"mail sended to you for reset");
    }
    @PostMapping("/reset/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") final String token, @RequestBody @Valid final ResetPasswordRequest request) throws EntreeException, TokenNotFoundException {
        return controllerResponse(userService.passwordReset(token,request),"password reseted");
    }
}
