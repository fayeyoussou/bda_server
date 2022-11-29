package sn.youdev.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.model.File;
import sn.youdev.repository.FileRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static sn.youdev.config.Constante.ASSETS;
import static sn.youdev.config.Constante.jsonResponse;

@Service
@Slf4j
public class OtherServiceImp implements OtherService {
    private final FileRepo fileRepo;
    private final UserService userService;
    private Path found;
    @Autowired
    public OtherServiceImp(FileRepo fileRepo, UserService userService) {
        this.fileRepo = fileRepo;
        this.userService = userService;
    }

    public void downloadFile(File myFile) throws IOException {
        found = null;
        Path uploadDirectory;
        if (Objects.equals(myFile.getType(), "default")) uploadDirectory = Paths.get(ASSETS+"/default");
        else uploadDirectory = Paths.get(ASSETS+"/"+myFile.getType());
        Files.list(uploadDirectory).forEach((file)->{
            if(file.getFileName().toString().startsWith(myFile.getNom())){
                this.found=file;
                return;
            }
        });
        if (found==null) found = Paths.get(ASSETS+"/default/notfound.png");
//        if(found !=null){
//            return new UrlResource(found.toUri());
//        }else return null;
    }
    @Override
    public ResponseEntity<?> getFile(String nom, HttpServletRequest request) throws EntityNotFoundException, IOException {
        try {

            System.out.println("Started here ------->");
            File file = fileRepo.findByNom(nom).orElseThrow(() -> new EntityNotFoundException("file not found"));
//            System.out.println("=====================");
//
//            System.out.println(request.getUserPrincipal());
////            UserResponse requester = request.getUserPrincipal();
//            System.out.println("=====================");
//            UsernamePasswordAuthenticationToken userPA = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
//            UserResponse requester = (UserResponse) userPA.getPrincipal();
//            System.out.println(requester.getRoles());
//            return ResponseEntity.ok(userPA);
//            if (Objects.equals(file.getOwner().getId(), requester.getId()) || requester.getRoles().contains("admin")) {
                downloadFile(file);

                if (this.found == null)
                    return jsonResponse(false, HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND.value(), "file is deleted -> contact the admins");
                System.out.println("=====================");
                Resource resource = new UrlResource(this.found.toUri());
                System.out.println(resource.getFilename());
                String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getId() == 1L ? "image/png":file.getType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(resource);

//            } else
//                return jsonResponse(false, HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED.value(), "access restreint");
        }catch (Exception e){
            log.error("error",e);
            throw e;
        }
    }
}
