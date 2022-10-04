package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static sn.youdev.config.Constante.ASSETS;
import static sn.youdev.config.Constante.getExtension;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "files")
@Slf4j
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;
    private String description;
    @Column(nullable = false)
    private String type;
    private Date dateUpload;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
    public File(MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get(ASSETS+"/"+multipartFile.getContentType());
        java.io.File theDir = uploadDirectory.toFile();
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        InputStream inputStream = multipartFile.getInputStream();
        this.nom = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        this.type = multipartFile.getContentType();
        this.dateUpload = new Date();
        Path filePath = uploadDirectory.resolve(this.nom+getExtension(fileName));
        Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    public File(MultipartFile multipartFile,String titre) throws IOException {
        this.description = titre;
        Path uploadDirectory = Paths.get(ASSETS+"/"+multipartFile.getContentType());
        java.io.File theDir = uploadDirectory.toFile();
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        InputStream inputStream = multipartFile.getInputStream();
        this.nom = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        this.type = multipartFile.getContentType();
        this.dateUpload = new Date();
        Path filePath = uploadDirectory.resolve(this.nom+getExtension(fileName));
        Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    public Integer deleteFile() throws IOException {
        AtomicReference<Integer> found = new AtomicReference<>( 0);
        Path uploadDirectory = Paths.get(ASSETS+"/"+this.type);
            Files.list(uploadDirectory).forEach((file) -> {
                if (file.getFileName().toString().startsWith(this.nom)) {
                    try {
                        found.set(1);
                        Files.delete(file);
                    } catch (IOException e) {
                        found.set(2);
                        log.error(e.getClass().getSimpleName(), e);
                    }
                }
            });
        return found.get();
    }
}
