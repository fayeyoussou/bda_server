package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.ResultatRequest;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.ResultatResponse;
import sn.youdev.model.Banque;
import sn.youdev.model.Don;
import sn.youdev.model.Donneur;
import sn.youdev.model.Resultat;
import sn.youdev.model.serializable.EtatDon;
import sn.youdev.repository.DonRepo;
import sn.youdev.repository.ResultatRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DonServiceImp implements DonService {
    private final DonRepo repo;
    private final DonneurService donneurService;
    private final BanqueService banqueService;
    private final ResultatRepo resultatRepo;

    @Autowired
    public DonServiceImp(DonRepo repo, DonneurService donneurService, BanqueService banqueService, ResultatRepo resultatRepo) {
        this.repo = repo;
        this.donneurService = donneurService;
        this.banqueService = banqueService;

        this.resultatRepo = resultatRepo;
    }
    private Don getDonById(String id) throws EntityNotFoundException {
        return repo.findById(id).orElseThrow(()->new EntityNotFoundException("Don non trouve dans la base"));
    }

    @Override
    public List<DonResponse> listAllDon() {

        List<Don> dons = repo.findAllByOrderByDateAsc();
        List<DonResponse> donResponses = new ArrayList<>();
        for (Don don:dons
             ) {
            donResponses.add(don.getDonResponse());
        }
        return donResponses;
    }

    @Override
    public List<DonResponse> listDonDispo() {
        List<Don> dons = repo.findAllByEtatOrderByDateAsc(EtatDon.DISPONIBLE);
        List<DonResponse> donResponses = new ArrayList<>();
        for (Don don:dons
        ) {
            donResponses.add(don.getDonResponse());
        }
        return donResponses;
    }

    @Override
    public List<DonResponse> listByDonneur(String numero) throws EntityNotFoundException {

        List<Don> dons = repo.findAllByDonneur(donneurService.findDonneur(numero));
        List<DonResponse> donResponses = new ArrayList<>();
        for (Don don:dons
        ) {
            donResponses.add(don.getDonResponse());
        }
        return donResponses;
    }

    @Override
    public DonResponse getDon(String numero) throws EntityNotFoundException {
        return getDonById(numero).getDonResponse();
    }

    @Override
    public ResultatResponse seeResultat(String numero) throws EntityNotFoundException {
        Don don = getDonById(numero);
        if (don.getResultat()==null) return null;
        return don.getResultat().resultatResponse();
    }
    private void validate(Object donRequest) throws ArgumentValidationExption {
        CustomValidator validator = new CustomValidator();
        Map<String,String> errors = validator.validate(donRequest);
       if(errors.size()>0)throw new ArgumentValidationExption(errors);
    }
    @Override
    public DonResponse saveDon(DonRequest donRequest) throws ArgumentValidationExption, EntityNotFoundException {
        CustomValidator validator = new CustomValidator();
        Map<String,String> errors = validator.validate(donRequest);
        if(errors.size()>0)throw new ArgumentValidationExption(errors);
        Donneur donneur = donneurService.findDonneur(donRequest.getDonneur());
        Banque banque;
        if(donRequest.getBanque()==null) banque = null;
        else banque = banqueService.getBanqueById(donRequest.getBanque());
        Don don = new Don(donRequest.getObservation(),donneur,banque,donRequest.getDate());
        repo.save(don);
        return don.getDonResponse();
    }

    @Override
    public Object saveResult(ResultatRequest request) throws ArgumentValidationExption, EntityNotFoundException {
        Resultat resultat = new Resultat();
        validate(request);
        resultat.setDon(getDonById(request.getDon()));
        resultat.setHepatiteB(request.getHepatiteB());
        resultat.setHepatiteC(request.getHepatiteC());
        resultat.setVih1(request.getVih1());
        resultat.setVih2(request.getVih2());
        resultat.setSyphillis(request.getSyph());
        resultat.setNatTest(request.getNat());
        resultatRepo.save(resultat);
        return resultat.resultatResponse();
    }
    @Transactional
    @Override
    public DonResponse editDon(String numero,DonRequest request) throws ArgumentValidationExption, EntityNotFoundException {
        validate(request);
        Don don = getDonById(numero);
        Donneur donneur = donneurService.findDonneur(request.getDonneur());
        Banque banque;
        if(request.getBanque()==null) banque = null;
        else banque = banqueService.getBanqueById(request.getBanque());
        don.setBanque(banque);
        don.setDonneur(donneur);
        don.setDate(request.getDate());
        if(request.getObservation()!=null )don.setObservation(request.getObservation());
        return don.getDonResponse();
    }
    @Transactional
    @Override
    public DonResponse changeEtat(String numero,String etat) throws EntityNotFoundException {
        Don dom = getDonById(numero);
        dom.setEtat(EtatDon.valueOf(etat.toUpperCase()));
        return dom.getDonResponse();
    }
    @Transactional
    @Override
    public ResultatResponse editResultat(ResultatRequest request) throws EntityNotFoundException, ArgumentValidationExption, EntreeException {
        Don don = getDonById(request.getDon());
        validate(request);
        Resultat resultat = don.getResultat();
        if(resultat == null) throw new EntreeException("ce don n'a pas de resultat");
        resultat.setSyphillis(request.getSyph());
        resultat.setHepatiteB(request.getHepatiteB());
        resultat.setHepatiteC(request.getHepatiteC());
        resultat.setVih1(request.getVih1());
        resultat.setVih2(request.getVih2());
        resultat.setNatTest(request.getNat());
        return resultat.resultatResponse();
    }
}
