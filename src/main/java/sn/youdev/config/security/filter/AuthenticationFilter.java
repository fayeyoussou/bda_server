package sn.youdev.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sn.youdev.dto.request.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{


            BufferedReader reader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            LoginRequest authRequest = objectMapper.readValue(stringBuilder.toString(),LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getLogin(),
                    authRequest.getPassword()
            );
            setDetails(request,token);
            log.info(token.toString());
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
