package sn.youdev.config.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.model.Token;
import sn.youdev.model.User;
import sn.youdev.repository.TokenRepo;
import sn.youdev.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenRepo tokenRepo;
    private final UserService userService;

    @Autowired
    public AuthenticationSuccessHandler(TokenRepo tokenRepo, UserService userService) {
        this.tokenRepo = tokenRepo;
        this.userService = userService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        try {
            User user = userService.findByCredentials(principal.getUsername());
            Token token = new Token((byte) 1,user);
            tokenRepo.save(token);
            log.info(token.getCode());
            response.addHeader("Authorization",token.getCode());
            response.addHeader("Content-Type","application/json");
            response.getWriter().write("{\"token\":"+token.getCode()+",\"login\":"+user.getLogin()+"}");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
