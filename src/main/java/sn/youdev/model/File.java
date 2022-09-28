package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.Objects;

import static sn.youdev.config.Constante.ASSETS;
import static sn.youdev.config.Constante.getExtension;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;
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
}
