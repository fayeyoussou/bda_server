package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.DemandeRequest;
import sn.youdev.dto.response.DemandeListResponse;
import sn.youdev.dto.response.DemandeResponse;
import sn.youdev.model.Demande;
import sn.youdev.model.Role;
import sn.youdev.model.User;
import sn.youdev.repository.DemandeRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DemandeServiceImp implements DemandeService {
    private final DemandeRepo repo;
    private final UserService userService;

    @Autowired
    public DemandeServiceImp(DemandeRepo repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Override
    public Demande getDemande(Long id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("demande non trouvée"));
    }

    @Override
    public DemandeResponse getDemandeById(Long id) throws EntityNotFoundException, EntreeException {
        Demande demande = getDemande(id);
        if (!demande.getEtat()) throw new EntreeException("demande supprime");
        return demande.response();
    }

    @Override
    public List<DemandeListResponse> getDemandeList() {
        List<DemandeListResponse> responses = new ArrayList<>();
        List<Demande> demandes = repo.findAllByEtatTrue();
        for (Demande demande:demandes
             ) {
            responses.add(demande.responseList());
        }
        return responses;
    }

    @Override
    public DemandeResponse addDemande(HttpServletRequest httpServletRequest, DemandeRequest request) throws UserNotFoundException {
        Demande demande = new Demande();
        demande.setDemandeur(userService.getUserByRequest(httpServletRequest));
        demande.setMessage(request.getMessage());
        demande.setObjet(request.getObject());
        repo.save(demande);
        return demande.response();
    }

    @Transactional
    @Override
    public DemandeResponse editDemande(Long id, HttpServletRequest httpServletRequest, DemandeRequest request) throws UserNotFoundException, EntityNotFoundException, EntreeException {
        Demande demande  = getDemande(id);
        User user = userService.getUserByRequest(httpServletRequest);
        if(Objects.equals(demande.getDemandeur().getId(), user.getId())) {
            demande.setMessage(request.getMessage());
            demande.setObjet(request.getObject());
            return demande.response();
        }else throw new EntreeException("vous n'etes pas l'auteur de cette demande");
    }

    @Transactional
    @Override
    public String deleteDemande(Long id, HttpServletRequest request) throws EntityNotFoundException, UserNotFoundException, EntreeException {
        User user = userService.getUserByRequest(request);
        boolean isAdminOrCnts = false;
        for (Role role:user.getRoles()
             ) {
            if (Objects.equals(role.getNom(), "admin") || Objects.equals(role.getNom(), "cnts")) {
                isAdminOrCnts = true;
                break;
            }
        }
        Demande demande = getDemande(id);
        if(isAdminOrCnts|| Objects.equals(user.getId(), demande.getDemandeur().getId())) {demande.setEtat(false);
        return "supprimée";}
        else throw new EntreeException("vous n'avez pas le droit de supprime cette demande");
    }
}
