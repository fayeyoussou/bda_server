package sn.youdev.config.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.TokenNotFoundException;
import sn.youdev.model.Token;
import sn.youdev.model.User;
import sn.youdev.repository.TokenRepo;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final TokenRepo tokenRepo;


    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenRepo tokenRepo) {
        super(authenticationManager);
        this.tokenRepo = tokenRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenCode = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if(tokenCode == null) throw new EntreeException("il faut se connecter");
            Optional<Token> tokenOptional = tokenRepo.findByCode(tokenCode);
            if (tokenOptional.isEmpty()) throw new TokenNotFoundException("donnee non trouve se reconnecter");
            Token token = tokenOptional.get();
            if (token.getType()!=(byte)1) throw new EntreeException("token incorrect");
            if(token.getExpiration().before(new Date())) throw new EntreeException("token expire");
            User user = token.getUser();
            Map<String,String> map = new HashMap<>();
            map.put("id",user.getId().toString());
            map.put("token",token.getCode());
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user,map,user.getAuthorities());
//            userToken.getCredentials()
            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request,response);

        } catch(Exception e){
            response.setHeader("error",e.getMessage());
            chain.doFilter(request,response);
        }
    }
}
