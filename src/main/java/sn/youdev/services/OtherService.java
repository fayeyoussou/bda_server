package sn.youdev.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface OtherService {
    ResponseEntity<?> getFile(String nom, HttpServletRequest request) throws EntityNotFoundException, IOException;
}
