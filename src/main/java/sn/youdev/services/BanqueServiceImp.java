package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.BanqueRequete;
import sn.youdev.dto.response.BanqueResponse;
import sn.youdev.model.Banque;
import sn.youdev.model.GroupeSanguin;
import sn.youdev.model.Localisation;
import sn.youdev.model.Reserve;
import sn.youdev.repository.BanqueRepo;
import sn.youdev.repository.GroupeRepo;
import sn.youdev.repository.ReserveRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BanqueServiceImp implements BanqueService {
    private final BanqueRepo banqueRepo;
    private  final ReserveRepo reserveRepo;
    private final GroupeRepo groupeRepo;
    private final LocalisationService locaService;
    public CustomValidator validator = new CustomValidator();
    @Autowired
    public BanqueServiceImp(BanqueRepo banqueRepo, ReserveRepo reserveRepo, GroupeRepo groupeRepo, LocalisationService localisationRepo) {
        this.banqueRepo = banqueRepo;
        this.reserveRepo = reserveRepo;
        this.groupeRepo = groupeRepo;
        this.locaService = localisationRepo;
    }
    @Override
    public Banque getBanqueById(Long id) throws EntityNotFoundException {
        return banqueRepo.findById(id).orElseThrow(()->new EntityNotFoundException("banque "+id+" not found"));
    }
    @Override
    public List<BanqueResponse> getBanqueList() {
        List<BanqueResponse> banqueResponses = new ArrayList<>();
        for (Banque banque: banqueRepo.findAllByEtatTrue()
             ) {
            banqueResponses.add(banque.response());
        }
        return banqueResponses;
    }

    @Override
    public BanqueResponse addBanque(BanqueRequete requete) throws EntityNotFoundException, ArgumentValidationExption {
        Map <String,String> map= validator.validate(requete);
        if(banqueRepo.findByNomIgnoreCase(requete.getNom()).isPresent()) {
            map.put("nom","le nom existe deja");
        }
        if (map.size()>0) throw new ArgumentValidationExption(map);

        Localisation localisation = (Localisation) locaService.getLocalisation(requete.getLocalisation());
        Banque banque = new Banque();
        banque.setLocalisation(localisation);
        banque.setTelephone(requete.getTelephone());
        banque.setNom(requete.getNom());
        List<Reserve> reserves = new ArrayList<>();
        banqueRepo.save(banque);
        List<GroupeSanguin> groupeSanguins = groupeRepo.findAll();
        for (GroupeSanguin group:groupeSanguins
             ) {
            Reserve reserve = new Reserve(group,banque);
            reserveRepo.save(reserve);
            reserves.add(reserve);
        }
        banque.setReserves(reserves);
        return banque.response();
    }
    @Transactional
    @Override
    public BanqueResponse editBanque(Long id, BanqueRequete requete) throws EntityNotFoundException {
        Banque banque = getBanqueById(id);
        Localisation localisation = (Localisation) locaService.getLocalisation(requete.getLocalisation());
        banque.setLocalisation(localisation);
        banque.setTelephone(requete.getTelephone());
        banque.setNom(requete.getNom());
        return banque.response();
    }

    @Override
    public BanqueResponse getBanque(Long id) throws EntityNotFoundException {
        Banque banque = getBanqueById(id);
        return banque.response();
    }

    @Transactional
    @Override
    public Boolean deleteBanque(Long id) throws EntityNotFoundException {
        Banque banque = getBanqueById(id);
        banque.setEtat(false);
        for (Reserve reserve:banque.getReserves()
             ) {
            reserve.setQuantity(0);
        }
        return true;
    }

    @Transactional
    @Override
    public BanqueResponse editReserve(Long id,Map<String, Integer> reserves) throws EntityNotFoundException {
        Banque banque = getBanqueById(id);
        reserves.forEach((key,value)->{
            Optional<Reserve> reserve = reserveRepo.findByGroupeSanguin_GroupeAndBanque(key,banque);
            reserve.ifPresent(reserve1 -> reserve1.setQuantity(value));
        });
        return banque.response();
    }
}
