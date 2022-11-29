package sn.youdev.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.TestException;
import sn.youdev.dto.request.BanqueRequete;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.LocalisationRequest;
import sn.youdev.dto.request.RegisterRequest;
import sn.youdev.dto.response.BanqueResponse;
import sn.youdev.model.*;
import sn.youdev.repository.*;

import javax.persistence.GeneratedValue;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static sn.youdev.config.Constante.ASSETS;

@Service
public class TestService {
    private Path found;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private  final InfoRepo infoRepo;
    private final PasswordEncoder passwordEncoder;
    private final FileRepo fileRepo;
    private final RoleRepo roleRepo;
    private final GroupeRepo groupeRepo;
    private final DonneurService donneurService;
    private final DonService donService;
    private final BanqueService banqueService;
    private final LocalisationService localisationService;
    private final HopitalService hopitalService;
    @Autowired
    public TestService(UserRepo userRepo, TokenRepo tokenRepo, InfoRepo infoRepo, PasswordEncoder passwordEncoder, FileRepo fileRepo, RoleRepo roleRepo, GroupeRepo groupeRepo, DonneurService donneurService, DonService donService, BanqueService banqueService, LocalisationService localisationService, HopitalService hopitalService) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.infoRepo = infoRepo;
        this.passwordEncoder = passwordEncoder;
        this.fileRepo = fileRepo;
        this.roleRepo = roleRepo;
        this.groupeRepo = groupeRepo;
        this.donneurService = donneurService;
        this.donService = donService;
        this.banqueService = banqueService;
        this.localisationService = localisationService;
        this.hopitalService = hopitalService;
    }

    public Object uploadFile( MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get(ASSETS+"/images");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println( multipartFile.getOriginalFilename());
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadDirectory.resolve(RandomStringUtils.randomAlphanumeric(8).toLowerCase()+multipartFile.getOriginalFilename());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return "file saved";
        }catch (IOException io){
            throw new IOException("error : "+multipartFile.getOriginalFilename(),io);
        }
    }
    public Resource downloadFile(String fileName) throws IOException {
        found = null;
        Path uploadDirectory = Paths.get(ASSETS+"/images");
        Files.list(uploadDirectory).forEach((file)->{
            if(file.getFileName().toString().startsWith(fileName)){
                found=file;
                return;
            }
        });
        if(found !=null){
            return new UrlResource(found.toUri());
        }else return null;
    }
    private User addUser(RegisterRequest registerRequest) {
        InfoPerso infoPerso = new InfoPerso();
        infoPerso.setInfoPerso(registerRequest);
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        String password = RandomStringUtils.randomAlphanumeric(7);
        user.setPassword(passwordEncoder.encode("passer123"));
        user.setInfoPerso(infoPerso);
        File file = fileRepo.findById(1L).orElseThrow();
        fileRepo.save(file);
        Token token = new Token(user);
        infoPerso.setImage(file);
        infoRepo.save(infoPerso);
        userRepo.save(user);
        file.setOwner(user);
        tokenRepo.save(token);
        System.out.println(user.getResponse());
        return user;
    }

    @GeneratedValue
    @Transactional
    public void run() throws EntreeException, TestException, EntityNotFoundException, CustomArgumentValidationException {
        File file = new File();
        file.setDateUpload(new Date());
        file.setType("default");
        file.setNom("notfound");
        file.setId(1L);
        fileRepo.saveAndFlush(file);
        Role admin = roleRepo.saveAndFlush(new Role("Admin"));
        Role cnts = roleRepo.saveAndFlush(new Role("Cnts"));
        Role medecin = roleRepo.saveAndFlush(new Role("Medecin"));
        Role banque = roleRepo.saveAndFlush(new Role("Banque"));
        groupeRepo.saveAllAndFlush(List.of(
                new GroupeSanguin("O+"),
                new GroupeSanguin("O-"),
                new GroupeSanguin("A+"),
                new GroupeSanguin("A-"),
                new GroupeSanguin("AB+"),
                new GroupeSanguin("AB-"),
                new GroupeSanguin("B+"),
                new GroupeSanguin("B-")
                )
        );
        User fayeyousso= addUser(new RegisterRequest("fayeyouso@gmail.com","youssoupha","faye","1596199102362","(221) 77 490 66 62","fayeyousso"));
        User niang = addUser(new RegisterRequest("n_kharagne@gmail.com","Ibrahima","Niang","1545199102362","(221) 77 345 23 14","niankharagne"));
        User thiam = addUser(new RegisterRequest("babel@gmail.com","Mohamed","Thiam","1365189902362","+221 78 654 25 13","babel"));
        User thiane  = addUser(new RegisterRequest("thiane@gmail.com","Thiane","Sene","1587200502362","(221) 77 235 66 62","thianechou"));
        userRepo.flush();
        User user = userRepo.findByLoginOrInfoPerso_Email("fayeyousso","fayeyousso").orElseThrow();
        user.addRoleToUser(admin);
        user.addRoleToUser(cnts);
        donneurService.saveWithExist(fayeyousso.getId(),"O+");
        donneurService.saveWithExist(niang.getId(), "O-");
        donneurService.saveWithExist(thiam.getId(), "A-");
        donneurService.saveWithExist(thiane.getId(),"AB-");
        LocalisationRequest localisationRequest = new LocalisationRequest();
        localisationRequest.setAdresse("avenue fann");
        localisationRequest.setLatitude((float) 14.70);
        localisationRequest.setLongitude((float) -17.48);
        localisationRequest.setCode("CNTS");
        localisationService.addLocalisation(localisationRequest);
        BanqueRequete br = new BanqueRequete();
        br.setTelephone("(221) 33 890 25 12");
        br.setNom("Centre national de transfusion sanguine");
        br.setLocalisation(localisationService.getLocalisation(1L).getId());
        BanqueResponse banqueResponse = banqueService.addBanque(br);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-4);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));
        calendar.add(Calendar.MONTH,-7);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));
        calendar.add(Calendar.MONTH,-12);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));
        calendar.add(Calendar.MONTH,-16);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));
        calendar.add(Calendar.MONTH,-21);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));
        calendar.add(Calendar.MONTH,-26);
        donService.saveDon(new DonRequest(fayeyousso.getInfoPerso().getNumeroDonneur().getNumero(),"nada1",banqueResponse.getId(),calendar.getTime()));

    }
}
