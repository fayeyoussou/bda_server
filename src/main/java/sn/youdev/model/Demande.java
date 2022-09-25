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
@Table(name = "demandes")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String Subject;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String message;
    private Date date = new Date();
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeur",nullable = false)
    private User demandeur;
    @OneToOne(mappedBy = "demande")
    @JoinColumn(name = "reponse_id")
    private Reponse reponse;
}
