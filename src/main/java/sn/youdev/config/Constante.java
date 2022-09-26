package sn.youdev.config;

import org.apache.commons.lang3.RandomStringUtils;
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

@SuppressWarnings("DanglingJavadoc")
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

    public static final String[] ADMIN_LIST = {
            "/api/user/**"
    };
    public static final String[] CNTS_LIST = {

            "/api/donneur/**"
    };
    public static final String[] AUTH_LIST = {
            "/api/user/password",
    };
    public static ResponseEntity<Object> jsonResponse(Boolean status, Object obj, int statusCode, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("status",status);
        response.put("result",obj);
        /**
         * message null message != null est faux donc sort
         * message ""   message != null vrai, mais message nest string vide faux donc sort
         * message = "val"  message != vrai et message n'est pas string vide vrai donc entre
         */
        if (message !=null && !message.equals("")) response.put("message",message);
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
    public static String generateNumero(String start){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return start
                +(day<10? RandomStringUtils.randomAlphabetic(1)+day:day)
                +RandomStringUtils.randomAlphanumeric(2)
                +(year%100)+RandomStringUtils.randomAlphanumeric(2)
                +(month<10 ? month+RandomStringUtils.randomAlphabetic(1):month);
    }
}
