package sn.youdev.services;

import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.DemandeRequest;
import sn.youdev.dto.response.DemandeListResponse;
import sn.youdev.dto.response.DemandeResponse;
import sn.youdev.model.Demande;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DemandeService {
    Demande getDemande(Long id) throws EntityNotFoundException;
    DemandeResponse getDemandeById(Long id) throws EntityNotFoundException, EntreeException;
    List<DemandeListResponse> getDemandeList();
    DemandeResponse addDemande(HttpServletRequest httpServletRequest, DemandeRequest request) throws UserNotFoundException;
    DemandeResponse editDemande(Long id,HttpServletRequest httpServletRequest, DemandeRequest request) throws UserNotFoundException, EntityNotFoundException, EntreeException;
    String deleteDemande(Long id,HttpServletRequest request) throws EntityNotFoundException, UserNotFoundException, EntreeException;
}
