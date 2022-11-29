package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class ArticleResponse {
    private Long id;
    private String image;
    private String description_image;
    private String titre;
    private String description;
    private String type;
    private String auteur;
    private String auteurImage;
    private List<SectionString> sections;
    private Date date_publication;
    private Date date_modification;
    private int vues;
    public ArticleResponseList liste(){
        return new ArticleResponseList(id,image ,titre,description,type,auteur,date_publication);
    }
}
