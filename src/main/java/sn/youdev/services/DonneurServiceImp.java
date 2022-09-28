package sn.youdev.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.youdev.config.Constante;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.TestException;
import sn.youdev.dto.request.DonneurRequest;
import sn.youdev.dto.request.LoginRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.DonneurResponse;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.Donneur;
import sn.youdev.model.InfoPerso;
import sn.youdev.model.Token;
import sn.youdev.model.User;
import sn.youdev.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class DonneurServiceImp implements DonneurService {
    private final DonneurRepo repo;
    private final InfoRepo infoRepo;
    private final GroupeRepo groupeRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    @Autowired
    public DonneurServiceImp (DonneurRepo repo, InfoRepo infoRepo, GroupeRepo groupeRepo, PasswordEncoder passwordEncoder, UserRepo userRepo, TokenRepo tokenRepo) {
        this.repo = repo;
        this.infoRepo = infoRepo;
        this.groupeRepo = groupeRepo;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public Donneur findDonneur(String numero) throws EntityNotFoundException {
        return repo.findById(numero).orElseThrow(()->new EntityNotFoundException("donneur non trouvé"));
    }

    @Override
    public DonneurResponse findByNumero(String numero) throws EntityNotFoundException {
        return findDonneur(numero).getDonneurResponse();
    }

    @Override
    public List<DonneurResponse> findAll() {
        List<Donneur> donneurs = repo.findAllByActiveTrueOrderByInfoPerso_Nom();
        List<DonneurResponse> donneurResponses = new ArrayList<>();
        for (
                Donneur donneur: donneurs
             ) {
            donneurResponses.add(donneur.getDonneurResponse());
        }
        return donneurResponses;
    }
    private void verifyEntry(DonneurRequest registerRequest) throws EntreeException {
        if(infoRepo.findByEmailIgnoreCase(registerRequest.getEmail()).isPresent()) throw new EntreeException("1");
        if(infoRepo.findByCin(registerRequest.getCin()).isPresent()) throw  new EntreeException("2");
        if (infoRepo.findByTelephone(registerRequest.getTelephone()).isPresent()) throw  new EntreeException("3");
    }

    @Override
    public DonneurResponse saveDonneur(DonneurRequest donneurRequest) throws EntityNotFoundException, EntreeException, TestException {
        verifyEntry(donneurRequest);
        try {


            Donneur donneur = new Donneur();
            donneur.generateNumero();
            donneur.setGroupeSanguin(groupeRepo.findById(donneurRequest.getGroupe()).orElseThrow(() -> new EntityNotFoundException("groupe non trouvé")));
            repo.save(donneur);
            InfoPerso infos = new InfoPerso();
            infos.setCin(donneurRequest.getCin());
            infos.setEmail(donneurRequest.getEmail());
            infos.setPrenom(donneurRequest.getPrenom());
            infos.setNom(donneurRequest.getNom());
            infos.setTelephone(donneurRequest.getTelephone());
            infos.setNumeroDonneur(donneur);
            infoRepo.save(infos);
            return new DonneurResponse(donneur.getNumero(),infos.getPrenom(),infos.getNom(),donneur.getDateDernierDon(),true,donneur.getDons().size());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new TestException(e);
        }
    }

    @Transactional
    @Override
    public DonneurResponse editDonneur(String numero,DonneurRequest donneurRequest) throws EntityNotFoundException {
        Donneur donneur =findDonneur(numero);
        donneur.setGroupeSanguin(groupeRepo.findById(donneurRequest.getGroupe()).orElseThrow(()->new EntityNotFoundException("donneur non trouvé")));
        InfoPerso infos = donneur.getInfoPerso();
        infos.setCin(donneurRequest.getCin());
        infos.setEmail(donneurRequest.getEmail());
        infos.setPrenom(donneurRequest.getPrenom());
        infos.setNom(donneurRequest.getNom());
        infos.setTelephone(donneurRequest.getTelephone());
        infos.setNumeroDonneur(donneur);
        return donneur.getDonneurResponse();
    }

    @Transactional
    @Override
    public Boolean deleteDonneur(String numero) throws EntityNotFoundException {
        Donneur donneur = findDonneur(numero);
        donneur.setActive(false);
        donneur.setInfoPerso(null);
        return true;
    }
    @Override
    public Map<String, Object> saveWithExist(Long idInfo, String groupe) throws EntityNotFoundException, TestException {
        try{
            Map map = new HashMap<>();
        InfoPerso infos= infoRepo.findById(idInfo).orElseThrow(()->new EntityNotFoundException("info not found"));
        log.info(infos.getPrenom());
        log.info(infos.getUser().getLogin());
        Donneur donneur = new Donneur();
        donneur.generateNumero();
        donneur.setGroupeSanguin(groupeRepo.findById(groupe).orElseThrow(()->new EntityNotFoundException("donneur non trouvé")));
        donneur.setInfoPerso(infos);
        repo.save(donneur);
        infos.setNumeroDonneur(donneur);
        infoRepo.save(infos);
        map.put("donneur_info",new DonneurResponse(donneur.getNumero(),infos.getPrenom(),infos.getNom(),donneur.getDateDernierDon(),true,donneur.getDons().size()));
        map.put("user_info",infos.getUser().getResponse());
        return map;
        }
        catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw new TestException(e);
        }
    }

    @Override
    public Map<String, Object> saveExistDonneur(String numero, StringRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        Donneur donneur = findDonneur(numero);
        User user = new User();
        user.setEnabled(false);
        user.setLogin(request.getValue());
        user.setPassword(passwordEncoder.encode("passer123"));
        user.setNonLocked(true);
        user.setInfoPerso(donneur.getInfoPerso());
        userRepo.save(user);
        Token token = new Token((byte)0,user,2);
        tokenRepo.save(token);
        UserResponse userResponse = user.getResponse();
        Map<String, Object> map = new HashMap<>();
        map.put("user",userResponse);
        //  mail should be sent here
        map.put("enable_token", Constante.applicationUrl(httpServletRequest)+"/api/auth/enable/"+token.getCode());
        return map;
    }
}
