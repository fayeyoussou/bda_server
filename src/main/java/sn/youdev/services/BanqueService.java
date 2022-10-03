package sn.youdev.services;

import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.BanqueRequete;
import sn.youdev.dto.response.BanqueResponse;
import sn.youdev.model.Banque;

import java.util.List;
import java.util.Map;

public interface BanqueService {
    Banque getBanqueById(Long id) throws EntityNotFoundException;
    List<BanqueResponse> getBanqueList();
    BanqueResponse addBanque(BanqueRequete requete) throws EntityNotFoundException, ArgumentValidationExption;
    BanqueResponse editBanque(Long id,BanqueRequete requete) throws EntityNotFoundException;
    BanqueResponse getBanque(Long id) throws EntityNotFoundException;
    Boolean deleteBanque(Long id) throws EntityNotFoundException;
    BanqueResponse editReserve(Long id,Map<String, Integer> reserves) throws EntityNotFoundException;
}
