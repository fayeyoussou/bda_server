package sn.youdev.services;

import org.springframework.stereotype.Service;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ReponseRequest;
import sn.youdev.dto.response.DemandeResponse;
import sn.youdev.model.Demande;
import sn.youdev.model.Reponse;
import sn.youdev.model.User;
import sn.youdev.repository.ReponseRepo;

import javax.servlet.http.HttpServletRequest;
@Service
public class ReponseServiceImp implements ReponseService {
    private final ReponseRepo repo;
    private final UserService userService;
    private final DemandeService demandeService;

    public ReponseServiceImp(ReponseRepo repo, UserService userService, DemandeService demandeService) {
        this.repo = repo;
        this.userService = userService;
        this.demandeService = demandeService;
    }

    @Override
    public DemandeResponse repondre(Long id, ReponseRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException, UserNotFoundException {
        Demande demande = demandeService.getDemande(id);
        User user = userService.getUserByRequest(httpServletRequest);
        Reponse reponse = new Reponse();
        reponse.setDemande(demande);
        reponse.setMessage(request.getMessage());
        reponse.setObjet(request.getObjet());
        reponse.setRepondeur(user);
        repo.save(reponse);
        demande.setReponse(reponse);
        return demande.response();
    }
}
