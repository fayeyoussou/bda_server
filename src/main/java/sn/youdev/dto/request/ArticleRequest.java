package sn.youdev.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.dto.response.SectionString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static sn.youdev.config.Constante.PAS_VIDE;

@Getter @Setter
public class ArticleRequest {
//    private MultipartFile image;
    @NotEmpty(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String description_image;
    @NotEmpty(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String titre;
    @NotEmpty(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String type;
    @NotEmpty(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String description;

}
