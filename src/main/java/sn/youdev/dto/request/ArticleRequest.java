package sn.youdev.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.dto.response.SectionString;

import java.util.List;

@Getter @Setter
public class ArticleRequest {
    private MultipartFile image;
    private String description_image;
    private String titre;
    private String type;
    private List<SectionString> sections;
}
