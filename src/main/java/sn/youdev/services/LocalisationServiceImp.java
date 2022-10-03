package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.LocalisationRequest;
import sn.youdev.model.Localisation;
import sn.youdev.repository.LocalisationRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LocalisationServiceImp implements LocalisationService {
    private final LocalisationRepo repo;
    @Autowired
    public LocalisationServiceImp(LocalisationRepo repo) {
        this.repo = repo;
    }

    @Override
    public Object getLocalisationList() {
        List<Map<String, String>> list=new ArrayList<>();
        for (Localisation localisation:repo.findAll()
             ) {
            list.add(localisation.getResponse());
        }
        return list;
    }

    @Override
    public Localisation getLocalisation(Long id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("positition non trouve"));
    }

    @Override
    public Object addLocalisation(LocalisationRequest request) {
        Localisation localisation = new Localisation();
        localisation.setAdresse(request.getAdresse());
        localisation.setCode(request.getCode());
        localisation.setLatitude(request.getLatitude());
        localisation.setLongitude(request.getLongitude());
        repo.save(localisation);
        return localisation.getResponse();
    }

    @Transactional
    @Override
    public Object editLocalisation(Long id,LocalisationRequest request) throws EntityNotFoundException {
        Localisation localisation = (Localisation) getLocalisation(id);
        localisation.setAdresse(request.getAdresse());
        localisation.setCode(request.getCode());
        localisation.setLatitude(request.getLatitude());
        localisation.setLongitude(request.getLongitude());
        return localisation.getResponse();
    }

    @Override
    public Object deleteLocalisation(Long id) throws EntityNotFoundException {
        Localisation localisation = (Localisation) getLocalisation(id);
        repo.delete(localisation);
        return "deleted";
    }
}
