package sn.youdev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Constante {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    public static final String[] WHITE_LIST = {
            "/api/register/**"
    };
    public static ResponseEntity<Object> jsonResponse(Boolean status, Object obj, int statusCode, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("status",status);
        response.put("result",obj);
        if (!message.isEmpty()) response.put("message",message);
        return ResponseEntity.status(statusCode).body(response);
    }
    public static Date calculateExp(int minutes){
        Calendar calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,minutes);
        return calendar.getTime();
    }

    public static String applicationUrl(HttpServletRequest httpRequest) {
        return "http://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+httpRequest.getContextPath();
    }
}
