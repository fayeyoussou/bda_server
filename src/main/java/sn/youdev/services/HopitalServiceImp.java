package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.HopitalRequest;
import sn.youdev.dto.response.HopitalResponse;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.Hopital;
import sn.youdev.model.MedecinHopital;
import sn.youdev.repository.HopitalRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HopitalServiceImp implements HopitalService {
    private final HopitalRepo repo;
    private final LocalisationService localisationService;

    @Autowired
    public HopitalServiceImp(HopitalRepo repo, LocalisationService localisationService) {
        this.repo = repo;
        this.localisationService = localisationService;
    }

    @Override
    public Hopital getHopital(Long id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("no hopital found with this id"));
    }

    @Override
    public HopitalResponse GetById(Long id) throws EntityNotFoundException {
        return getHopital(id).hopitalResponse();
    }

    @Override
    public List<HopitalResponse> listHopital() {
        List<HopitalResponse> responses = new ArrayList<>();
        for (Hopital hopital: repo.findAllByEtatTrue()
             ) {
         responses.add(hopital.hopitalResponse());
        }
        return responses;
    }

    @Override
    public HopitalResponse saveHopital(HopitalRequest request) throws EntityNotFoundException, CustomArgumentValidationException {
        Map<String,String> erros = new CustomValidator().validate(request);
        if (erros.size()>0) throw new CustomArgumentValidationException(erros);
        Hopital hopital = new Hopital();
        hopital.setLocalisation(localisationService.getLocalisation(request.getLocalisation()));
        hopital.setTelephone(request.getTelephone());
        hopital.setNom(request.getNom());
        repo.save(hopital);
        return hopital.hopitalResponse();
    }

    @Transactional
    @Override
    public HopitalResponse editHopital(Long id, HopitalRequest request) throws EntityNotFoundException, CustomArgumentValidationException {
        Map<String,String> erros = new CustomValidator().validate(request);
        if (erros.size()>0) throw new CustomArgumentValidationException(erros);
        Hopital hopital = getHopital(id);
        hopital.setLocalisation(localisationService.getLocalisation(request.getLocalisation()));
        hopital.setTelephone(request.getTelephone());
        hopital.setNom(request.getNom());
        return hopital.hopitalResponse();
    }

    @Transactional
    @Override
    public Boolean deleteHopital(Long id) throws EntityNotFoundException {
        Hopital hopital = getHopital(id);
        hopital.setEtat(false);
        return true;
    }

    @Override
    public List<UserResponse> getEmployees(Long id) throws EntityNotFoundException {
        Hopital hopital = getHopital(id);
        List<UserResponse> responses = new ArrayList<>();
        for (MedecinHopital medecin : hopital.getMedecins()
                ) {
            responses.add(medecin.getUser().getResponse());
        }
        return responses;
    }
}
