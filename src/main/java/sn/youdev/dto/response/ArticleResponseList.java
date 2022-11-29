package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter @Setter @AllArgsConstructor
public class ArticleResponseList {
    private Long id;
    private String image;
    private String titre;
    private String description;
    private String type;
    private String auteur;
    private Date date_publication;
}
