package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "journees")
public class Journee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_autorisation;
    private Date date_journee;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentaire_organisateur;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentaire_cnts;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "organisateur")
    private User organisateur;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "autorisateur")
    private User autorisateur;
}
