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
    private String type;
    private String auteur;
    private List<SectionString> sections;
    private Date date_publication;
    private Date date_modification;
    public ArticleResponseList liste(){
        return new ArticleResponseList(id,type,auteur,date_publication);
    }
}
