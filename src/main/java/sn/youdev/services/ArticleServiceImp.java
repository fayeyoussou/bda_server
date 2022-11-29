package sn.youdev.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.Constante;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleEditRequest;
import sn.youdev.dto.request.ArticleRequest;
import sn.youdev.dto.response.ArticleResponse;
import sn.youdev.dto.response.SectionString;
import sn.youdev.model.Article;
import sn.youdev.model.File;
import sn.youdev.model.Section;
import sn.youdev.model.User;
import sn.youdev.model.serializable.TypeArticle;
import sn.youdev.repository.ArticleRepo;
import sn.youdev.repository.FileRepo;
import sn.youdev.repository.SectionRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepo repo;
    private final SectionRepo sectionRepo;
    private final UserService userService;
    private final FileRepo fileRepo;


    @Autowired
    public ArticleServiceImp(ArticleRepo repo, SectionRepo sectionRepo, UserService userService, FileRepo fileRepo) {
        this.repo = repo;
        this.sectionRepo = sectionRepo;
        this.userService = userService;
        this.fileRepo = fileRepo;
    }

    @Transactional
    @Override
    public ArticleResponse addArticle(ArticleRequest request, HttpServletRequest htRequest,MultipartFile image) throws UserNotFoundException, IOException, EntityNotFoundException, CustomArgumentValidationException {

        CustomValidator validator = new CustomValidator();
        Map<String, String> errors = validator.validate(request);
        if(image ==null) errors.put("image","image needed");
        else if(!image.getContentType().contains("image")) errors.put("image","format image incorrect");
        else if(image.getSize() > 2087796) errors.put("image","taille de l'image doit être inférieur a 2Mo");
        if(errors.size()>0) throw new CustomArgumentValidationException(errors);
        User user = userService.getUserByRequest(htRequest);
        Article article = new Article();
        article.setAuteur(user);
        article.setDateModification(null);
        article.setDatePublication(new Date());
        article.setTitre(request.getTitre().toLowerCase());
        article.setDescription(request.getDescription().toLowerCase());
        File file = new File(Constante.createFile(image));
        fileRepo.save(file);
        article.setType(TypeArticle.valueOf(request.getType().toUpperCase()));

            article.setImage(fileRepo.findById(file.getId()).orElseThrow(()->new EntityNotFoundException("not found")));
        System.out.println("=================");
//        System.out.println(file);
        System.out.println("=================");
        System.out.println(article);
        System.out.println("=================");
        repo.save(article);

        return article.response();
    }

    @Override
    public List<ArticleResponse> listArticle() {
        List<ArticleResponse> responses = new ArrayList<>();
        List<Article> articles = repo.findAllByValideTrueAndDisponibleTrueOrderByDatePublicationDesc();
        for (Article article:articles
             ) {
            responses.add(article.response());
        }
        return responses;
    }

    @Override
    public ArticleResponse getArticleById(Long id) throws EntityNotFoundException, EntreeException {
        Article article = getArticle(id);
        if(!article.getDisponible()) throw new EntreeException("article supprime");
        if (!article.getValide())throw new EntreeException("article pas encore valide");
        return article.response();
    }

    @Override
    public Article getArticle(Long id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("article non trouve"));

    }

    @Override
    public Section getSection(Long article_id, Byte position) throws EntityNotFoundException {
        return sectionRepo.findByArticle_idAndPosition(article_id,position).orElse(null);
    }

    @Transactional
    @Override
    public ArticleResponse editArticle(Long id, ArticleEditRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        Article article = getArticle(id);
        article.setTitre(request.getTitre());
        article.setType(TypeArticle.valueOf(request.getType().toUpperCase()));
        article.setDateModification(new Date());
        return article.response();
    }
    @Transactional
    @Override
    public ArticleResponse addSections(Long id,List<SectionString> sections) throws EntityNotFoundException{
        try {Article article = getArticle(id);
        Byte i = 0;
        for (SectionString sectionString:sections
             ) {
            Section section1 = new Section(i, sectionString.getTitre(), sectionString.getContenu(), article);
            sectionRepo.save(section1);
            i++;
        }
        return article.response();}
        catch (Exception e){
            log.error(e.getClass().getSimpleName(),e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public ArticleResponse addOrEditSection(Long id, SectionString sectionString) throws EntityNotFoundException {
        Article article = getArticle(id);
        article.setDateModification(new Date());
        Section section = getSection(article.getId(),sectionString.getPosition());
        if (section == null) {
            Section section1 = new Section(sectionString.getPosition(), sectionString.getTitre(), sectionString.getContenu(), article);
            sectionRepo.save(section1);
        }else{
            Byte newPosition = (byte) -(section.getPosition());
            Section verif = getSection(article.getId(), newPosition);
            if(verif!=null) {
             verif.setContenu(section.getContenu());
             verif.setTitle(section.getTitle());
            }else {
                Section section1 = new Section(newPosition,section.getTitle(),section.getContenu(),article);
                section.setEtat(false);
                sectionRepo.save(section1);
            }
            section.setTitle(sectionString.getTitre());
            section.setContenu(sectionString.getContenu());
        }
        return article.response();
    }

    @Transactional
    @Override
    public Boolean deleteArticle(Long id) throws EntityNotFoundException {
        Article article = getArticle(id);
        article.setDisponible(false);
        return null;
    }

    @Override
    public ArticleResponse changeImage(Long id, MultipartFile image) throws EntityNotFoundException, IOException {
        Article article = getArticle(id);
        File file = article.getImage();
        file.deleteFile();
        fileRepo.delete(file);
        File newFile = new File(Constante.createFile(image));
        fileRepo.save(newFile);
        article.setImage(newFile);
        return article.response();
    }
    @Transactional
    @Override
    public ArticleResponse addVues(Long id) throws EntityNotFoundException {
        Article article = getArticle(id);
        article.setVues(article.getVues()+1);
        return article.response();
    }

    @Transactional
    @Override
    public ArticleResponse deleteSection(Long id, Byte position) throws EntityNotFoundException {
        Article article = getArticle(id);
        Section section = getSection(id,position);
        if (section==null)throw new EntityNotFoundException("section non trouve");
        Byte newPosition = (byte) -(position);
        section.setEtat(false);
        section.setPosition(newPosition);
        for (Section sect:article.getSections()
             ) {
            if (sect.getPosition()>position) sect.setPosition((byte) (sect.getPosition()-1));
        }
        return article.response();
    }
}
