package sn.youdev.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicReference;

import static sn.youdev.config.Constante.ASSETS;

@Service
public class TestService {
    private Path found;
    public Object uploadFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get(ASSETS+"/images");
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadDirectory.resolve(RandomStringUtils.randomAlphanumeric(8).toLowerCase()+fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return "file saved";
        }catch (IOException io){
            throw new IOException("error : "+fileName,io);
        }
    }
    public Resource downloadFile(String fileName) throws IOException {
        found = null;
        Path uploadDirectory = Paths.get(ASSETS+"/images");
        Files.list(uploadDirectory).forEach((file)->{
            if(file.getFileName().toString().startsWith(fileName)){
                found=file;
                return;
            }
        });
        if(found !=null){
            return new UrlResource(found.toUri());
        }else return null;
    }
}
