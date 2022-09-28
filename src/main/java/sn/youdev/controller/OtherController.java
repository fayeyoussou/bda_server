package sn.youdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.services.OtherService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class OtherController {

    private final OtherService otherService;

    @Autowired
    public OtherController(OtherService otherService) {
        this.otherService = otherService;
    }

    @GetMapping("/file/{nom}")
    public ResponseEntity<?> getFile(@PathVariable("nom") final String nom, HttpServletRequest request) throws EntityNotFoundException, IOException {
        System.out.println("here");
        return otherService.getFile(nom,request);
    }
}
