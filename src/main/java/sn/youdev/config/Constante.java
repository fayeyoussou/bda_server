package sn.youdev.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.dto.response.UserReponseToken;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.File;
import sn.youdev.model.User;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("124MB"));
        factory.setMaxRequestSize(DataSize.parse("124MB"));
        return factory.createMultipartConfig();
    }
    public static final String[] ADMIN_LIST = {
            "/api/user/**"
    };
    public static  final  String PAS_VIDE= "ne peut être vide";
    public static final String[] CNTS_LIST = {

            "/api/donneur/**"
    };
    public static final String[] AUTH_LIST = {
            "/api/user/password",
    };
    public static final String ASSETS = "src/main/java/sn/youdev/assets";
    public static ResponseEntity<Object> jsonResponse(Boolean status, Object obj, int statusCode, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("status",status);
        response.put("result",obj);
        response.put("statusCode",statusCode);
        /**
         * message null message != null est faux donc sort
         * message ""   message != null vrai, mais message nest string vide faux donc sort
         * message = "val"  message != vrai et message n'est pas string vide vrai donc entre
         */
        if (message !=null && !message.equals("")) response.put("message",message);
        return ResponseEntity.status(200).body(response);
    }

    public static Date calculateExp(int minutes){
        Calendar calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,minutes);
        return calendar.getTime();
    }
    public static List<String> createFile(MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get(ASSETS + "/" + multipartFile.getContentType());
        String type = multipartFile.getContentType();
        java.io.File theDir = uploadDirectory.toFile();
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        InputStream inputStream = multipartFile.getInputStream();
        String nom = RandomStringUtils.randomAlphanumeric(15).toLowerCase();
        Path filePath = uploadDirectory.resolve(nom + getExtension(fileName));
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        assert type != null;
        return List.of(nom, type);
    }


    public static String applicationUrl(HttpServletRequest httpRequest) {
        return "http://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+httpRequest.getContextPath();
    }
    public static String generateNumero(String start){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (start
                +(day<10? RandomStringUtils.randomAlphabetic(1)+day:day)
                +RandomStringUtils.randomAlphanumeric(2)
                +(year%100)+RandomStringUtils.randomAlphanumeric(2)
                +(month<10 ? month+RandomStringUtils.randomAlphabetic(1):month)).toUpperCase();
    }
    public static String getExtension(String fileName){
        return (String) fileName.subSequence(fileName.lastIndexOf("."),fileName.length());
    }
}
