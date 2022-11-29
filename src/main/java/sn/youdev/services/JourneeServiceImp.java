package sn.youdev.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleRequest;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.JourneeRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.ArticleResponse;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.JourneeResponse;
import sn.youdev.model.*;
import sn.youdev.repository.JourneeDonRepo;
import sn.youdev.repository.JourneeRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class JourneeServiceImp implements JourneeService {
    private final JourneeRepo repo;
    private final JourneeDonRepo journeeDonRepo;
    private final UserService userService;
    private final DonService donService;
    private final ArticleService articleService;

    @Autowired
    public JourneeServiceImp(JourneeRepo repo, JourneeDonRepo journeeDonRepo, UserService userService, DonService donService, ArticleService articleService) {
        this.repo = repo;
        this.journeeDonRepo = journeeDonRepo;
        this.userService = userService;
        this.donService = donService;
        this.articleService = articleService;
    }

    @Override
    public List<JourneeResponse> listJournee() {
        List<JourneeResponse> journeeResponses = new ArrayList<>();
        for (Journee journee:
             repo.findAll()) {
            journeeResponses.add(journee.response());
        }
        return journeeResponses;
    }

    @Override
    public List<DonResponse> listDonJournee(Long id) throws EntityNotFoundException {
        List<DonResponse> responses= new ArrayList<>();
        List<JourneeDon> journeeDons = getJournee(id).getDons();
        if(journeeDons!=null){
            for (JourneeDon jdon:
                 journeeDons) {
                responses.add(jdon.getDon().getDonResponse());
            }
        }
        return responses;
    }

    @Override
    public ArticleResponse articleJournee(Long id) throws EntityNotFoundException {
        Journee journee = getJournee(id);
        if(journee.getArticle()!=null) return journee.getArticle().response();
        return null;
    }

    @Override
    public JourneeResponse addJournee(JourneeRequest request) throws UserNotFoundException {
        Journee journee = new Journee();
        journee.setDate_journee(request.getDateJournee());
        journee.setDate_autorisation(request.getDateAutorisation());
        journee.setOrganisateur(userService.findUser(request.getOrganisateur()));
        repo.save(journee);
        return journee.response();
    }

    @Override
    public List<DonResponse> addDonJournee(Long id, DonRequest request) throws CustomArgumentValidationException, EntityNotFoundException {
        Journee journee = getJournee(id);
        DonResponse donResponse = donService.saveDon(request);
        Don don = donService.getDonById(donResponse.getNumero());
        JourneeDon journeeDon = new JourneeDon(journee,don);
        journeeDonRepo.save(journeeDon);
        return listDonJournee(id);
    }

    @Override
    public ArticleResponse addArticleToJournee(Long id, ArticleRequest request,HttpServletRequest httpServletRequest) throws UserNotFoundException, IOException, EntityNotFoundException {
//        Journee journee = getJournee(id);
//        ArticleResponse articleResponse = articleService.addArticle(request,httpServletRequest,);
//        Article article = articleService.getArticle(articleResponse.getId());
//        journee.setArticle(article);
//        repo.save(journee);
//        return articleResponse;
        return null;
    }

    @Override
    public Journee getJournee(Long id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("journee non trouvée"));
    }

    @Override
    public JourneeResponse getJourneeById(Long id) throws EntityNotFoundException {
        return getJournee(id).response();
    }

    @Transactional
    @Override
    public JourneeResponse addCntsComment(Long id, StringRequest request) throws EntityNotFoundException {
        Journee journee = getJournee(id);
        journee.setCommentaire_cnts(request.getValue());
        return journee.response();
    }

    @Transactional
    @Override
    public JourneeResponse addOrgaComment(Long id, HttpServletRequest httpServletRequest, StringRequest request) throws EntityNotFoundException, UserNotFoundException, EntreeException {
        Journee journee = getJournee(id);
        User user = userService.getUserByRequest(httpServletRequest);
        if(user == journee.getOrganisateur()){
            journee.setCommentaire_organisateur(request.getValue());
            return journee.response();
        }else
            throw new EntreeException("vous n'etes pas l'organisateur de cette journee");
    }

}
